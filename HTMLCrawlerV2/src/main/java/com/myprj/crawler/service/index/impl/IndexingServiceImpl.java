package com.myprj.crawler.service.index.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.elasticsearch.client.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myprj.crawler.domain.Pageable;
import com.myprj.crawler.model.target.ConsolidationAttributeModel;
import com.myprj.crawler.model.target.ConsolidationModel;
import com.myprj.crawler.repository.target.ConsolidationAttributeRepository;
import com.myprj.crawler.repository.target.ConsolidationRepository;
import com.myprj.crawler.service.index.IndexingService;
import com.myprj.crawler.util.Config;
import com.myprj.crawler.util.ElasticSearchClient;

/**
 * @author DienNM (DEE)
 */
@Service
public class IndexingServiceImpl implements IndexingService {

    private Logger logger = LoggerFactory.getLogger(IndexingServiceImpl.class);

    @Autowired
    private ConsolidationAttributeRepository consolidationAttributeRepository;

    @Autowired
    private ConsolidationRepository consolidationRepository;

    @Override
    public void fullIndex() {
        int pageSize = 100;
        int pageIndex = 0;
        while (true) {
            Pageable pageable = new Pageable(pageSize, pageIndex++);
            List<ConsolidationModel> consolidations = consolidationRepository.find(pageable);
            if (consolidations.isEmpty()) {
                break;
            }
            List<String> consolidationIds = new ArrayList<String>();
            for (ConsolidationModel consolidationModel : consolidations) {
                consolidationIds.add(consolidationModel.getMd5Key());
            }
            List<ConsolidationAttributeModel> attributes = consolidationAttributeRepository
                    .findByConsolidationIds(consolidationIds);
            Map<String, Map<String, Object>> dataIndex = new HashMap<String, Map<String, Object>>();
            for(ConsolidationAttributeModel attribute : attributes) {
                Map<String, Object> document = dataIndex.get(attribute.getId().getConsolidationId());
                if(document == null) {
                    document = new HashMap<String, Object>();
                    dataIndex.put(attribute.getId().getConsolidationId(), document);
                }
                document.put(attribute.getId().getName(), attribute.getValue());
            }
            doIndex(dataIndex);
        }
    }

    private void doIndex(Map<String, Map<String, Object>> dataIndex) {
        try {
            Client client = ElasticSearchClient.getInstance();
            String indexName = Config.get("elasticsearch.index.name");
            String type = Config.get("elasticsearch.index.type");
            for(String key : dataIndex.keySet()) {
                Map<String, Object> document = dataIndex.get(key);
                client.prepareIndex(indexName, type, key).setSource(document)
                        .execute().actionGet();
            }
        } catch (Exception e) {
            logger.error("{}", e);
        }
    }

}
