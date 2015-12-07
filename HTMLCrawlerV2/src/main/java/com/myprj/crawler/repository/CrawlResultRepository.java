package com.myprj.crawler.repository;

import java.util.List;

import com.myprj.crawler.model.crawl.CrawlResultModel;

/**
 * @author DienNM (DEE)
 */

public interface CrawlResultRepository extends GenericDao<CrawlResultModel, Long> {
    
    List<CrawlResultModel> findByItemKey(String itemKey);
    
    List<CrawlResultModel> findByCategoryKey(String categoryKey);
    
}
