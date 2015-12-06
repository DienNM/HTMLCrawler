package com.myprj.crawler.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.myprj.crawler.domain.crawl.CrawlResultData;
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
    public CrawlResultData save(CrawlResultData crawlResult) {
        CrawlResultModel crawlResultModel = new CrawlResultModel();
        CrawlResultData.toModel(crawlResult, crawlResultModel);
        crawlResultRepository.save(crawlResultModel);
        
        CrawlResultData persisted = new CrawlResultData();
        CrawlResultData.toData(crawlResultModel, persisted);
        
        return persisted;
    }

    @Override
    @Transactional
    public CrawlResultData get(long id) {
        CrawlResultModel crawlResultModel = crawlResultRepository.find(id);
        if(crawlResultModel == null) {
            logger.warn("Crawl Result " + id + " not found");
            return null;
        }

        CrawlResultData persisted = new CrawlResultData();
        CrawlResultData.toData(crawlResultModel, persisted);
        
        return persisted;
    }

    @Override
    @Transactional
    public List<CrawlResultData> getByItemId(long itemId) {
        
        List<CrawlResultModel> crawlResultModels = crawlResultRepository.findByItemId(itemId);
        List<CrawlResultData> crawlResultDatas = new ArrayList<CrawlResultData>();
        CrawlResultData.toDatas(crawlResultModels, crawlResultDatas);
        
        return crawlResultDatas;
    }

}
