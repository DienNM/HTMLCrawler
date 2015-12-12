package com.myprj.crawler.service.event.impl;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private Logger logger = LoggerFactory.getLogger(ConsolidationCompletedEventListener.class);

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
            logger.info("{}", consolidation);
        }
    }

    @Override
    public Class<ConsolidationCompletedEvent> getEventType() {
        return ConsolidationCompletedEvent.class;
    }
}
