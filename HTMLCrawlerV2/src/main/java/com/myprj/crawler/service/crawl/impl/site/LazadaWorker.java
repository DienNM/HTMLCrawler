package com.myprj.crawler.service.crawl.impl.site;

import org.springframework.stereotype.Service;

import com.myprj.crawler.service.crawl.impl.DefaultWorkerService;

/**
 * @author DienNM (DEE)
 */

@Service("lazadaWorker")
public class LazadaWorker extends DefaultWorkerService {

    @Override
    protected boolean isValidLink(String url) {
        return url != null && url.startsWith("http://www.lazada.vn/");
    }

}
