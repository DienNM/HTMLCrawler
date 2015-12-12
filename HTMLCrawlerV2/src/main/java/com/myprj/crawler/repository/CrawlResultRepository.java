package com.myprj.crawler.repository;

import java.util.List;

import com.myprj.crawler.domain.PageResult;
import com.myprj.crawler.domain.Pageable;
import com.myprj.crawler.model.crawl.CrawlResultId;
import com.myprj.crawler.model.crawl.CrawlResultModel;

/**
 * @author DienNM (DEE)
 */

public interface CrawlResultRepository extends GenericDao<CrawlResultModel, CrawlResultId> {
    
    List<CrawlResultModel> findByRequestId(String requestId);
    
    List<CrawlResultModel> find(String siteKey, String categoryKey, String itemKey, Pageable pageable);
    
    PageResult<CrawlResultModel> findPaging(String siteKey, String categoryKey, String itemKey, Pageable pageable);
}
