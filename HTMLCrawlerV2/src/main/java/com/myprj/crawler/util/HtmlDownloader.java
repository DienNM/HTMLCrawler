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
                Thread.sleep(pausedTime);
                Document doc = Jsoup.connect(url)
                        .userAgent("Mozilla")
                        .referrer("http://www.google.com")
                        .timeout(10000).get();
                return doc;
            } catch (Exception e) {
                logger.error("Dowloaded {} failed. Attempted: {}. Error: {}", url, attempted, e.getMessage());
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
    
    public static void main(String[] args) {
        HtmlDownloader.downloadAndStore2File("http://www.lazada.vn/laptop-apple-macbook-air-mjve2-13inch-bac-1299964.html"
                + "", "/media/diennm/Working/abc.txt");
    }
    
}
