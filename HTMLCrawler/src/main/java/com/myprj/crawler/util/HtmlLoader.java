package com.myprj.crawler.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author DienNM (DEE)
 **/

public class HtmlLoader {
    private final static Logger logger = LoggerFactory.getLogger(Log.CRAWLER_LOG);

    public static Document download(String url) {
        int attempted = 0;
        while (true) {
            try {
                Document doc = Jsoup.connect(url)
                        .ignoreContentType(true)
                        .userAgent("Mozilla")
                        .referrer("http://www.google.com")
                        .timeout(10000).get();
                return doc;
            } catch (Exception e) {
                logger.error("Dowloaded {} failed. Attempted: {}. Error: ", url, attempted, e.getMessage());
                attempted++;
                if (attempted > 5) {
                    return null;
                }
            }
        }
    }

}
