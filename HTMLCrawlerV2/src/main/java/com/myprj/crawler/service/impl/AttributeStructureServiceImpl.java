package com.myprj.crawler.service.impl;

import static com.myprj.crawler.domain.config.ItemContent.EMPTY_TEXT;
import static com.myprj.crawler.enumeration.AttributeType.LIST;
import static com.myprj.crawler.enumeration.AttributeType.OBJECT;
import static com.myprj.crawler.enumeration.AttributeType.TEXT;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.myprj.crawler.domain.config.AttributeData;
import com.myprj.crawler.domain.config.ItemContent;
import com.myprj.crawler.domain.config.ItemData;
import com.myprj.crawler.enumeration.AttributeType;
import com.myprj.crawler.util.AttributeUtil;

/**
 * @author DienNM (DEE)
 */
@Service("attributeStructureService")
public class AttributeStructureServiceImpl extends AbstractItemStructure<AttributeData>{

    @Override
    public AttributeData createRoot(ItemData item) {
        AttributeData root = new AttributeData();
        root.setType(OBJECT);
        root.setName(ItemContent.ROOT);
        root.setId(String.valueOf(item.getId()) + "|" + ItemContent.ROOT);
        root.setRoot(true);
        root.setParent(null);
        root.setItemId(item.getId());
        
        item.setSampleContent(new ItemContent());
        
        return root;
    }

    @Override
    protected AttributeData createAttribute(AttributeData parent, AttributeType type, ItemData item, String key) {
        AttributeData attribute = new AttributeData();
        attribute.setName(key);
        attribute.setId(parent.getId() + "|" + key);
        attribute.setParent(parent);
        attribute.setParentId(parent.getId());
        attribute.setItemId(item.getId());
        attribute.setType(type);
        return attribute;
    }

    @Override
    public void addChild(AttributeData current, AttributeData child) {
        List<AttributeData> children = current.getChildren();
        if(children == null) {
            current.setChildren(new ArrayList<AttributeData>());
        }
        current.getChildren().add(child);
    }
    
    @Override
    public String getKey(AttributeData attribute) {
        return attribute.getId();
    }

    @Override
    protected AttributeData buildMap(ItemData item, AttributeData parent, String key, Map<String, Object> value) {
        AttributeData current = createAttribute(parent, OBJECT, item, key);
        Map<String, Object> parentObject = AttributeUtil.getObject(item.getSampleContent(), getKey(parent));
        parentObject.put(key, new HashMap<String, Object>());

        return build(item, current, value);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected AttributeData buildList(ItemData item, AttributeData parent, String key, List<Object> value) {
        AttributeData current = createAttribute(parent, LIST, item, key);

        Map<String, Object> parentObj = AttributeUtil.getObject(item.getSampleContent(), getKey(parent));
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

    @Override
    protected AttributeData buildString(ItemData item, AttributeData parent, String key, String value) {
        AttributeData current = createAttribute(parent, TEXT, item, key);

        Map<String, Object> parentObj = AttributeUtil.getObject(item.getSampleContent(), getKey(parent));
        parentObj.put(key, EMPTY_TEXT);
        return current;
    }

}
