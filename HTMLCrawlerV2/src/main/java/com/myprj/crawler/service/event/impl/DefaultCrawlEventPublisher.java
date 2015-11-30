package com.myprj.crawler.service.event.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.myprj.crawler.service.event.CrawlEventListener;
import com.myprj.crawler.service.event.CrawlEventPublisher;

/**
 * @author DienNM (DEE)
 */

public class DefaultCrawlEventPublisher implements CrawlEventPublisher {

    private Map<String, List<CrawlEventListener<? extends CrawlEvent>>> listeners = 
            new HashMap<String, List<CrawlEventListener<? extends CrawlEvent>>>();

    @Override
    public synchronized void register(CrawlEventListener<? extends CrawlEvent> listener) {
        String type = listener.getEventType().getCanonicalName();
        List<CrawlEventListener<? extends CrawlEvent>> listenersByTypes = listeners.get(type);
        if(listenersByTypes == null) {
            listenersByTypes = new ArrayList<CrawlEventListener<? extends CrawlEvent>>();
        }
        listenersByTypes.add(listener);
        listeners.put(type, listenersByTypes);
    }

    @Override
    public synchronized void publish(CrawlEvent event) {
        String type = event.getClass().getCanonicalName();
        List<CrawlEventListener<? extends CrawlEvent>> listenersByTypes = listeners.get(type);
        if(listenersByTypes == null) {
            return;
        }
        for(CrawlEventListener<? extends CrawlEvent> listenerByType : listenersByTypes) {
            listenerByType.handle(event);
        }
    }

}
