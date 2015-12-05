package com.myprj.crawler.service.crawl.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.myprj.crawler.enumeration.CrawlerHandlerType;
import com.myprj.crawler.service.crawl.Worker;

/**
 * @author DienNM (DEE)
 */
@Service("defaultCrawlerService")
public class DefaultCrawlerService extends AbstractCrawler {
    
    
    @Autowired
    @Qualifier("defaultWorker")
    private Worker workerService;

    @Override
    protected Worker getWorker() {
        return workerService;
    }
    
    @Override
    public void registerHandler() {
        crawlerHandler.register(CrawlerHandlerType.none.name(), this);
    }

}
