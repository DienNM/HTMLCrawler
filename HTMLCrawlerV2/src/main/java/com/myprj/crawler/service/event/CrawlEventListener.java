package com.myprj.crawler.service.event;

/**
 * @author DienNM (DEE)
 */

public interface CrawlEventListener<T> {
    
    Class<T> getEventType();
    
    void handle(T event);
    
}

