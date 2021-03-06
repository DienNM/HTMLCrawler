package com.myprj.crawler.service.crawl.impl.site;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.myprj.crawler.enumeration.CrawlerHandlerType;
import com.myprj.crawler.service.crawl.Worker;
import com.myprj.crawler.service.crawl.impl.AbstractCrawler;

/**
 * @author DienNM (DEE)
 */

@Service("choTotCrawler")
public class ChoTotCrawler extends AbstractCrawler{
    
    @Autowired
    @Qualifier("chototWorker")
    private Worker workerService;
    
    @Override
    protected Worker getWorker() {
        return workerService;
    }
    
    @Override
    @PostConstruct
    public void registerHandler() {
        crawlerHandler.register(CrawlerHandlerType.chotot.name(), this);
    }
}
