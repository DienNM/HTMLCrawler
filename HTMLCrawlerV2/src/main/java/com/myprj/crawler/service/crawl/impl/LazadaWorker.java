package com.myprj.crawler.service.crawl.impl;

/**
 * @author DienNM (DEE)
 */

public class LazadaWorker extends DefaultWorkerService {

    @Override
    protected boolean isValidLink(String url) {
        return url != null && url.startsWith("http://www.lazada.vn/");
    }

}
