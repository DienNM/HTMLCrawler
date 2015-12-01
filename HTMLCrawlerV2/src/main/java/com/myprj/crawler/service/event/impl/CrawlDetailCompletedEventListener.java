package com.myprj.crawler.service.event.impl;

import com.myprj.crawler.model.crawl.CrawlResultModel;
import com.myprj.crawler.service.event.CrawlEventListener;
import com.myprj.crawler.util.Serialization;

/**
 * @author DienNM (DEE)
 */

public class CrawlDetailCompletedEventListener implements CrawlEventListener<CrawlDetailCompletedEvent> {
    
    @Override
    public void handle(CrawlEvent event) {
        if (event instanceof CrawlDetailCompletedEvent) {
            CrawlDetailCompletedEvent crawlDetailCompletedEvent = (CrawlDetailCompletedEvent) event;
            CrawlResultModel crawlResult = crawlDetailCompletedEvent.getCrawlResult();
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
