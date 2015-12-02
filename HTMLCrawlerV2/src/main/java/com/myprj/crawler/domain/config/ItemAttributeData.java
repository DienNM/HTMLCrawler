package com.myprj.crawler.domain.config;

import static com.myprj.crawler.util.Serialization.deserialize;

import java.util.ArrayList;
import java.util.List;

import com.myprj.crawler.domain.AuditData;
import com.myprj.crawler.model.config.ItemAttributeModel;
import com.myprj.crawler.util.Serialization;

/**
 * @author DienNM (DEE)
 */

public class ItemAttributeData extends AuditData {
    
    private static final long serialVersionUID = 1L;

    private long id;
    
    private long itemId;
    
    private long parentId = -1;
    
    private String attributeId;
    
    private AttributeSelector selector;
    
    private AttributeData attribute;
    
    private ItemAttributeData parent;
    
    private List<ItemAttributeData> children = new ArrayList<ItemAttributeData>();
    
    public ItemAttributeData() {
    }
    
    public static void toDatas(List<ItemAttributeModel> sources, List<ItemAttributeData> dests) {
        for(ItemAttributeModel source : sources) {
            ItemAttributeData dest = new ItemAttributeData();
            toData(source, dest);
            dests.add(dest);
        }
    }
    
    public static void toModels(List<ItemAttributeData> sources, List<ItemAttributeModel> dests) {
        for(ItemAttributeData source : sources) {
            ItemAttributeModel dest = new ItemAttributeModel();
            toModel(source, dest);
            dests.add(dest);
        }
    }
    
    public static void toData(ItemAttributeModel source, ItemAttributeData dest) {
        dest.setId(source.getId());
        dest.setItemId(source.getItemId());
        dest.setAttributeId(source.getAttributeId());
        dest.setParentId(source.getParentId());
        dest.setSelector(deserialize(source.getSelectorJson(), AttributeSelector.class));
        toAuditData(source, dest);
    }
    
    
    
    public static void toModel(ItemAttributeData source, ItemAttributeModel dest) {
        dest.setId(source.getId());
        dest.setItemId(source.getItemId());
        dest.setAttributeId(source.getAttributeId());
        dest.setParentId(source.getParentId());
        dest.setSelectorJson(Serialization.serialize(source.getSelector()));
        toAuditModel(source, dest);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getItemId() {
        return itemId;
    }

    public void setItemId(long itemId) {
        this.itemId = itemId;
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

    public String getAttributeId() {
        return attributeId;
    }

    public void setAttributeId(String attributeId) {
        this.attributeId = attributeId;
    }

    public AttributeData getAttribute() {
        return attribute;
    }

    public void setAttribute(AttributeData attribute) {
        this.attribute = attribute;
    }

    public long getParentId() {
        return parentId;
    }

    public void setParentId(long parentId) {
        this.parentId = parentId;
    }
}
