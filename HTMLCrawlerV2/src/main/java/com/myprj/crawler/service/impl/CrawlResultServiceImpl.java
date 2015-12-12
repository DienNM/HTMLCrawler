package com.myprj.crawler.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.myprj.crawler.domain.crawl.CrawlResultData;
import com.myprj.crawler.model.crawl.CrawlResultId;
import com.myprj.crawler.model.crawl.CrawlResultModel;
import com.myprj.crawler.repository.CrawlResultRepository;
import com.myprj.crawler.service.CrawlResultService;

/**
 * @author DienNM (DEE)
 */
@Service
public class CrawlResultServiceImpl implements CrawlResultService {

    private Logger logger = LoggerFactory.getLogger(CrawlResultServiceImpl.class);

    @Autowired
    private CrawlResultRepository crawlResultRepository;

    @Override
    @Transactional
    public CrawlResultData saveOrUpdate(CrawlResultData crawlResult) {
        CrawlResultModel newObject = new CrawlResultModel();
        CrawlResultData.toModel(crawlResult, newObject);
        
        CrawlResultModel crawlResultModel = crawlResultRepository.find(newObject.getId());
        if (crawlResultModel == null) {
            crawlResultRepository.save(newObject);
        } else {
            crawlResultModel.setDetail(newObject.getDetail());
            crawlResultModel.setRequestId(newObject.getRequestId());
            crawlResultModel.setStatus(newObject.getStatus());
            crawlResultModel.setUrl(newObject.getUrl());
            crawlResultRepository.update(crawlResultModel);
        }
        CrawlResultData persisted = new CrawlResultData();
        CrawlResultData.toData(crawlResultModel, persisted);

        return persisted;
    }
    
    @Override
    public CrawlResultData get(String siteKey, String categoryKey, String itemKey, String resultKey) {
        CrawlResultId id = new CrawlResultId();
        id.setCategoryKey(categoryKey);
        id.setItemKey(itemKey);
        id.setSiteKey(siteKey);
        id.setResultKey(resultKey);
        
        CrawlResultModel crawlResultModel = crawlResultRepository.find(id);
        if (crawlResultModel == null) {
            logger.warn("Crawl Result " + id + " not found");
            return null;
        }

        CrawlResultData persisted = new CrawlResultData();
        CrawlResultData.toData(crawlResultModel, persisted);

        return persisted;
    }

    @Override
    public List<CrawlResultData> getByItemKey(String itemKey) {
        List<CrawlResultModel> crawlResultModels = crawlResultRepository.findByItemKey(itemKey);
        List<CrawlResultData> crawlResultDatas = new ArrayList<CrawlResultData>();
        CrawlResultData.toDatas(crawlResultModels, crawlResultDatas);

        return crawlResultDatas;
    }

    @Override
    public List<CrawlResultData> getByCategoryKey(String categoryKey) {
        List<CrawlResultModel> crawlResultModels = crawlResultRepository.findByCategoryKey(categoryKey);
        List<CrawlResultData> crawlResultDatas = new ArrayList<CrawlResultData>();
        CrawlResultData.toDatas(crawlResultModels, crawlResultDatas);

        return crawlResultDatas;
    }

    @Override
    public List<CrawlResultData> getByRequestId(String requestId) {
        List<CrawlResultModel> crawlResultModels = crawlResultRepository.findByRequestId(requestId);
        List<CrawlResultData> crawlResultDatas = new ArrayList<CrawlResultData>();
        CrawlResultData.toDatas(crawlResultModels, crawlResultDatas);

        return crawlResultDatas;
    }

}
