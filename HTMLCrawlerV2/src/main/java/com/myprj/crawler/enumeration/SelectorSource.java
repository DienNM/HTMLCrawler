package com.myprj.crawler.enumeration;

/**
 * @author DienNM (DEE)
 */

public enum SelectorSource {
    
    I("Internal"),
    E("External");
    
    private String text;
    
    private SelectorSource(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
    
}
