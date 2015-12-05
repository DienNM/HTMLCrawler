package com.myprj.crawler.service.impl;

import static com.myprj.crawler.enumeration.AttributeType.LIST;
import static com.myprj.crawler.enumeration.AttributeType.OBJECT;
import static com.myprj.crawler.enumeration.AttributeType.TEXT;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;

import org.jsoup.helper.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myprj.crawler.domain.config.AttributeData;
import com.myprj.crawler.domain.config.AttributeSelector;
import com.myprj.crawler.domain.config.ItemAttributeData;
import com.myprj.crawler.domain.config.ItemContent;
import com.myprj.crawler.domain.crawl.WorkerItemData;
import com.myprj.crawler.enumeration.AttributeType;
import com.myprj.crawler.enumeration.SelectorSource;
import com.myprj.crawler.service.AttributeService;
import com.myprj.crawler.service.ItemAttributeStructureService;
import com.myprj.crawler.util.AttributeUtil;
import com.myprj.crawler.util.Serialization;

/**
 * @author DienNM (DEE)
 */
@Service
public class ItemAttributeStructureServiceImpl implements ItemAttributeStructureService {

    @Autowired
    private AttributeService attributeService;

    public void setAttributeService(AttributeService attributeService) {
        this.attributeService = attributeService;
    }

    @SuppressWarnings("unchecked")
    @Override
    public ItemAttributeData build(WorkerItemData workerItem, String jsonText) {
        Map<String, Object> inputObject = Serialization.deserialize(jsonText, Map.class);

        long itemId = workerItem.getItemId();
        String attributeId = String.valueOf(itemId) + "|" + ItemContent.ROOT;

        ItemAttributeData root = new ItemAttributeData();
        root.setId(workerItem.getId() + "|" + attributeId);
        root.setRoot(true);
        root.setName(ItemContent.ROOT);
        root.setType(OBJECT);
        root.setParent(null);
        root.setParentId(null);
        root.setAttributeId(attributeId);
        root.setItemId(itemId);
        root.setAttribute(attributeService.getRoot(itemId));

        return build(workerItem, root, inputObject);
    }

    @SuppressWarnings("unchecked")
    private ItemAttributeData build(WorkerItemData workerItem, ItemAttributeData current, Map<String, Object> obj) {
        for (Entry<String, Object> entry : obj.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            ItemAttributeData child = null;
            if (value instanceof Map) {
                child = buildMap(workerItem, current, key, (Map<String, Object>) value);
            } else if (value instanceof List) {
                child = buildList(workerItem, current, key, (List<Object>) value);
            } else if (value instanceof String) {
                child = buildString(workerItem, current, key, (String) value);
            }
            addChild(current, child);
        }
        return current;
    }

    protected ItemAttributeData createAttribute(ItemAttributeData parent, AttributeType type,
            WorkerItemData workerItem, String key) {
        String attributeId = parent.getAttributeId() + "|" + key;
        AttributeData attributeData = attributeService.get(attributeId);
        if (attributeData == null) {
            throw new InvalidParameterException("Cannot build Item Attribute structue. Key: " + attributeId
                    + " not found");
        }
        ItemAttributeData attribute = new ItemAttributeData();
        attribute.setId(workerItem + "|" + attributeId);
        attribute.setName(key);
        attribute.setAttributeId(attributeId);
        attribute.setAttribute(attributeData);
        attribute.setParent(parent);
        attribute.setParentId(parent.getId());
        attribute.setItemId(workerItem.getItemId());
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

    private ItemAttributeData buildMap(WorkerItemData workerItem, ItemAttributeData parent, String key,
            Map<String, Object> value) {
        ItemAttributeData current = createAttribute(parent, OBJECT, workerItem, key);
        return build(workerItem, current, value);
    }

    @SuppressWarnings("unchecked")
    private ItemAttributeData buildList(WorkerItemData workerItem, ItemAttributeData parent, String key,
            List<Object> value) {
        ItemAttributeData current = createAttribute(parent, LIST, workerItem, key);

        if (!AttributeUtil.contentObject(value)) {
            if (value != null && !value.isEmpty()) {
                AttributeSelector attributeSelector = parseAttritbuteSelectors(value.get(0).toString());
                current.setSelector(attributeSelector);
            }
            return current;
        }

        ItemAttributeData attribute = createAttribute(current, OBJECT, workerItem, key);
        ItemAttributeData child = build(workerItem, attribute, (Map<String, Object>) value.get(0));
        addChild(current, child);
        return current;
    }

    private ItemAttributeData buildString(WorkerItemData workerItem, ItemAttributeData parent, String key, String value) {
        ItemAttributeData current = createAttribute(parent, TEXT, workerItem, key);
        AttributeSelector attributeSelector = parseAttritbuteSelectors(value);
        current.setSelector(attributeSelector);
        return current;
    }

    // I@CSS||E@CSS||E@CSS
    private AttributeSelector parseAttritbuteSelectors(String input) {
        if (StringUtil.isBlank(input)) {
            return null;
        }
        String[] texts = input.split(Pattern.quote("|") + Pattern.quote("|"));
        if (texts.length == 0) {
            throw new InvalidParameterException("Cannot parse CSS-Selector value: " + input);
        }
        String e0 = texts[0];
        AttributeSelector targetSelector = parseAttritbuteSelector(e0);

        for (int i = 1; i < texts.length; i++) {
            String ex = texts[i];
            AttributeSelector externalSelector = parseAttritbuteSelector(ex);
            targetSelector.getExternalSelectors().add(externalSelector);
        }

        return targetSelector;
    }

    private AttributeSelector parseAttritbuteSelector(String input) {
        int firstIndexOfAT = input.indexOf("@");
        String source = input.substring(0, firstIndexOfAT);
        String css = input.substring(firstIndexOfAT + 1);
        return new AttributeSelector(css, SelectorSource.fromString(source));
    }
}
