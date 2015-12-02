package com.myprj.crawler.domain.config;

import java.util.HashMap;
import java.util.Map;

/**
 * @author DienNM (DEE)
 */

public class ItemContent {
    
    public static final String ROOT = "content".intern();
    public static final String EMPTY_TEXT = "EMPTY".intern();
    
    private Map<String, Object> content;
    private String url;
    
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
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    
}
