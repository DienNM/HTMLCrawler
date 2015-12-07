package com.myprj.crawler.domain.config;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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
    
    public static Map<String, Object> clone(ItemContent itemContent) {
        Map<String, Object> clone = new HashMap<String, Object>();
        Map<String, Object> current = itemContent.getContent();
        copy(current, clone);
        return clone;
    }
    
    @SuppressWarnings("unchecked")
    private static void copy(Map<String, Object> current, Map<String, Object> clone) {
        for(Entry<String, Object> entry : current.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            if(value instanceof Map) {
                Map<String, Object> map = (Map<String, Object>) value;
                Map<String, Object> mapResult = new HashMap<String, Object>();
                copy(map, mapResult);
                clone.put(key, mapResult);
            } else if(value instanceof List) {
                List<Object> list = (List<Object>) value;
                List<Object> listResult = new ArrayList<Object>();
                if(list == null || !(list.get(0) instanceof Map)) {
                    clone.put(key, listResult);
                } else {
                    Map<String, Object> map = (Map<String, Object>) list.get(0);
                    Map<String, Object> mapResult = new HashMap<String, Object>();
                    copy(map, mapResult);
                    listResult.add(mapResult);
                    clone.put(key, listResult);
                }
            } else if(value instanceof String) {
                clone.put(key, value);
            }
        }
    }
    
    public Map<String, Object> getContent() {
        return content;
    }
    public void setContent(Map<String, Object> content) {
        this.content = content;
    }
}
