package com.myprj.crawler.service.impl;

import static com.myprj.crawler.domain.config.ItemContent.EMPTY_TEXT;
import static com.myprj.crawler.enumeration.AttributeType.HTML;
import static com.myprj.crawler.enumeration.AttributeType.LIST;
import static com.myprj.crawler.enumeration.AttributeType.OBJECT;
import static com.myprj.crawler.enumeration.AttributeType.TEXT;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.myprj.crawler.domain.config.AttributeData;
import com.myprj.crawler.domain.config.ItemContent;
import com.myprj.crawler.domain.config.ItemData;
import com.myprj.crawler.enumeration.AttributeType;
import com.myprj.crawler.service.AttributeStructureService;
import com.myprj.crawler.util.AttributeUtil;
import com.myprj.crawler.util.Serialization;

/**
 * @author DienNM (DEE)
 */
@Service
public class AttributeStructureServiceImpl implements AttributeStructureService {

    @SuppressWarnings("unchecked")
    @Override
    public AttributeData build(ItemData item, String jsonText) {
        Map<String, Object> inputObject = Serialization.deserialize(jsonText, Map.class);

        AttributeData root = new AttributeData();
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

    @SuppressWarnings("unchecked")
    private AttributeData build(ItemData item, AttributeData current, Map<String, Object> obj) {
        for (Entry<String, Object> entry : obj.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            AttributeData child = null;
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

    private AttributeData createAttribute(AttributeData parent, AttributeType type, ItemData item, String key) {
        AttributeData attribute = new AttributeData();
        attribute.setName(key);
        attribute.setId(parent.getId() + "|" + key);
        attribute.setParent(parent);
        attribute.setParentId(parent.getId());
        attribute.setItemId(item.getId());
        attribute.setType(type);
        return attribute;
    }

    private void addChild(AttributeData current, AttributeData child) {
        List<AttributeData> children = current.getChildren();
        if (children == null) {
            current.setChildren(new ArrayList<AttributeData>());
        }
        current.getChildren().add(child);
    }

    private AttributeData buildMap(ItemData item, AttributeData parent, String key, Map<String, Object> value) {
        AttributeData current = createAttribute(parent, OBJECT, item, key);
        Map<String, Object> parentObject = AttributeUtil.getObject(item.getSampleContent(), parent.getId());
        parentObject.put(key, new HashMap<String, Object>());

        return build(item, current, value);
    }

    @SuppressWarnings("unchecked")
    private AttributeData buildList(ItemData item, AttributeData parent, String key, List<Object> value) {
        AttributeData current = createAttribute(parent, LIST, item, key);

        Map<String, Object> parentObj = AttributeUtil.getObject(item.getSampleContent(), parent.getId());
        List<Object> listValue = new ArrayList<Object>();
        parentObj.put(key, listValue);

        if (!AttributeUtil.contentObject(value)) {
            listValue.add(EMPTY_TEXT);
            return current;
        }

        Map<String, Object> elementList = new HashMap<String, Object>();
        listValue.add(elementList);
        AttributeData attribute = createAttribute(current, OBJECT, item, key);
        AttributeData child = build(item, attribute, (Map<String, Object>) value.get(0));
        addChild(current, child);
        return current;
    }

    private AttributeData buildString(ItemData item, AttributeData parent, String key, String value) {
        AttributeData current = null;
        
        if(StringUtils.isEmpty(value) || AttributeType.TEXT.name().equalsIgnoreCase(value)) {
            current = createAttribute(parent, TEXT, item, key);
        } else {
            current = createAttribute(parent, HTML, item, key);
        }
        Map<String, Object> parentObj = AttributeUtil.getObject(item.getSampleContent(), parent.getId());
        
        parentObj.put(key, EMPTY_TEXT);
        return current;
    }

}
