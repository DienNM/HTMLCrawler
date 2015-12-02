package com.myprj.crawler.domain;

import com.myprj.crawler.enumeration.Level;
import com.myprj.crawler.enumeration.CrawlType;

/**
 * @author DienNM (DEE)
 */

public class ErrorLink {
    
    private String url;
    private String message;
    private Level level;
    private CrawlType crawlType;
    
    public ErrorLink() {
    }
    
    public ErrorLink(String url) {
        this.url = url;
    }
    
    @Override
    public String toString() {
        return String.format("Level=%s, Target Type=%s, URL=%s, Message=%s", level, crawlType, url, message);
    }
    
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public Level getLevel() {
        return level;
    }
    public void setLevel(Level level) {
        this.level = level;
    }
    public CrawlType getCrawlType() {
        return crawlType;
    }
    public void setCrawlType(CrawlType crawlType) {
        this.crawlType = crawlType;
    }
    
}
