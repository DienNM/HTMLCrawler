package com.myprj.crawler.service.impl;

import static com.myprj.crawler.enumeration.AttributeType.LIST;
import static com.myprj.crawler.enumeration.AttributeType.LIST_OBJECT;
import static com.myprj.crawler.enumeration.AttributeType.OBJECT;
import static com.myprj.crawler.util.AttributeSelectorUtil.parseAttritbuteSelectors;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myprj.crawler.domain.config.AttributeSelector;
import com.myprj.crawler.domain.config.ItemAttributeData;
import com.myprj.crawler.domain.config.ItemContent;
import com.myprj.crawler.domain.config.ItemData;
import com.myprj.crawler.domain.config.WorkerItemAttributeData;
import com.myprj.crawler.domain.crawl.WorkerItemData;
import com.myprj.crawler.enumeration.AttributeType;
import com.myprj.crawler.service.ItemAttributeService;
import com.myprj.crawler.service.ItemService;
import com.myprj.crawler.service.WorkerItemAttributeStructureService;
import com.myprj.crawler.util.AttributeUtil;
import com.myprj.crawler.util.Serialization;

/**
 * @author DienNM (DEE)
 */
@Service
public class WorkerItemAttributeStructureServiceImpl implements WorkerItemAttributeStructureService {
    
    private Logger logger = LoggerFactory.getLogger(WorkerItemAttributeStructureServiceImpl.class);
    
    @Autowired
    private ItemAttributeService itemAttributeService;
    
    @Autowired
    private ItemService itemService;

    public void setAttributeService(ItemAttributeService attributeService) {
        this.itemAttributeService = attributeService;
    }

    @SuppressWarnings("unchecked")
    @Override
    public WorkerItemAttributeData build(WorkerItemData workerItem, String jsonText) {
        Map<String, Object> inputObject = Serialization.deserialize(jsonText, Map.class);
        
        ItemData itemData = itemService.get(workerItem.getItemKey());
        if(itemData == null) {
            logger.error("Item Key: " + workerItem.getItemKey() + " not found");
            return null;
        }
        String attributeId = itemData.getId() + "|" + ItemContent.ROOT;

        WorkerItemAttributeData root = new WorkerItemAttributeData();
        root.setId(workerItem.getId() + "|" + attributeId);
        root.setWorkerItemId(workerItem.getId());
        root.setRoot(true);
        root.setName(ItemContent.ROOT);
        root.setType(OBJECT);
        root.setParent(null);
        root.setParentId(null);
        root.setAttributeId(attributeId);
        root.setItemKey(workerItem.getItemKey());
        root.setSiteKey(workerItem.getSiteKey());

        return build(workerItem, root, inputObject);
    }

    @SuppressWarnings("unchecked")
    private WorkerItemAttributeData build(WorkerItemData workerItem, WorkerItemAttributeData current, Map<String, Object> obj) {
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

    protected WorkerItemAttributeData createAttribute(WorkerItemAttributeData parent, AttributeType type,
            WorkerItemData workerItem, String key) {
        String attributeId = parent.getAttributeId() + "|" + key;
        ItemAttributeData attributeData = itemAttributeService.get(attributeId);
        if (attributeData == null) {
            throw new InvalidParameterException("Cannot build Worker Item Attribute structue. Key: " + attributeId
                    + " not found");
        }
        WorkerItemAttributeData attribute = new WorkerItemAttributeData();
        attribute.setWorkerItemId(workerItem.getId());
        attribute.setId(workerItem.getId() + "|" + attributeId);
        attribute.setName(key);
        attribute.setAttributeId(attributeId);
        attribute.setParent(parent);
        attribute.setParentId(parent.getId());
        attribute.setItemKey(workerItem.getItemKey());
        if(type == null) {
            attribute.setType(attributeData.getType());
        } else {
            attribute.setType(type);
        }
        return attribute;
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
        WorkerItemAttributeData current = createAttribute(parent, OBJECT, workerItem, key);
        return build(workerItem, current, value);
    }

    @SuppressWarnings("unchecked")
    private WorkerItemAttributeData buildList(WorkerItemData workerItem, WorkerItemAttributeData parent, String key,
            List<Object> value) {

        if (!AttributeUtil.contentObject(value)) {
         // [...]
            WorkerItemAttributeData current = createAttribute(parent, LIST, workerItem, key);
            if (value != null && !value.isEmpty()) {
                AttributeSelector attributeSelector = parseAttritbuteSelectors(value.get(0).toString());
                current.setSelector(attributeSelector);
            }
            return current;
        }
        
        // [{key:value,...}]
        if(value.size() != 2) {
            throw new InvalidParameterException("Error build attribute: " + key + " - LIST_OBJECT needs 2 params");
        }
        WorkerItemAttributeData current = createAttribute(parent, LIST_OBJECT, workerItem, key);
        AttributeSelector attributeSelector = parseAttritbuteSelectors(value.get(1).toString());
        current.setSelector(attributeSelector);
        
        WorkerItemAttributeData attribute = createAttribute(current, OBJECT, workerItem, key);
        
        WorkerItemAttributeData child = build(workerItem, attribute, (Map<String, Object>) value.get(0));
        addChild(current, child);
        return current;
    }

    private WorkerItemAttributeData buildString(WorkerItemData workerItem, WorkerItemAttributeData parent, String key, String value) {
        WorkerItemAttributeData current = createAttribute(parent, null, workerItem, key);
        AttributeSelector attributeSelector = parseAttritbuteSelectors(value);
        current.setSelector(attributeSelector);
        return current;
    }

    
}
