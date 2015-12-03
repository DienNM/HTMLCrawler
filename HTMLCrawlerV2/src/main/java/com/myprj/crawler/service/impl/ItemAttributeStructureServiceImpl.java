package com.myprj.crawler.service.impl;

import static com.myprj.crawler.enumeration.AttributeType.LIST;
import static com.myprj.crawler.enumeration.AttributeType.OBJECT;
import static com.myprj.crawler.enumeration.AttributeType.TEXT;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.jsoup.helper.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myprj.crawler.domain.config.AttributeData;
import com.myprj.crawler.domain.config.AttributeSelector;
import com.myprj.crawler.domain.config.ItemAttributeData;
import com.myprj.crawler.domain.config.ItemContent;
import com.myprj.crawler.domain.config.ItemData;
import com.myprj.crawler.enumeration.AttributeType;
import com.myprj.crawler.enumeration.SelectorSource;
import com.myprj.crawler.service.AttributeService;
import com.myprj.crawler.util.AttributeUtil;

/**
 * @author DienNM (DEE)
 */
@Service("itemAttributeStructureService")
public class ItemAttributeStructureServiceImpl extends AbstractItemStructure<ItemAttributeData> {
    
    @Autowired
    private AttributeService attributeService;
    
    public void setAttributeService(AttributeService attributeService) {
        this.attributeService = attributeService;
    }

    @Override
    public ItemAttributeData createRoot(ItemData item) {
        ItemAttributeData root = new ItemAttributeData();
        root.setRoot(true);
        root.setType(OBJECT);
        root.setParent(null);
        root.setParentId(-1);
        root.setAttributeId(String.valueOf(item.getId()) + "|" + ItemContent.ROOT);
        root.setItemId(item.getId());
        root.setAttribute(attributeService.getRoot(item.getId()));
        
        item.setRootItemAttribute(root);
        
        return root;
    }

    @Override
    protected ItemAttributeData createAttribute(ItemAttributeData parent, AttributeType type, ItemData item, String key) {
        String attributeId = parent.getAttributeId() + "|" + key;
        AttributeData attributeData = attributeService.get(attributeId);
        if(attributeData == null) {
            throw new InvalidParameterException("Cannot build Item Attribute structue. Key: " + attributeId + " not found");
        }
        ItemAttributeData attribute = new ItemAttributeData();
        attribute.setAttributeId(attributeId);
        attribute.setAttribute(attributeData);
        attribute.setParent(parent);
        attribute.setParentId(parent.getId());
        attribute.setItemId(item.getId());
        attribute.setType(type);
        return attribute;
    }

    @Override
    public void addChild(ItemAttributeData current, ItemAttributeData child) {
        List<ItemAttributeData> children = current.getChildren();
        if(children == null) {
            current.setChildren(new ArrayList<ItemAttributeData>());
        }
        current.getChildren().add(child);
    }

    @Override
    public String getKey(ItemAttributeData attribute) {
        return attribute.getAttributeId();
    }

    @Override
    protected ItemAttributeData buildMap(ItemData item, ItemAttributeData parent, String key, Map<String, Object> value) {
        ItemAttributeData current = createAttribute(parent, OBJECT, item, key);
        return build(item, current, value);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected ItemAttributeData buildList(ItemData item, ItemAttributeData parent, String key, List<Object> value) {
        ItemAttributeData current = createAttribute(parent, LIST, item, key);

        Map<String, Object> parentObj = AttributeUtil.getObject(item.getSampleContent(), getKey(parent));
        List<Object> listValue = new ArrayList<Object>();
        parentObj.put(key, listValue);

        if (!AttributeUtil.contentObject(value)) {
            if(value != null && !value.isEmpty()) {
                AttributeSelector attributeSelector = parseAttritbuteSelectors(value.get(0).toString());
                current.setSelector(attributeSelector);
            }
            return current;
        }

        ItemAttributeData attribute = createAttribute(current, OBJECT, item, key);
        ItemAttributeData child = build(item, attribute, (Map<String, Object>) value.get(0));
        addChild(current, child);
        return current;
    }

    @Override
    protected ItemAttributeData buildString(ItemData item, ItemAttributeData parent, String key, String value) {
        ItemAttributeData current = createAttribute(parent, TEXT, item, key);
        AttributeSelector attributeSelector = parseAttritbuteSelectors(value);
        current.setSelector(attributeSelector);
        return current;
    }
    
    // I@CSS||E@CSS||E@CSS
    private AttributeSelector parseAttritbuteSelectors(String input) {
        if(StringUtil.isBlank(input)) {
            return null;
        }
        String[] texts = input.split(Pattern.quote("|") + Pattern.quote("|"));
        if(texts.length == 0) {
            throw new InvalidParameterException("Cannot parse CSS-Selector value: " + input);
        }
        String e0 = texts[0];
        AttributeSelector targetSelector = parseAttritbuteSelector(e0);
        
        for(int i = 1; i < texts.length; i++) {
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
