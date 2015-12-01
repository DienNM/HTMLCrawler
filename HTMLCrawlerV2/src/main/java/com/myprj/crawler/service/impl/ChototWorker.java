package com.myprj.crawler.service.impl;


/**
 * @author DienNM (DEE)
 */

public class ChototWorker extends DefaultPagingWorkerService {
    
    @Override
    protected boolean isValidLinkOfListTargetItem(String url) {
        return url != null;
    }
    
    @Override
    protected String updateUrlBeforeCrawling(String url) {
        return url.replace("www.chotot.vn", "m.chotot.vn");
    }
    
    @Override
    protected String updateUrlFormat(String url) {
        if(url.endsWith("#")) {
            url = url.substring(0, url.lastIndexOf("#"));
        } 
        if(url.endsWith("f=p")) {
            url = url + "&o=%s";
        } else if(url.endsWith("f=c")) {
            url = url + "&o=%s";
        } else {
            url = url + "?o=%s";
        }
        return url;
    }
}
