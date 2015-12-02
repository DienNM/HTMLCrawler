package com.myprj.crawler.domain.worker;

import com.myprj.crawler.enumeration.Level;
import com.myprj.crawler.enumeration.WorkerItemType;

/**
 * @author DienNM (DEE)
 */

public class ErrorLink {
    
    private String url;
    private String message;
    private Level level;
    private WorkerItemType targetType;
    
    public ErrorLink() {
    }
    
    public ErrorLink(String url) {
        this.url = url;
    }
    
    @Override
    public String toString() {
        return String.format("Level=%s, Target Type=%s, URL=%s, Message=%s", level, targetType, url, message);
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
    public WorkerItemType getTargetType() {
        return targetType;
    }
    public void setTargetType(WorkerItemType targetType) {
        this.targetType = targetType;
    }
    
}
