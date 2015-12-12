package com.myprj.crawler.repository;

import java.util.List;

import com.myprj.crawler.model.crawl.CrawlResultId;
import com.myprj.crawler.model.crawl.CrawlResultModel;

/**
 * @author DienNM (DEE)
 */

public interface CrawlResultRepository extends GenericDao<CrawlResultModel, CrawlResultId> {
    
    List<CrawlResultModel> findByItemKey(String itemKey);
    
    List<CrawlResultModel> findByCategoryKey(String categoryKey);
    
    List<CrawlResultModel> findByRequestId(String requestId);
}
