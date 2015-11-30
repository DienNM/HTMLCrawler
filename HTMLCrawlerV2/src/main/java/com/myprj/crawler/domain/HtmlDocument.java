package com.myprj.crawler.domain;

import org.jsoup.nodes.Document;

/**
 * @author DienNM (DEE)
 */

public class HtmlDocument {
    
    private Document document;
    
    public HtmlDocument() {
    }
    
    public HtmlDocument(Document document) {
        this.document = document;
    }

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }
    
}
