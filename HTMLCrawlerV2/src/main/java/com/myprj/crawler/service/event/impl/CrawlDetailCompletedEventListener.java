package com.myprj.crawler.service.event.impl;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myprj.crawler.domain.crawl.CrawlResultData;
import com.myprj.crawler.service.event.CrawlEventListener;
import com.myprj.crawler.service.event.CrawlEventPublisher;
import com.myprj.crawler.util.Serialization;

/**
 * @author DienNM (DEE)
 */
@Service
public class CrawlDetailCompletedEventListener implements CrawlEventListener<CrawlDetailCompletedEvent> {
    
    @Autowired
    private CrawlEventPublisher crawlEventPublisher;
    
    @Override
    @PostConstruct
    public void register() {
        crawlEventPublisher.register(this);
    }
    
    @Override
    public void handle(CrawlEvent event) {
        if (event instanceof CrawlDetailCompletedEvent) {
            CrawlDetailCompletedEvent crawlDetailCompletedEvent = (CrawlDetailCompletedEvent) event;
            CrawlResultData crawlResult = crawlDetailCompletedEvent.getCrawlResult();
            // Save or do something
            if(crawlResult.getDetail().isEmpty()) {
                System.out.println("Cannot crawl any information of " + crawlResult.getItemId());
            } else {
                System.out.println(Serialization.serialize(crawlResult));
            }
            
        }
    }

    @Override
    public Class<CrawlDetailCompletedEvent> getEventType() {
        return CrawlDetailCompletedEvent.class;
    }
}
