package com.myprj.crawler.domain.config;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author DienNM (DEE)
 */

public class ItemContent implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    public static final String ROOT = "content".intern();
    public static final String EMPTY_TEXT = "EMPTY".intern();
    
    private Map<String, Object> content;
    
    public ItemContent() {
        content = new HashMap<String, Object>();
        content.put(ROOT, new HashMap<String, Object>());
    }
    
    public Map<String, Object> getContent() {
        return content;
    }
    public void setContent(Map<String, Object> content) {
        this.content = content;
    }
}
