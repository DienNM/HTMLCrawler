package com.myprj.crawler.service.crawl.impl;

/**
 * @author DienNM (DEE)
 */

public class LazadaWorker extends DefaultPagingWorkerService {
    
    @Override
    protected boolean isValidLinkOfListTargetItem(String url) {
        return url != null && url.startsWith("http://www.lazada.vn/");
    }
    
}
