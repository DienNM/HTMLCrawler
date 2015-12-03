package com.myprj.crawler.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.myprj.crawler.domain.config.ItemContent;
import com.myprj.crawler.domain.config.ItemData;
import com.myprj.crawler.enumeration.AttributeType;
import com.myprj.crawler.service.ItemStructureService;
import com.myprj.crawler.util.Serialization;

/**
 * @author DienNM (DEE)
 */

public abstract class AbstractItemStructure<T> implements ItemStructureService<T> {

    public abstract String getKey(T attribute);

    public abstract T createRoot(ItemData item);

    public abstract void addChild(T current, T child);

    protected abstract T createAttribute(T parent, AttributeType type, ItemData item, String key);

    @SuppressWarnings("unchecked")
    @Override
    public T buildAttributes(ItemData item, String jsonText) {
        Map<String, Object> inputObject = Serialization.deserialize(jsonText, Map.class);
        
        T root = createRoot(item);
        
        return build(item, root, inputObject);
    }

    @SuppressWarnings("unchecked")
    protected T build(ItemData item, T current, Map<String, Object> obj) {
        for (Entry<String, Object> entry : obj.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            T child = null;
            if (value instanceof Map) {
                child = buildMap(item, current, key, (Map<String, Object>) value);
            } else if (value instanceof List) {
                child = buildList(item, current, key, (List<Object>) value);
            } else if (value instanceof String) {
                child = buildString(item, current, key, (String) value);
            }
            addChild(current, child);
        }
        return current;
    }

    protected abstract T buildMap(ItemData item, T parent, String key, Map<String, Object> value);

    protected abstract T buildList(ItemData item, T parent, String key, List<Object> value);

    protected abstract T buildString(ItemData item, T parent, String key, String value);
}
