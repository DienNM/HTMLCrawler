package com.myprj.crawler.service.event.impl;

import com.myprj.crawler.service.event.CrawlEventListener;
import com.myprj.crawler.util.Serialization;

/**
 * @author DienNM (DEE)
 */

public class CrawlCompletedEventListener implements CrawlEventListener<CrawlCompletedEvent> {

    @Override
    public void handle(CrawlCompletedEvent event) {
        System.out.println(Serialization.serialize(event.getCrawlResult()));
    }
    
    @Override
    public Class<CrawlCompletedEvent> getEventType() {
        return CrawlCompletedEvent.class;
    }
}
