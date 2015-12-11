package com.myprj.crawler.util;

import java.util.HashMap;
import java.util.Map;

/**
 * @author DienNM (DEE)
 */

public final class Config {

    private static Map<String, String> configs = new HashMap<String, String>();

    public static void add(String key, String value) {
        configs.put(key, value);
    }

    public static String get(String key) {
        return configs.get(key);
    }
    
}
