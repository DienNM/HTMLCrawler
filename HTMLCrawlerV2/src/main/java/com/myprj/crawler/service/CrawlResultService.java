package com.myprj.crawler.service;

import java.util.List;

import com.myprj.crawler.domain.crawl.CrawlResultData;

/**
 * @author DienNM (DEE)
 */

public interface CrawlResultService {
    
    CrawlResultData saveOrUpdate(CrawlResultData crawlResult);
    
    CrawlResultData get(long id);
    
    List<CrawlResultData> getByItemKey(String itemKey);
    
    List<CrawlResultData> getByCategoryKey(String categoryKey);
    
    List<CrawlResultData> getByRequestId(String requestId);
    
}
