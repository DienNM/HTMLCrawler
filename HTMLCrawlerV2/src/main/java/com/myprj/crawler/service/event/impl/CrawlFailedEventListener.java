package com.myprj.crawler.service.event.impl;

import com.myprj.crawler.service.event.CrawlEventListener;

/**
 * @author DienNM (DEE)
 */

public class CrawlFailedEventListener implements CrawlEventListener<CrawlFailedEvent> {

    @Override
    public void handle(CrawlEvent event) {
        if (event instanceof CrawlFailedEvent) {
            System.out.println("Crawled failed");
        }
    }

    @Override
    public Class<CrawlFailedEvent> getEventType() {
        return CrawlFailedEvent.class;
    }
}
