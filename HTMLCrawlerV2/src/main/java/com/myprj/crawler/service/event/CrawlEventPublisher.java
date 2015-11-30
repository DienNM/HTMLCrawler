package com.myprj.crawler.service.event;

import com.myprj.crawler.service.event.impl.CrawlEvent;


/**
 * @author DienNM (DEE)
 */

public interface CrawlEventPublisher {
    
    void register(CrawlEventListener<? extends CrawlEvent> event);
    
    void publish(CrawlEvent event);
    
}
