package com.myprj.crawler.service;

import java.util.List;

import com.myprj.crawler.domain.PageResult;
import com.myprj.crawler.domain.Pageable;
import com.myprj.crawler.domain.crawl.CrawlResultData;

/**
 * @author DienNM (DEE)
 */

public interface CrawlResultService {

    CrawlResultData saveOrUpdate(CrawlResultData crawlResult);

    CrawlResultData get(String siteKey, String categoryKey, String itemKey, String resultKey);

    List<CrawlResultData> get(String siteKey, String categoryKey, String itemKey, Pageable pageable);

    PageResult<CrawlResultData> getPaging(String siteKey, String categoryKey, String itemKey, Pageable pageable);

    List<CrawlResultData> getByRequestId(String requestId);

}
