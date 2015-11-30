package com.myprj.crawler.service.event;

import com.myprj.crawler.service.event.impl.CrawlEvent;

/**
 * @author DienNM (DEE)
 */

public interface CrawlEventListener<T extends CrawlEvent> {
    
    Class<T> getEventType();
    
    void handle(CrawlEvent event); 
    
}

