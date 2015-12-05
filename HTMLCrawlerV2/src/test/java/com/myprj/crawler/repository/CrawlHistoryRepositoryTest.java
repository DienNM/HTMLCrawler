package com.myprj.crawler.repository;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.myprj.crawler.AbstractTest;
import com.myprj.crawler.enumeration.CrawlStatus;
import com.myprj.crawler.model.crawl.CrawlHistoryModel;

/**
 * @author DienNM (DEE)
 */

public class CrawlHistoryRepositoryTest extends AbstractTest {
    
    @Autowired
    private CrawlHistoryRepository crawlHistoryRepository;
    
    @Test
    @Transactional
    public void testSave() {
        
        // Save 1
        CrawlHistoryModel crawlHistory = new CrawlHistoryModel();
        crawlHistory.setStatus(CrawlStatus.ERROR);
        crawlHistory.setWorkerId(1);
        crawlHistoryRepository.save(crawlHistory);
        
        CrawlHistoryModel persisted = crawlHistoryRepository.findLatest(1);
        Assert.assertNotNull(persisted);
        Assert.assertEquals(CrawlStatus.ERROR, persisted.getStatus());
        
        // Save 2
        crawlHistory = new CrawlHistoryModel();
        crawlHistory.setStatus(CrawlStatus.CRAWLING);
        crawlHistory.setWorkerId(1);
        crawlHistoryRepository.save(crawlHistory);
        
        // Save 3
        crawlHistory = new CrawlHistoryModel();
        crawlHistory.setStatus(CrawlStatus.ERROR);
        crawlHistory.setWorkerId(2);
        crawlHistoryRepository.save(crawlHistory);
        
        persisted = crawlHistoryRepository.findLatest(1);
        Assert.assertNotNull(persisted);
        Assert.assertEquals(CrawlStatus.CRAWLING, persisted.getStatus());
        
        persisted = crawlHistoryRepository.findLatest(2);
        Assert.assertNotNull(persisted);
        Assert.assertEquals(CrawlStatus.ERROR, persisted.getStatus());
        
        List<CrawlHistoryModel> persisteds = crawlHistoryRepository.findByWorkerId(1);
        Assert.assertEquals(2, persisteds.size());
        
        persisteds = crawlHistoryRepository.findByWorkerId(2);
        Assert.assertEquals(1, persisteds.size());
        
        persisteds = crawlHistoryRepository.findByWorkerId(3);
        Assert.assertEquals(0, persisteds.size());
    }
    
}
