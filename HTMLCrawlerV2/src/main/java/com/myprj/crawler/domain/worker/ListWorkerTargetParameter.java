package com.myprj.crawler.domain.worker;

import java.io.Serializable;

/**
 * @author DienNM (DEE)
 */

public class ListWorkerTargetParameter implements Serializable{
    
    private static final long serialVersionUID = 1L;

    private int toPage;
    
    private int fromPage;
    
    private int currentPage;
    
    private String urlAttribute = "href";
    
    public ListWorkerTargetParameter() {
    }
    
    public int getToPage() {
        return toPage;
    }

    public void setToPage(int toPage) {
        this.toPage = toPage;
    }

    public int getFromPage() {
        return fromPage;
    }

    public void setFromPage(int fromPage) {
        this.fromPage = fromPage;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public String getUrlAttribute() {
        return urlAttribute;
    }

    public void setUrlAttribute(String urlAttribute) {
        this.urlAttribute = urlAttribute;
    }
    
}
