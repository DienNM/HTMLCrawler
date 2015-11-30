package com.myprj.crawler.util;

import java.io.File;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author DienNM (DEE)
 **/

public class HtmlDownloader {
    
    private final static Logger logger = LoggerFactory.getLogger(HtmlDownloader.class);
    
    public static Document download(String url) {
        return download(url, 1000, 5);
    }
    
    public static Document download(String url, int pausedTime, int attemptTimes) {
        int attempted = 0;
        while (true) {
            try {
                Document doc = Jsoup.connect(url)
                        .ignoreContentType(true)
                        .userAgent("Mozilla")
                        .referrer("http://www.google.com")
                        .timeout(pausedTime).get();
                return doc;
            } catch (Exception e) {
                logger.error("Dowloaded {} failed. Attempted: {}. Error: ", url, attempted, e.getMessage());
                attempted++;
                if (attempted > attemptTimes) {
                    return null;
                }
            }
        }
    }
    
    /**
     * Used for check HTML content
     */
    public static void downloadAndStore2File(String url, String outputFile) {
        Document doc = download(url);
        if(doc != null) {
            StreamUtil.write(new File(outputFile), doc.html(), false);
        }
    }

}
