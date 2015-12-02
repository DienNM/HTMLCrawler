package com.myprj.crawler.domain.config;

import java.util.ArrayList;
import java.util.List;

import com.myprj.crawler.domain.AuditData;
import com.myprj.crawler.enumeration.AttributeType;
import com.myprj.crawler.model.config.AttributeModel;

/**
 * @author DienNM (DEE)
 */

public class ItemAttributeData extends AuditData {
    
    private static final long serialVersionUID = 1L;

    private String id;
    
    private long itemId;
    
    private String name;
    
    private AttributeType type;
    
    private AttributeSelector selector;
    
    private ItemAttributeData parent;
    
    
    private List<ItemAttributeData> children = new ArrayList<ItemAttributeData>();
    
    public ItemAttributeData() {
    }
    
    public static void toDatas(List<AttributeModel> sources, List<ItemAttributeData> dests) {
        for(AttributeModel source : sources) {
            ItemAttributeData dest = new ItemAttributeData();
            toData(source, dest);
            dests.add(dest);
        }
    }
    
    public static void toModels(List<ItemAttributeData> sources, List<AttributeModel> dests) {
        for(ItemAttributeData source : sources) {
            AttributeModel dest = new AttributeModel();
            toModel(source, dest);
            dests.add(dest);
        }
    }
    
    public static void toData(AttributeModel source, ItemAttributeData dest) {
        dest.setId(String.valueOf(source.getId()));
        dest.setName(source.getName());
        dest.setItemId(source.getItemId());
        dest.setType(source.getType());
        toAuditData(source, dest);
    }
    
    
    
    public static void toModel(ItemAttributeData source, AttributeModel dest) {
        //dest.setId();
        dest.setName(source.getName());
        dest.setItemId(source.getItemId());
        dest.setType(source.getType());
        toAuditModel(source, dest);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getItemId() {
        return itemId;
    }

    public void setItemId(long itemId) {
        this.itemId = itemId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AttributeType getType() {
        return type;
    }

    public void setType(AttributeType type) {
        this.type = type;
    }

    public ItemAttributeData getParent() {
        return parent;
    }

    public void setParent(ItemAttributeData parent) {
        this.parent = parent;
    }

    public List<ItemAttributeData> getChildren() {
        return children;
    }

    public void setChildren(List<ItemAttributeData> children) {
        this.children = children;
    }

    public AttributeSelector getSelector() {
        return selector;
    }

    public void setSelector(AttributeSelector selector) {
        this.selector = selector;
    }
}
