package com.myprj.crawler.service.event.impl;

import com.myprj.crawler.model.crawl.CrawlResultModel;


/**
 * @author DienNM (DEE)
 */

public class CrawlDetailCompletedEvent implements CrawlEvent {

    private static final long serialVersionUID = 1L;
    
    private CrawlResultModel crawlResult;
    
    public CrawlDetailCompletedEvent() {
    }

    
    public CrawlDetailCompletedEvent(CrawlResultModel crawlResult) {
        this.crawlResult = crawlResult;
    }
    
    public CrawlResultModel getCrawlResult() {
        return crawlResult;
    }

    public void setCrawlResult(CrawlResultModel crawlResult) {
        this.crawlResult = crawlResult;
    }

}
