package com.myprj.crawler.service;

import com.myprj.crawler.domain.WorkerContext;
import com.myprj.crawler.exception.WorkerException;
import com.myprj.crawler.model.crawl.WorkerItemModel;
import com.myprj.crawler.service.cache.AttributeCacheService;
import com.myprj.crawler.service.event.CrawlEventPublisher;

/**
 * @author DienNM (DEE)
 */

public interface WorkerService {
    
    void doWork(WorkerContext workerCtx, WorkerItemModel workerItem) throws WorkerException;
    
    void setAttributeCacheService(AttributeCacheService attributeCacheService);
    
    void setCrawlEventPublisher(CrawlEventPublisher crawlEventPublisher);
}
