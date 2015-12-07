package com.myprj.crawler.service.event.impl;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myprj.crawler.domain.crawl.CrawlResultData;
import com.myprj.crawler.service.CrawlResultService;
import com.myprj.crawler.service.event.CrawlEventListener;
import com.myprj.crawler.service.event.CrawlEventPublisher;

/**
 * @author DienNM (DEE)
 */
@Service
public class CrawlDetailCompletedEventListener implements CrawlEventListener<CrawlDetailCompletedEvent> {

    private Logger logger = LoggerFactory.getLogger(CrawlDetailCompletedEventListener.class);

    @Autowired
    private CrawlEventPublisher crawlEventPublisher;

    @Autowired
    private CrawlResultService crawlResultService;

    @Override
    @PostConstruct
    public void register() {
        crawlEventPublisher.register(this);
    }

    @Override
    public void handle(CrawlEvent event) {
        if (event instanceof CrawlDetailCompletedEvent) {
            CrawlDetailCompletedEvent crawlDetailCompletedEvent = (CrawlDetailCompletedEvent) event;
            CrawlResultData crawlResult = crawlDetailCompletedEvent.getCrawlResult();
            if (crawlResult.getDetail().isEmpty()) {
                logger.warn("Cannot crawl any information of " + crawlResult.getItemKey());
                return;
            }
            crawlResultService.save(crawlResult);
        }
    }

    @Override
    public Class<CrawlDetailCompletedEvent> getEventType() {
        return CrawlDetailCompletedEvent.class;
    }
}
