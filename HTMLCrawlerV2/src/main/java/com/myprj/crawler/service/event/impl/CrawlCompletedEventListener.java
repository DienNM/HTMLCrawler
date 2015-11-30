package com.myprj.crawler.service.event.impl;

import com.myprj.crawler.model.crawl.CrawlResultModel;
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

            CrawlResultModel crawlResult = Serialization.deserialize(a, CrawlResultModel.class);
            System.out.println(crawlResult);
        }
    }

    @Override
    public Class<CrawlCompletedEvent> getEventType() {
        return CrawlCompletedEvent.class;
    }
}
