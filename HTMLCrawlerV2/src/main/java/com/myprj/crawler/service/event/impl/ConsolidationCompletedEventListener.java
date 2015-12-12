package com.myprj.crawler.service.event.impl;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myprj.crawler.domain.target.ConsolidationData;
import com.myprj.crawler.service.event.CrawlEventListener;
import com.myprj.crawler.service.event.CrawlEventPublisher;
import com.myprj.crawler.service.target.ConsolidationService;

/**
 * @author DienNM (DEE)
 */
@Service
public class ConsolidationCompletedEventListener implements CrawlEventListener<ConsolidationCompletedEvent> {

    @Autowired
    private CrawlEventPublisher crawlEventPublisher;

    @Autowired
    private ConsolidationService consolidationService;

    @Override
    @PostConstruct
    public void register() {
        crawlEventPublisher.register(this);
    }

    @Override
    public void handle(CrawlEvent event) {
        if (event instanceof ConsolidationCompletedEvent) {
            ConsolidationCompletedEvent consolidationEvent = (ConsolidationCompletedEvent) event;
            ConsolidationData consolidation = consolidationEvent.getConsolidation();
            consolidationService.saveOrUpdate(consolidation);
        }
    }

    @Override
    public Class<ConsolidationCompletedEvent> getEventType() {
        return ConsolidationCompletedEvent.class;
    }
}
