package com.myprj.crawler.service.mapping;

import java.util.HashMap;
import java.util.Map;

/**
 * @author DienNM (DEE)
 */

public class Mapping extends HashMap<String, Object> {

    private static final long serialVersionUID = 1L;

    public Mapping() {
    }

    @SuppressWarnings("unchecked")
    public Mapping(Object object) {
        if (object instanceof Map) {
            putAll((Map<String, Object>) object);
        }
    }

    public void addData(Map<String, Object> mapping) {
        putAll(mapping);
    }
}
