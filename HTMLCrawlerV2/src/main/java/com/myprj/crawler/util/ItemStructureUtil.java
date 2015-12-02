package com.myprj.crawler.util;

import static com.myprj.crawler.domain.config.ItemContent.EMPTY_TEXT;
import static com.myprj.crawler.enumeration.AttributeType.LIST;
import static com.myprj.crawler.enumeration.AttributeType.OBJECT;
import static com.myprj.crawler.enumeration.AttributeType.TEXT;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.myprj.crawler.domain.config.AttributeData;
import com.myprj.crawler.domain.config.ItemContent;
import com.myprj.crawler.domain.config.ItemData;
import com.myprj.crawler.enumeration.AttributeType;

/**
 * @author DienNM (DEE)
 */
public final class ItemStructureUtil {

    @SuppressWarnings("unchecked")
    public static AttributeData build(ItemData item, String jsonText) {
        Map<String, Object> inputObject = Serialization.deserialize(jsonText, Map.class);
        
        // Create Root Attribute: content
        AttributeData rootAttribute = new AttributeData();
        rootAttribute.setType(OBJECT);
        rootAttribute.setName(ItemContent.ROOT);
        rootAttribute.setId(String.valueOf(item.getId()) + "|" + ItemContent.ROOT);
        
        item.setItemContent(new ItemContent());
        return build(item, rootAttribute, inputObject);
    }
    
    public static void populateValue2Attributes(Map<String, Object> detail, String attributeId, Object value) {
        Iterator<String> attNames = AttributeUtil.parseAttributeNames(attributeId);
        attNames.next();
        if(attNames.hasNext()) {
            populate(detail, attNames, value);
        }
    }
    
    @SuppressWarnings("unchecked")
    private static void populate(Map<String, Object> detail, Iterator<String> attNames, Object value) {
        String attName = attNames.next();
        Object object = detail.get(attName);
        if(object == null || object instanceof String) {
            detail.put(attName, value);
        }
        if(object instanceof List) {
            List<Object> listObjects = (List<Object>) object;
            if(!AttributeUtil.contentObject(listObjects)) {
                if(listObjects.size() == 1 && listObjects.get(0).equals(EMPTY_TEXT)) {
                    listObjects.remove(0);
                }
                listObjects.add(value);
                return;
            }
            Map<String, Object> itemValue = (Map<String, Object>) listObjects.get(0);
            if(!attNames.hasNext()) {
                itemValue.put(attName, value);
            } else {
                populate(itemValue, attNames, value);
            }
        } else if(object instanceof Map) {
            Map<String, Object> itemValue = (Map<String, Object>) object;
            if(!attNames.hasNext()) {
                itemValue.put(attName, value);
            } else {
                populate(itemValue, attNames, value);
            }
        }
    }

    @SuppressWarnings("unchecked")
    private static AttributeData build(ItemData item, AttributeData current, Map<String, Object> obj) {
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

    private static AttributeData buildMap(ItemData item, AttributeData parent, String key, Map<String, Object> value) {
        AttributeData current = createAttribute(parent, key);
        current.setParent(parent);
        current.setType(OBJECT);

        Map<String, Object> parentObject = AttributeUtil.getObject(item.getItemContent(), parent.getId());
        parentObject.put(key, new HashMap<String, Object>());

        return build(item, current, value);
    }

    @SuppressWarnings("unchecked")
    private static AttributeData buildList(ItemData item, AttributeData parent, String key, List<Object> value) {
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

    private static AttributeData buildString(ItemData item, AttributeData parent, String key) {
        AttributeData current = createAttribute(parent, key);
        current.setParent(parent);
        current.setType(TEXT);
        
        Map<String, Object> parentObj = AttributeUtil.getObject(item.getItemContent(), parent.getId());
        parentObj.put(key, EMPTY_TEXT);
        return current;
    }

    private static AttributeData createAttribute(AttributeData parent, String key) {
        AttributeData attribute = new AttributeData();
        attribute.setName(key);
        attribute.setId(parent.getId() + "|" + key);
        return attribute;
    }

    public static void print(AttributeData root) {
        print(root, 0);
    }

    private static void print(AttributeData att, int indent) {
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

    private static String getSpace(int indent) {
        String indexSpace = "";
        for (int i = 0; i < indent; i++) {
            indexSpace += " ";
        }
        return indexSpace;
    }

}
