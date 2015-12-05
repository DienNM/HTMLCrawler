package com.myprj.crawler.service.crawl;

import com.myprj.crawler.domain.RequestCrawl;
import com.myprj.crawler.domain.crawl.WorkerData;
import com.myprj.crawler.exception.CrawlerException;
/**
 * @author DienNM (DEE)
 **/
public interface CrawlerService {
    
    void registerHandler();
    
    void destroy(WorkerData worker);
    
    void init(WorkerData worker) throws CrawlerException;
    
    void crawl(RequestCrawl request) throws CrawlerException;
}
