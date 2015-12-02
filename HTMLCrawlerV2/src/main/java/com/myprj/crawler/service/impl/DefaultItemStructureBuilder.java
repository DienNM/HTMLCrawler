package com.myprj.crawler.service.impl;

import static com.myprj.crawler.domain.config.ItemContent.EMPTY_TEXT;
import static com.myprj.crawler.enumeration.AttributeType.LIST;
import static com.myprj.crawler.enumeration.AttributeType.OBJECT;
import static com.myprj.crawler.enumeration.AttributeType.TEXT;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.stereotype.Service;

import com.myprj.crawler.domain.config.AttributeData;
import com.myprj.crawler.domain.config.ItemContent;
import com.myprj.crawler.domain.config.ItemData;
import com.myprj.crawler.enumeration.AttributeType;
import com.myprj.crawler.service.ItemStructureBuilder;
import com.myprj.crawler.util.AttributeUtil;
import com.myprj.crawler.util.Serialization;

/**
 * @author DienNM (DEE)
 */
@Service
public class DefaultItemStructureBuilder implements ItemStructureBuilder {

    @SuppressWarnings("unchecked")
    @Override
    public AttributeData build(ItemData item, String jsonText) {
        Map<String, Object> inputObject = Serialization.deserialize(jsonText, Map.class);
        
        // Create Root Attribute: content
        AttributeData rootAttribute = new AttributeData();
        rootAttribute.setType(OBJECT);
        rootAttribute.setName(ItemContent.ROOT);
        rootAttribute.setId(String.valueOf(item.getId()) + "|" + ItemContent.ROOT);
        
        item.setItemContent(new ItemContent());
        return build(item, rootAttribute, inputObject);
    }


    @SuppressWarnings("unchecked")
    private AttributeData build(ItemData item, AttributeData current, Map<String, Object> obj) {
        for (Entry<String, Object> entry : obj.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            AttributeData childAttribute = null;
            if (value instanceof Map) {
                childAttribute = buildMap(item, current, key, (Map<String, Object>) value);
            } else if (value instanceof List) {
                childAttribute = buildList(item, current, key, (List<Object>) value);
            } else if (value instanceof String) {
                childAttribute = buildString(item, current, key);
            }
            current.getChildren().add(childAttribute);
        }
        return current;
    }

    private AttributeData buildMap(ItemData item, AttributeData parent, String key, Map<String, Object> value) {
        AttributeData current = createAttribute(parent, key);
        current.setParent(parent);
        current.setType(OBJECT);

        Map<String, Object> parentObject = AttributeUtil.getObject(item.getItemContent(), parent.getId());
        parentObject.put(key, new HashMap<String, Object>());

        return build(item, current, value);
    }

    @SuppressWarnings("unchecked")
    private AttributeData buildList(ItemData item, AttributeData parent, String key, List<Object> value) {
        AttributeData current = createAttribute(parent, key);
        current.setParent(parent);
        current.setType(LIST);


        Map<String, Object> parentObj = AttributeUtil.getObject(item.getItemContent(), parent.getId());
        List<Object> listValue = new ArrayList<Object>();
        parentObj.put(key, listValue);

        if (!AttributeUtil.contentObject(listValue)) {
            listValue.add(EMPTY_TEXT);
            return current;
        }

        Map<String, Object> elementList = new HashMap<String, Object>();
        listValue.add(elementList);
        AttributeData subAttribute = createAttribute(current, key);
        subAttribute.setParent(current);
        subAttribute.setType(OBJECT);
        
        return build(item, subAttribute, (Map<String, Object>) value.get(0));
    }

    private AttributeData buildString(ItemData item, AttributeData parent, String key) {
        AttributeData current = createAttribute(parent, key);
        current.setParent(parent);
        current.setType(TEXT);
        
        Map<String, Object> parentObj = AttributeUtil.getObject(item.getItemContent(), parent.getId());
        parentObj.put(key, EMPTY_TEXT);
        return current;
    }

    private AttributeData createAttribute(AttributeData parent, String key) {
        AttributeData attribute = new AttributeData();
        attribute.setName(key);
        attribute.setId(parent.getId() + "|" + key);
        return attribute;
    }

    @Override
    public void print(AttributeData root) {
        print(root, 0);
    }

    protected void print(AttributeData att, int indent) {
        int currentIndent = indent;
        System.out.println(getSpace(currentIndent) + "\"" + att.getName() + "\"" + " [" + att.getId() + "] " + " ("
                + att.getType() + ")");
        if (att.getType() == AttributeType.OBJECT) {
            System.out.println(getSpace(currentIndent) + "Value: {");
        }
        if (!att.getChildren().isEmpty()) {
            indent = indent + 4;
            for (AttributeData attributeData : att.getChildren()) {
                print(attributeData, indent);
            }
        }
        if (att.getType() == AttributeType.OBJECT) {
            System.out.println(getSpace(currentIndent) + "}");
        }
    }

    private String getSpace(int indent) {
        String indexSpace = "";
        for (int i = 0; i < indent; i++) {
            indexSpace += " ";
        }
        return indexSpace;
    }

}
