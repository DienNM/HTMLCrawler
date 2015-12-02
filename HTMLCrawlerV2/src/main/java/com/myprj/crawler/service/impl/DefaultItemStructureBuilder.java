package com.myprj.crawler.service.impl;

import static com.myprj.crawler.enumeration.AttributeType.LIST;
import static com.myprj.crawler.enumeration.AttributeType.OBJECT;
import static com.myprj.crawler.enumeration.AttributeType.TEXT;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.stereotype.Service;

import com.myprj.crawler.domain.config.AttributeData;
import com.myprj.crawler.enumeration.AttributeType;
import com.myprj.crawler.service.ItemStructureBuilder;
import com.myprj.crawler.util.Serialization;

/**
 * @author DienNM (DEE)
 */
@Service
public class DefaultItemStructureBuilder implements ItemStructureBuilder {

    @SuppressWarnings("unchecked")
    @Override
    public AttributeData build(long itemId, String jsonText) {

        Map<String, Object> jsonObj = Serialization.deserialize(jsonText, Map.class);
        AttributeData attribute = new AttributeData();
        attribute.setType(OBJECT);
        attribute.setName("content");
        attribute.setId(itemId + "|" + "cnt");
        return build(itemId, attribute, jsonObj);

    }

    @SuppressWarnings("unchecked")
    private AttributeData build(long itemId, AttributeData current, Map<String, Object> obj) {
        for (Entry<String, Object> entry : obj.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            AttributeData childAttribute = null;
            if (value instanceof Map) {
                childAttribute = buildMap(itemId, current, key, (Map<String, Object>) value);
            } else if (value instanceof List) {
                childAttribute = buildList(itemId, current, key, (List<Object>) value);
            } else if (value instanceof String) {
                childAttribute = buildString(itemId, current, key);
            }
            current.getChildren().add(childAttribute);
        }
        return current;
    }

    private AttributeData buildMap(long itemId, AttributeData parent, String key, Map<String, Object> value) {
        AttributeData current = createAttribute(parent, key);
        current.setParent(parent);
        current.setType(OBJECT);
        return build(itemId, current, value);
    }

    @SuppressWarnings("unchecked")
    private AttributeData buildList(long itemId, AttributeData parent, String key, List<Object> value) {
        AttributeData current = createAttribute(parent, key);
        current.setParent(parent);
        current.setType(LIST);
        Object obj = value.get(0);
        if(obj instanceof Map) {
            return build(itemId, current, (Map<String, Object>) obj);
        }
        return current;
    }

    private AttributeData buildString(long itemId, AttributeData parent, String key) {
        AttributeData current = createAttribute(parent, key);
        current.setParent(parent);
        current.setType(TEXT);
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
    
    private void print(AttributeData att, int indent) {
        int currentIndent = indent;
        System.out.println(getSpace(currentIndent) + "\"" + att.getName() + "\"" + " [" + att.getId() + "] " + " (" + att.getType() + ")");
        if(att.getType() == AttributeType.OBJECT) {
            System.out.println(getSpace(currentIndent) + "Value: {");
        }
        if(!att.getChildren().isEmpty()) {
            indent = indent + 4;
            for(AttributeData attributeData : att.getChildren()) {
                print(attributeData, indent);
            }
        }
        if(att.getType() == AttributeType.OBJECT) {
            System.out.println(getSpace(currentIndent) + "}");
        }
    }
    
    private String getSpace(int indent) {
        String indexSpace = "";
        for(int i = 0; i < indent ; i++) {
            indexSpace += " ";
        }
        return indexSpace;
    }

}
