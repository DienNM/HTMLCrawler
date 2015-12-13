package com.myprj.crawler.service.mapping.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myprj.crawler.domain.DataMapping;
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
import com.myprj.crawler.util.Serialization;

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
            Pageable pageable = new Pageable(pageSize, pageIndex++);
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
        List<DataMapping> dataMappings = mappingService.loadDataMappings(migrationParam.getSiteKey(),
                migrationParam.getCategoryKey(), migrationParam.getItemKey());
        for (CrawlResultData crawlResult : crawlResults) {
            ConsolidationData consolidation = createConsolidation(crawlResult, dataMappings,
                    migrationParam.getIndexMapping());
            if (consolidation != null) {
                consolidationDatas.add(consolidation);
                crawlEventPublisher.publish(new ConsolidationCompletedEvent(consolidation));
            }
        }
        return consolidationDatas;
    }

    @SuppressWarnings("unchecked")
    private ConsolidationData createConsolidation(CrawlResultData crawlResult, List<DataMapping> mappings,
            Map<String, Object> indexMapping) {
        ConsolidationData consolidation = new ConsolidationData();
        consolidation.setCategoryKey(crawlResult.getCategoryKey());
        consolidation.setItemKey(crawlResult.getItemKey());
        consolidation.setSiteKey(crawlResult.getSiteKey());
        consolidation.setResultKey(crawlResult.getResultKey());
        consolidation.setUrl(crawlResult.getUrl());
        ConsolidationData.createMd5Key(consolidation);

        Map<String, Object> contentObject = (Map<String, Object>) crawlResult.getDetail().get("content");

        Map<String, Object> resultMappingIndex = mappingService.doMappingIndex(indexMapping,
                contentObject);

        mappingService.applyRuleMapping(mappings, resultMappingIndex);

        if (resultMappingIndex.isEmpty()) {
            logger.warn(String.format("No attributes are mapped: %s", consolidation.toString()));
            return null;
        }
        ConsolidationData.createMd5Attribute(consolidation, resultMappingIndex);

        for (String attributeName : resultMappingIndex.keySet()) {
            ConsolidationAttributeData attribute = new ConsolidationAttributeData();
            attribute.setConsolidationId(consolidation.getMd5Key());
            attribute.setName(attributeName);
            Object value = resultMappingIndex.get(attributeName);
            if (value == null || StringUtils.isEmpty(value.toString())) {
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
