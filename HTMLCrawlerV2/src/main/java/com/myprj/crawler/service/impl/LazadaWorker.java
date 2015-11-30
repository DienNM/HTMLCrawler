package com.myprj.crawler.service.impl;

/**
 * @author DienNM (DEE)
 */

public class LazadaWorker extends DefaultWorkerService {
    
    @Override
    protected boolean isValidLinkOfListTargetItem(String url) {
        return url != null && url.startsWith("http://www.lazada.vn/");
    }
    
}
