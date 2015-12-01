package com.myprj.crawler.service;

import com.myprj.crawler.exception.CrawlerException;
import com.myprj.crawler.model.crawl.WorkerModel;
import com.myprj.crawler.repository.CrawlHistoryRepository;
import com.myprj.crawler.repository.WorkerRepository;
/**
 * @author DienNM (DEE)
 **/
public interface CrawlerService {
    
    void crawl(long workerId) throws CrawlerException;
    
    void init(WorkerModel worker) throws CrawlerException;
    
    void destroy(WorkerModel worker);
    
    void setWorkerService(WorkerService workerService);
    
    void setCrawlHistoryRepository(CrawlHistoryRepository crawlHistoryRepository);
    
    void setWorkerRepository(WorkerRepository workerRepository);
}
