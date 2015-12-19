package com.myprj.crawler.service.index.impl;

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
import com.myprj.crawler.repository.target.ConsolidationAttributeRepository;
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

    @Override
    public void fullIndex() {
        int pageSize = 300;
        int pageIndex = 0;
        while (true) {
            Pageable pageable = new Pageable(pageSize, pageIndex++);
            List<ConsolidationAttributeModel> attributes = consolidationAttributeRepository.find(pageable);
            if (attributes.isEmpty()) {
                break;
            }
            doIndex(attributes);
        }
    }

    private void doIndex(List<ConsolidationAttributeModel> attributes) {
        try {
            Client client = ElasticSearchClient.getInstance();
            String indexName = Config.get("elasticsearch.index.name");
            String type = Config.get("elasticsearch.index.type");
            for (ConsolidationAttributeModel attribute : attributes) {

                Map<String, Object> document = new HashMap<String, Object>();
                document.put("key", attribute.getId().getName());
                document.put("value", attribute.getValue());

                client.prepareIndex(indexName, type,
                        attribute.getId().getName() + "_" + attribute.getId().getConsolidationId()).setSource(document)
                        .execute().actionGet();
            }
        } catch (Exception e) {
            logger.error("{}", e);
        }
    }

}
