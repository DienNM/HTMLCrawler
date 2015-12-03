package com.myprj.crawler.service.crawl.impl.site;

import org.springframework.stereotype.Service;

import com.myprj.crawler.service.crawl.impl.DefaultWorker;

/**
 * @author DienNM (DEE)
 */

@Service("lazadaWorker")
public class LazadaWorker extends DefaultWorker {

    @Override
    protected boolean isValidLink(String url) {
        return url != null && url.startsWith("http://www.lazada.vn/");
    }

}
