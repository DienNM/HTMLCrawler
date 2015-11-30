package com.myprj.crawler.service;

import com.myprj.crawler.exception.CrawlerException;
import com.myprj.crawler.model.crawl.WorkerModel;
import com.myprj.crawler.repository.CrawlHistoryRepository;
/**
 * @author DienNM (DEE)
 **/
public interface CrawlerService {
    
    void crawl(WorkerModel worker) throws CrawlerException;
    
    void init(WorkerModel worker);
    
    void destroy(WorkerModel worker);
    
    void setWorkerService(WorkerService workerService);
    
    void setCrawlHistoryRepository(CrawlHistoryRepository crawlHistoryRepository);
    
}
