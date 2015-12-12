package com.myprj.crawler.service.mapping.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myprj.crawler.domain.MigrationParam;
import com.myprj.crawler.domain.Pageable;
import com.myprj.crawler.domain.crawl.CrawlResultData;
import com.myprj.crawler.domain.target.ConsolidationAttributeData;
import com.myprj.crawler.domain.target.ConsolidationData;
import com.myprj.crawler.service.CrawlResultService;
import com.myprj.crawler.service.event.CrawlEventPublisher;
import com.myprj.crawler.service.event.impl.ConsolidationCompletedEvent;
import com.myprj.crawler.service.mapping.MappingService;
import com.myprj.crawler.service.mapping.MigrationService;
import com.myprj.crawler.service.mapping.Pair;
import com.myprj.crawler.util.Serialization;
import com.myprj.crawler.util.converter.ConsolidationConverter;

/**
 * @author DienNM (DEE)
 */
@Service
public class MigrationServiceImpl implements MigrationService {

    private Logger logger = LoggerFactory.getLogger(MigrationServiceImpl.class);

    @Autowired
    private MappingService mappingService;

    @Autowired
    private CrawlResultService crawlResultService;
    
    @Autowired
    private CrawlEventPublisher crawlEventPublisher;

    @Override
    public void migrate(MigrationParam migrationParam) {

        int pageSize = 300;
        int pageIndex = 0;
        while (true) {
            Pageable pageable = new Pageable(pageSize, pageIndex);
            List<CrawlResultData> crawlResults = crawlResultService.get(migrationParam.getSiteKey(),
                    migrationParam.getCategoryKey(), migrationParam.getItemKey(), pageable);
            if (crawlResults.isEmpty()) {
                break;
            }
            migrate(migrationParam, crawlResults);
        }

    }

    @Override
    public List<ConsolidationData> migrate(MigrationParam migrationParam, List<CrawlResultData> crawlResults) {
        List<ConsolidationData> consolidationDatas = new ArrayList<ConsolidationData>();
        for (CrawlResultData crawlResult : crawlResults) {
            ConsolidationData consolidation = createConsolidation(crawlResult, migrationParam.getIndexMapping());
            if(consolidation != null) {
                consolidationDatas.add(consolidation);
                crawlEventPublisher.publish(new ConsolidationCompletedEvent(consolidation));
            }
        }
        return consolidationDatas;
    }

    private ConsolidationData createConsolidation(CrawlResultData crawlResult, Map<String, Object> indexMapping) {
        ConsolidationData consolidation = new ConsolidationData();
        consolidation.setCategoryKey(crawlResult.getCategoryKey());
        consolidation.setItemKey(crawlResult.getItemKey());
        consolidation.setSiteKey(crawlResult.getSiteKey());
        consolidation.setResultKey(crawlResult.getRequestId());
        ConsolidationData.createMd5Key(consolidation);

        Pair<Map<String, Object>, Map<String, Object>> resultPair = mappingService.doMappingIndex(indexMapping,
                crawlResult.getDetail());

        Map<String, Object> mapFields = resultPair.getLeft();
        Map<String, Object> mapAttributes = resultPair.getRight();

        if (mapAttributes.isEmpty()) {
            logger.warn(String.format("No attributes are mapped: %s", consolidation.toString()));
            return null;
        }
        ConsolidationData.createMd5Attribute(consolidation, mapAttributes);

        ConsolidationConverter.map(mapFields, consolidation);
        for (String attributeName : mapAttributes.keySet()) {
            ConsolidationAttributeData attribute = new ConsolidationAttributeData();
            attribute.setConsolidationId(consolidation.getMd5Key());
            attribute.setName(attributeName);
            Object value = mapAttributes.get(attributeName);
            if (value == null) {
                continue;
            }
            if (value instanceof String) {
                attribute.setValue(value.toString());
            } else {
                attribute.setValue(Serialization.serialize(value));
            }
            consolidation.getAttributes().add(attribute);
        }
        return consolidation;
    }
}
