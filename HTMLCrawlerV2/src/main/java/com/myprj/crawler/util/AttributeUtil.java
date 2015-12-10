package com.myprj.crawler.util;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import com.myprj.crawler.domain.config.ItemContent;
import com.myprj.crawler.enumeration.AttributeType;

/**
 * @author DienNM (DEE)
 */

public final class AttributeUtil {

    public static final String SEP_KEY = Pattern.quote("|");

    public static Iterator<String> parseAttributeNames(String attributeId) {
        List<String> names = new LinkedList<String>();
        String[] attNames = attributeId.split(SEP_KEY);
        for (String attName : attNames) {
            names.add(attName);
        }
        return names.iterator();
    }

    @SuppressWarnings("unchecked")
    public static Map<String, Object> getObject(ItemContent itemContent, String attributeId) {
        Iterator<String> attNames = parseAttributeNames(attributeId);
        attNames.next(); // Ignore ItemID
        return (Map<String, Object>) getObject(itemContent.getContent(), attNames);
    }

    @SuppressWarnings("unchecked")
    private static Object getObject(Map<String, Object> currentObj, Iterator<String> attNames) {
        if (!attNames.hasNext()) {
            return currentObj;
        }
        String attName = attNames.next();
        Object object = currentObj.get(attName);
        if (object == null || object instanceof String) {
            if(attNames.hasNext()) {
                return getObject(currentObj, attNames);
            }
            return currentObj;
        }
        if (object instanceof List) {
            List<Object> list = (List<Object>) object;
            if (contentObject(list)) {
                return getObject((Map<String, Object>) list.get(0), attNames);
            }
        }
        if (object instanceof Map) {
            return getObject((Map<String, Object>) object, attNames);
        }
        return currentObj;
    }

    public static boolean contentObject(List<Object> list) {
        if (list == null || list.isEmpty()) {
            return false;
        }
        Object object = list.get(0);
        return object instanceof Map;
    }

    public static AttributeType getElementType(List<Object> list) {
        if (list == null || list.isEmpty()) {
            return AttributeType.TEXT;
        }
        Object object = list.get(0);
        if (object instanceof Map) {
            return AttributeType.OBJECT;
        }
        return AttributeType.valueOf(object.toString());
    }
}
