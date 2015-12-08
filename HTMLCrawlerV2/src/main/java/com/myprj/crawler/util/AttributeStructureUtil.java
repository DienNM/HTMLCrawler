package com.myprj.crawler.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.myprj.crawler.domain.config.ItemAttributeData;
import com.myprj.crawler.domain.config.WorkerItemAttributeData;
import com.myprj.crawler.enumeration.AttributeType;

/**
 * @author DienNM (DEE)
 */
public final class AttributeStructureUtil {

    public static List<ItemAttributeData> navigateAttribtesFromRoot(ItemAttributeData root) {
        List<ItemAttributeData> attributeDatas = new ArrayList<ItemAttributeData>();
        navigateAttribtesFromParent(root, attributeDatas);
        return attributeDatas;
    }

    private static void navigateAttribtesFromParent(ItemAttributeData parent, List<ItemAttributeData> attributeDatas) {
        attributeDatas.add(parent);
        for (ItemAttributeData child : parent.getChildren()) {
            navigateAttribtesFromParent(child, attributeDatas);
        }
    }

    public static void populateValue2Attribute(Map<String, Object> detail, String attributeId, Object value) {
        Iterator<String> attNames = AttributeUtil.parseAttributeNames(attributeId);
        attNames.next();
        if (attNames.hasNext()) {
            populate(detail, attNames, value);
        }
    }

    @SuppressWarnings("unchecked")
    private static void populate(Map<String, Object> detail, Iterator<String> attNames, Object value) {
        String attName = attNames.next();
        Object object = detail.get(attName);
        if (object == null || value == null) {
            return;
        }
        if (object instanceof String) {
            detail.put(attName, value);
        }
        if (object instanceof List) {
            detail.put(attName, value);
            return;
        } else if (object instanceof Map) {
            Map<String, Object> itemValue = (Map<String, Object>) object;
            if (!attNames.hasNext()) {
                itemValue.put(attName, value);
            } else {
                populate(itemValue, attNames, value);
            }
        }
    }

    public static void print(ItemAttributeData root) {
        print(root, 0);
    }

    private static void print(ItemAttributeData att, int indent) {
        int currentIndent = indent;
        System.out.println(getSpace(currentIndent) + "\"" + att.getName() + "\"" + " [" + att.getId() + "] " + " ("
                + att.getType() + ")");

        if (att.getType() == AttributeType.OBJECT) {
            System.out.println(getSpace(currentIndent) + " { ");
        }
        if (!att.getChildren().isEmpty()) {
            indent = indent + 4;
            for (ItemAttributeData attributeData : att.getChildren()) {
                print(attributeData, indent);
            }
        }
        if (att.getType() == AttributeType.OBJECT) {
            System.out.println(getSpace(currentIndent) + " } ");
        }
    }

    public static void print(WorkerItemAttributeData root) {
        print(root, 0);
    }

    private static void print(WorkerItemAttributeData att, int indent) {
        int currentIndent = indent;
        System.out.println(getSpace(currentIndent) + "\"" + att.getName() + "\"" + " [" + att.getAttributeId() + "] "
                + " (" + att.getType() + ") " + " - Selector: "
                + (att.getSelector() != null ? att.getSelector().toString() : ""));

        if (att.getType() == AttributeType.OBJECT) {
            System.out.println(getSpace(currentIndent) + " { ");
        }
        if (!att.getChildren().isEmpty()) {
            indent = indent + 4;
            for (WorkerItemAttributeData itemAttribute : att.getChildren()) {
                print(itemAttribute, indent);
            }
        }
        if (att.getType() == AttributeType.OBJECT) {
            System.out.println(getSpace(currentIndent) + " } ");
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
