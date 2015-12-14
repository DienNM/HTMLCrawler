package com.myprj.crawler.util;

import java.util.Collection;

import org.apache.commons.lang3.StringUtils;

/**
 * @author DienNM (DEE)
 */

public final class CommonUtil {
    
    public static boolean isObjectTextEmpty(Object object) {
        if (object == null) {
            return true;
        }
        return StringUtils.isEmpty(object.toString().trim());
    }

    public static boolean isTextEmpty(String text) {
        if (StringUtils.isEmpty(text)) {
            return true;
        }
        return StringUtils.isEmpty(text.trim());
    }
    
    public static boolean isCollectionEmpty(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

}
