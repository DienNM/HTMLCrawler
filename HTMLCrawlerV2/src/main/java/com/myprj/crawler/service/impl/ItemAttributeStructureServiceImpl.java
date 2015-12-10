package com.myprj.crawler.service.impl;

import static com.myprj.crawler.domain.config.ItemContent.EMPTY_TEXT;
import static com.myprj.crawler.enumeration.AttributeType.LIST;
import static com.myprj.crawler.enumeration.AttributeType.OBJECT;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.myprj.crawler.domain.config.ItemAttributeData;
import com.myprj.crawler.domain.config.ItemContent;
import com.myprj.crawler.domain.config.ItemData;
import com.myprj.crawler.enumeration.AttributeType;
import com.myprj.crawler.service.ItemAttributeStructureService;
import com.myprj.crawler.util.AttributeUtil;
import com.myprj.crawler.util.Serialization;

/**
 * @author DienNM (DEE)
 */
@Service
@SuppressWarnings("unchecked")
public class ItemAttributeStructureServiceImpl implements ItemAttributeStructureService {

    @Override
    public ItemAttributeData build(ItemData item, String jsonText) {
        Map<String, Object> inputObject = Serialization.deserialize(jsonText, Map.class);

        ItemAttributeData root = new ItemAttributeData();
        root.setType(OBJECT);
        root.setName(ItemContent.ROOT);
        root.setId(String.valueOf(item.getId()) + "|" + ItemContent.ROOT);
        root.setRoot(true);
        root.setParent(null);
        root.setItemId(item.getId());

        // Initialize ItemContent
        item.setSampleContent(new ItemContent());

        return build(item, root, inputObject);
    }

    private ItemAttributeData build(ItemData item, ItemAttributeData current, Map<String, Object> obj) {
        for (Entry<String, Object> entry : obj.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            ItemAttributeData child = null;
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

    private ItemAttributeData createAttribute(ItemAttributeData parent, AttributeType type, ItemData item, String key) {
        ItemAttributeData attribute = new ItemAttributeData();
        attribute.setName(key);
        attribute.setId(parent.getId() + "|" + key);
        attribute.setParent(parent);
        attribute.setParentId(parent.getId());
        attribute.setItemId(item.getId());
        attribute.setType(type);
        return attribute;
    }

    private void addChild(ItemAttributeData current, ItemAttributeData child) {
        List<ItemAttributeData> children = current.getChildren();
        if (children == null) {
            current.setChildren(new ArrayList<ItemAttributeData>());
        }
        current.getChildren().add(child);
    }

    private ItemAttributeData buildMap(ItemData item, ItemAttributeData parent, String key, Map<String, Object> value) {
        ItemAttributeData current = createAttribute(parent, OBJECT, item, key);
        Map<String, Object> parentObject = AttributeUtil.getObject(item.getSampleContent(), parent.getId());
        parentObject.put(key, new HashMap<String, Object>());

        return build(item, current, value);
    }

    private ItemAttributeData buildList(ItemData item, ItemAttributeData parent, String key, List<Object> value) {
        Map<String, Object> parentObj = AttributeUtil.getObject(item.getSampleContent(), parent.getId());
        List<Object> listValue = new ArrayList<Object>();
        parentObj.put(key, listValue);
        
        ItemAttributeData current = createAttribute(parent, LIST, item, key);
        
        AttributeType elementType = AttributeUtil.getElementType(value);
        if(AttributeType.OBJECT.equals(elementType)) {
            Map<String, Object> elementList = new HashMap<String, Object>();
            listValue.add(elementList);
            
            ItemAttributeData attribute = createAttribute(current, OBJECT, item, key);
            ItemAttributeData child = build(item, attribute, (Map<String, Object>) value.get(0));
            addChild(current, child);
            
            return current;
        } else {
            listValue.add(EMPTY_TEXT);
            ItemAttributeData child = createAttribute(current, elementType, item, key);
            addChild(current, child);
            return current;
        }
    }
    
    
    private ItemAttributeData buildString(ItemData item, ItemAttributeData parent, String key, String value) {
        ItemAttributeData current = null;
        AttributeType type = null;
        
        if(StringUtils.isEmpty(value)) {
            type = AttributeType.TEXT;
        } else {
            type = AttributeType.valueOf(value);
        }
        current = createAttribute(parent, type, item, key);
        Map<String, Object> parentObj = AttributeUtil.getObject(item.getSampleContent(), parent.getId());
        parentObj.put(key, EMPTY_TEXT);
        
        return current;
    }

}
