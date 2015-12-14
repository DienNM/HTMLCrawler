package com.myprj.crawler.service.impl;

import static com.myprj.crawler.domain.config.AttributeSelector.parseSelectors;
import static com.myprj.crawler.enumeration.AttributeType.OBJECT;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myprj.crawler.domain.config.AttributeSelector;
import com.myprj.crawler.domain.config.ItemAttributeData;
import com.myprj.crawler.domain.config.ItemContent;
import com.myprj.crawler.domain.config.WorkerItemAttributeData;
import com.myprj.crawler.domain.crawl.WorkerItemData;
import com.myprj.crawler.enumeration.AttributeType;
import com.myprj.crawler.service.ItemAttributeService;
import com.myprj.crawler.service.WorkerItemAttributeStructureService;
import com.myprj.crawler.util.Serialization;

/**
 * @author DienNM (DEE)
 */
@Service
@SuppressWarnings("unchecked")
public class WorkerItemAttributeStructureServiceImpl implements WorkerItemAttributeStructureService {

    @Autowired
    private ItemAttributeService itemAttributeService;

    public void setAttributeService(ItemAttributeService attributeService) {
        this.itemAttributeService = attributeService;
    }

    @Override
    public WorkerItemAttributeData build(WorkerItemData workerItem, String jsonText) {
        Map<String, Object> inputObject = Serialization.deserialize(jsonText, Map.class);

        String itemKey = workerItem.getItemKey();
        String attributeId = itemKey + "|" + ItemContent.ROOT;

        WorkerItemAttributeData root = new WorkerItemAttributeData();
        root.setId(workerItem.getId() + "|" + attributeId);
        root.setWorkerItemId(workerItem.getId());
        root.setRoot(true);
        root.setName(ItemContent.ROOT);
        root.setType(OBJECT);
        root.setParentId(null);
        root.setAttributeId(attributeId);
        root.setItemKey(workerItem.getItemKey());
        root.setSiteKey(workerItem.getSiteKey());

        return build(workerItem, root, inputObject);
    }

    private WorkerItemAttributeData createAttribute(WorkerItemAttributeData parent, WorkerItemData workerItem,
            String key) {
        String attributeId = parent.getAttributeId() + "|" + key;
        ItemAttributeData attributeData = itemAttributeService.get(attributeId);
        if (attributeData == null) {
            throw new InvalidParameterException("Cannot build Worker Item Attribute structure. Key: " + attributeId
                    + " not found");
        }
        return createAttribute(parent, workerItem, key, attributeData);
    }

    private WorkerItemAttributeData createAttribute(WorkerItemAttributeData parent, WorkerItemData workerItem,
            String key, ItemAttributeData attributeData) {
        String attributeId = parent.getAttributeId() + "|" + key;
        WorkerItemAttributeData attribute = new WorkerItemAttributeData();
        attribute.setWorkerItemId(workerItem.getId());
        attribute.setId(workerItem.getId() + "|" + attributeId);
        attribute.setName(key);
        attribute.setAttributeId(attributeId);
        attribute.setParentId(parent.getId());
        attribute.setItemKey(workerItem.getItemKey());
        attribute.setType(attributeData.getType());
        attribute.setSiteKey(parent.getSiteKey());

        return attribute;
    }

    private WorkerItemAttributeData build(WorkerItemData workerItem, WorkerItemAttributeData current,
            Map<String, Object> obj) {
        for (Entry<String, Object> entry : obj.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();

            WorkerItemAttributeData child = null;
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

    private void addChild(WorkerItemAttributeData current, WorkerItemAttributeData child) {
        List<WorkerItemAttributeData> children = current.getChildren();
        if (children == null) {
            current.setChildren(new ArrayList<WorkerItemAttributeData>());
        }
        current.getChildren().add(child);
    }

    private WorkerItemAttributeData buildMap(WorkerItemData workerItem, WorkerItemAttributeData parent, String key,
            Map<String, Object> value) {
        WorkerItemAttributeData current = createAttribute(parent, workerItem, key);
        return build(workerItem, current, value);
    }

    private WorkerItemAttributeData buildList(WorkerItemData workerItem, WorkerItemAttributeData parent, String key,
            List<Object> value) {

        WorkerItemAttributeData current = createAttribute(parent, workerItem, key);
        ItemAttributeData itemAttributeList = itemAttributeService.get(current.getAttributeId());
        itemAttributeService.populateChildren(itemAttributeList);

        ItemAttributeData itemAttributeData = itemAttributeList.getChildren().get(0);
        AttributeType elementType = itemAttributeData.getType();

        if (AttributeType.OBJECT.equals(elementType)) {
            if (value.size() != 2) {
                throw new InvalidParameterException("Error build attribute: " + key + " - LIST_OBJECT needs 2 params");
            }
            AttributeSelector attributeSelector = parseSelectors(value.get(1).toString());
            current.setSelector(attributeSelector);

            WorkerItemAttributeData attribute = createAttribute(current, workerItem, key, itemAttributeData);
            WorkerItemAttributeData child = build(workerItem, attribute, (Map<String, Object>) value.get(0));
            addChild(current, child);

            return current;
        } else {
            WorkerItemAttributeData child = createAttribute(current, workerItem, key, itemAttributeData);
            if (value != null && !value.isEmpty()) {
                AttributeSelector attributeSelector = parseSelectors(value.get(0).toString());
                child.setSelector(attributeSelector);
            }
            addChild(current, child);
            return current;
        }
    }

    private WorkerItemAttributeData buildString(WorkerItemData workerItem, WorkerItemAttributeData parent, String key,
            String value) {
        WorkerItemAttributeData current = createAttribute(parent, workerItem, key);
        AttributeSelector attributeSelector = parseSelectors(value);
        current.setSelector(attributeSelector);
        return current;
    }

}
