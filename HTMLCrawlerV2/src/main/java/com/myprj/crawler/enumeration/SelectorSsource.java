package com.myprj.crawler.enumeration;

/**
 * @author DienNM (DEE)
 */

public enum SelectorSsource {
    
    I("Internal"),
    E("External");
    
    private String text;
    
    private SelectorSsource(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
    
}
