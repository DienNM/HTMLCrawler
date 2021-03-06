package com.myprj.crawler.service.crawl.impl.site;

import org.springframework.stereotype.Service;

import com.myprj.crawler.service.crawl.impl.DefaultWorker;

/**
 * @author DienNM (DEE)
 */

@Service("chototWorker")
public class ChototWorker extends DefaultWorker {

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
