package com.myprj.crawler.domain;

/**
 * @author DienNM (DEE)
 */

public class Link {
    
    private String link;
    
    private int fromPage;
    
    private int toPage;
    
    private boolean active = false;
    
    public Link(String link) {
        this.link = link;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public int getFromPage() {
        return fromPage;
    }

    public void setFromPage(int startPage) {
        this.fromPage = startPage;
    }

    public int getToPage() {
        return toPage;
    }

    public void setToPage(int toPage) {
        this.toPage = toPage;
    }
    
}
