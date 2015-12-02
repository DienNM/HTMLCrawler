package com.myprj.crawler.service.crawl.impl;

/**
 * @author DienNM (DEE)
 */

public class ChototWorker extends DefaultWorkerService {

    @Override
    protected boolean isValidLink(String url) {
        return url != null;
    }

    @Override
    protected String updateURLPagingFormat(String url) {
        if (url.endsWith("#")) {
            url = url.substring(0, url.lastIndexOf("#"));
        }
        if (url.endsWith("f=p")) {
            url = url + "&o=%s";
        } else if (url.endsWith("f=c")) {
            url = url + "&o=%s";
        } else {
            url = url + "?o=%s";
        }
        return updateURLPagingFormat(url);
    }
}
