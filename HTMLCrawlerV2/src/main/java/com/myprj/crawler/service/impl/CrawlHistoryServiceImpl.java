package com.myprj.crawler.service.impl;

import java.security.InvalidParameterException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.myprj.crawler.domain.crawl.CrawlHistoryData;
import com.myprj.crawler.model.crawl.CrawlHistoryModel;
import com.myprj.crawler.repository.CrawlHistoryRepository;
import com.myprj.crawler.service.CrawlHistoryService;

/**
 * @author DienNM (DEE)
 */
@Service
public class CrawlHistoryServiceImpl implements CrawlHistoryService {
    
    @Autowired
    private CrawlHistoryRepository crawlHistoryRepository;
    
    @Override
    @Transactional
    public CrawlHistoryData save(CrawlHistoryData crawlHistory) {
        CrawlHistoryModel crawlHistoryModel = new CrawlHistoryModel();
        CrawlHistoryData.toModel(crawlHistory, crawlHistoryModel);
        
        crawlHistoryRepository.save(crawlHistoryModel);
        CrawlHistoryData persisted = new CrawlHistoryData();
        CrawlHistoryData.toData(crawlHistoryModel, persisted);
        
        return persisted;
    }

    @Override
    @Transactional
    public CrawlHistoryData update(CrawlHistoryData crawlHistory) {
        CrawlHistoryModel crawlHistoryModel = crawlHistoryRepository.find(crawlHistory.getId());
        if(crawlHistoryModel == null) {
            throw new InvalidParameterException("Crawl Histrory " + crawlHistory.getId() + " not found");
        }
        CrawlHistoryData.toModel(crawlHistory, crawlHistoryModel);
        
        crawlHistoryRepository.update(crawlHistoryModel);
        CrawlHistoryData persisted = new CrawlHistoryData();
        CrawlHistoryData.toData(crawlHistoryModel, persisted);
        
        return persisted;
    }

    @Override
    @Transactional
    public CrawlHistoryData getLatest(long workerId) {
        
        CrawlHistoryModel crawlHistoryModel = crawlHistoryRepository.findLatest(workerId);
        if(crawlHistoryModel == null) {
            return null;
        }
        CrawlHistoryData persisted = new CrawlHistoryData();
        CrawlHistoryData.toData(crawlHistoryModel, persisted);
        
        return persisted;
    }

}
