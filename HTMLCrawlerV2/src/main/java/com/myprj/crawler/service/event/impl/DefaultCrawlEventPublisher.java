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

    private Map<String, List<CrawlEventListener<AbstractEvent>>> listeners = new HashMap<String, List<CrawlEventListener<AbstractEvent>>>();

    @Override
    public synchronized void register(CrawlEventListener<AbstractEvent> listener) {
        String type = listener.getEventType().getCanonicalName();
        List<CrawlEventListener<AbstractEvent>> listenersByTypes = listeners.get(type);
        if(listenersByTypes == null) {
            listenersByTypes = new ArrayList<CrawlEventListener<AbstractEvent>>();
        }
        listenersByTypes.add(listener);
        listeners.put(listener.getClass().getCanonicalName(), listenersByTypes);
    }

    @Override
    public synchronized void publish(AbstractEvent event) {
        String type = event.getClass().getCanonicalName();
        List<CrawlEventListener<AbstractEvent>> listenersByTypes = listeners.get(type);
        if(listenersByTypes == null) {
            return;
        }
        for(CrawlEventListener<AbstractEvent> listenerByType : listenersByTypes) {
            listenerByType.handle(event);
        }
    }

}
