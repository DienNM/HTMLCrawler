package com.myprj.crawler.service.mapping.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myprj.crawler.domain.DataMapping;
import com.myprj.crawler.domain.MigrationContext;
import com.myprj.crawler.domain.Pageable;
import com.myprj.crawler.domain.crawl.CrawlResultData;
import com.myprj.crawler.domain.target.ConsolidationAttributeData;
import com.myprj.crawler.domain.target.ConsolidationData;
import com.myprj.crawler.service.CrawlResultService;
import com.myprj.crawler.service.event.CrawlEventPublisher;
import com.myprj.crawler.service.event.impl.ConsolidationCompletedEvent;
import com.myprj.crawler.service.mapping.Mapping;
import com.myprj.crawler.service.mapping.MappingService;
import com.myprj.crawler.service.mapping.MigrationService;
import com.myprj.crawler.util.CommonUtil;
import com.myprj.crawler.util.MappingUtil;
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
    public void migrate(MigrationContext migrationCtx) {
        int pageSize = 300;
        int pageIndex = 0;
        while (true) {
            Pageable pageable = new Pageable(pageSize, pageIndex++);
            List<CrawlResultData> crawlResults = crawlResultService.get(migrationCtx.getSiteKey(),
                    migrationCtx.getCategoryKey(), migrationCtx.getItemKey(), pageable);
            if (crawlResults.isEmpty()) {
                break;
            }
            migrate(migrationCtx, crawlResults);
        }
    }

    @Override
    public List<ConsolidationData> migrate(MigrationContext migrationCtx, List<CrawlResultData> crawlResults) {
        List<ConsolidationData> consolidationDatas = new ArrayList<ConsolidationData>();
        List<DataMapping> ruleMappings = MappingUtil.loadRuleMappings(migrationCtx.getSiteKey(),
                migrationCtx.getCategoryKey(), migrationCtx.getItemKey());
        for (CrawlResultData crawlResult : crawlResults) {
            ConsolidationData consolidation = createConsolidation(crawlResult, ruleMappings, migrationCtx.getIndex());
            if (consolidation != null) {
                consolidationDatas.add(consolidation);
                crawlEventPublisher.publish(new ConsolidationCompletedEvent(consolidation));
            }
        }
        return consolidationDatas;
    }

    private ConsolidationData createConsolidation(CrawlResultData crawlResult, List<DataMapping> ruleMappings,
            Mapping index) {

        ConsolidationData consolidation = new ConsolidationData();
        consolidation.setCategoryKey(crawlResult.getCategoryKey());
        consolidation.setItemKey(crawlResult.getItemKey());
        consolidation.setSiteKey(crawlResult.getSiteKey());
        consolidation.setResultKey(crawlResult.getResultKey());
        consolidation.setUrl(crawlResult.getUrl());
        ConsolidationData.createMd5Key(consolidation);

        Mapping dataMappingResults = mappingService.doMappingIndex(index, crawlResult.getDataAsMapping());
        if (dataMappingResults.isEmpty()) {
            logger.warn(String.format("No attributes are mapped: %s", consolidation.toString()));
            return null;
        }
        mappingService.applyRuleMapping(ruleMappings, dataMappingResults);
        ConsolidationData.createMd5Attribute(consolidation, dataMappingResults);
        
        for (String attribute : dataMappingResults.keySet()) {
            ConsolidationAttributeData consAttribute = new ConsolidationAttributeData();
            consAttribute.setConsolidationId(consolidation.getMd5Key());
            consAttribute.setName(attribute);
            
            Object value = dataMappingResults.get(attribute);
            if (CommonUtil.isObjectTextEmpty(value)) {
                continue;
            }
            if (value instanceof String) {
                consAttribute.setValue(value.toString());
            } else {
                consAttribute.setValue(Serialization.serialize(value));
            }
            consolidation.getAttributes().add(consAttribute);
        }
        
        return consolidation;
    }
}
