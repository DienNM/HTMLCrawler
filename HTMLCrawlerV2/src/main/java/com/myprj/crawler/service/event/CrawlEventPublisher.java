package com.myprj.crawler.service.event;

import com.myprj.crawler.service.event.impl.AbstractEvent;


/**
 * @author DienNM (DEE)
 */

public interface CrawlEventPublisher {
    
    void register(CrawlEventListener<AbstractEvent> event);
    
    void publish(AbstractEvent event);
    
}
