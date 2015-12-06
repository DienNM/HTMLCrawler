package com.myprj.crawler.service;

import com.myprj.crawler.domain.crawl.CrawlHistoryData;

/**
 * @author DienNM (DEE)
 */

public interface CrawlHistoryService {
    
    CrawlHistoryData save(CrawlHistoryData crawlHistory);
    
    CrawlHistoryData update(CrawlHistoryData crawlHistory);
    
    CrawlHistoryData getLatest(long workerId);
    
}
