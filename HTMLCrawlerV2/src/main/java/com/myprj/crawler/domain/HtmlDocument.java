package com.myprj.crawler.domain;

import org.jsoup.nodes.Document;

/**
 * @author DienNM (DEE)
 */

public class HtmlDocument {
    
    private Document document;
    
    private String url;
    
    public HtmlDocument() {
    }
    
    public HtmlDocument(Document document) {
        this.document = document;
        if(document != null) {
            url = document.baseUri();
        }
    }

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    
}
