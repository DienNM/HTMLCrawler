package com.myprj.crawler.repository;

import java.util.List;

import com.myprj.crawler.model.crawl.CrawlHistoryModel;

/**
 * @author DienNM (DEE)
 */

public interface CrawlHistoryRepository {
    
    CrawlHistoryModel save(CrawlHistoryModel crawlHistory);
    
    CrawlHistoryModel findLatest(long workerId);
    
    List<CrawlHistoryModel> findByWorkerId(long workerId);
}
