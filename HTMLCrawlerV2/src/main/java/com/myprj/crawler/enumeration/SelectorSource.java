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
    
    public static SelectorSource fromString(String text) {
        if(text == null) {
            return I;
        }
        for(SelectorSource s : SelectorSource.values()) {
            if(s.name().toLowerCase().equals(text.toLowerCase())) {
                return s;
            }
        }
        return I;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
    
}
