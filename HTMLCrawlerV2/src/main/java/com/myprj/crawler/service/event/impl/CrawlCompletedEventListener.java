package com.myprj.crawler.service.event.impl;

import com.myprj.crawler.service.event.CrawlEventListener;
import com.myprj.crawler.util.Serialization;

/**
 * @author DienNM (DEE)
 */

public class CrawlCompletedEventListener implements CrawlEventListener<CrawlCompletedEvent> {

    @Override
    public void handle(CrawlEvent event) {
        if (event instanceof CrawlCompletedEvent) {
            CrawlCompletedEvent crawlCompletedEvent = (CrawlCompletedEvent) event;
            String a = Serialization.serialize(crawlCompletedEvent.getCrawlResult());
            System.out.println(a);
        }
    }

    @Override
    public Class<CrawlCompletedEvent> getEventType() {
        return CrawlCompletedEvent.class;
    }
}
