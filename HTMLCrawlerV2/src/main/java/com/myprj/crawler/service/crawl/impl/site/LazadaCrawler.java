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
@Service("lazadaCrawler")
public class LazadaCrawler extends AbstractCrawler {
    
    @Autowired
    @Qualifier("lazadaWorker")
    private Worker workerService;

    @Override
    public Worker getWorker() {
        return workerService;
    }
    
    @Override
    @PostConstruct
    public void registerHandler() {
        crawlerHandler.register(CrawlerHandlerType.lazada.name(), this);
    }
}
