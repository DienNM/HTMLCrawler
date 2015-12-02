package com.myprj.crawler.service.event.impl;

import com.myprj.crawler.domain.crawl.CrawlResultData;


/**
 * @author DienNM (DEE)
 */
public class CrawlDetailCompletedEvent implements CrawlEvent {

    private static final long serialVersionUID = 1L;
    
    private CrawlResultData crawlResult;
    
    public CrawlDetailCompletedEvent() {
    }

    
    public CrawlDetailCompletedEvent(CrawlResultData crawlResult) {
        this.crawlResult = crawlResult;
    }
    
    public CrawlResultData getCrawlResult() {
        return crawlResult;
    }

    public void setCrawlResult(CrawlResultData crawlResult) {
        this.crawlResult = crawlResult;
    }

}
