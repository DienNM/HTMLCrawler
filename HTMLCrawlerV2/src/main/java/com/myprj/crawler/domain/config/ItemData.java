package com.myprj.crawler.domain.config;

import static com.myprj.crawler.enumeration.GlobalStatus.ONLINE;

import java.util.ArrayList;
import java.util.List;

import com.myprj.crawler.domain.AuditData;
import com.myprj.crawler.enumeration.GlobalStatus;
import com.myprj.crawler.model.config.ItemModel;

/**
 * @author DienNM (DEE)
 */

public class ItemData extends AuditData {
    
    private static final long serialVersionUID = 1L;

    private long id;

    private long categoryId;

    private String name;

    private String description;

    private GlobalStatus status = ONLINE;
    
    private ItemAttributeData rootItemAttribute;
    
    private List<AttributeData> attributes = new ArrayList<AttributeData>();
    
    private List<ItemAttributeData> itemAttributes = new ArrayList<ItemAttributeData>();

    public ItemData() {
    }
    
    public static void toDatas(List<ItemModel> sources, List<ItemData> dests) {
        for(ItemModel source : sources) {
            ItemData dest = new ItemData();
            toData(source, dest);
            dests.add(dest);
        }
    }
    
    public static void toModels(List<ItemData> sources, List<ItemModel> dests) {
        for(ItemData source : sources) {
            ItemModel dest = new ItemModel();
            toModel(source, dest);
            dests.add(dest);
        }
    }
    
    public static void toData(ItemModel source, ItemData dest) {
        dest.setId(source.getId());
        dest.setName(source.getName());
        dest.setDescription(source.getDescription());
        dest.setStatus(source.getStatus());
        dest.setCategoryId(source.getCategoryId());
        toAuditData(source, dest);
    }
    
    
    
    public static void toModel(ItemData source, ItemModel dest) {
        dest.setId(source.getId());
        dest.setName(source.getName());
        dest.setDescription(source.getDescription());
        dest.setStatus(source.getStatus());
        dest.setCategoryId(source.getCategoryId());
        toAuditModel(source, dest);
    }
    
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public GlobalStatus getStatus() {
        return status;
    }

    public void setStatus(GlobalStatus status) {
        this.status = status;
    }

    public List<AttributeData> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<AttributeData> attributes) {
        this.attributes = attributes;
    }

    public List<ItemAttributeData> getItemAttributes() {
        return itemAttributes;
    }

    public void setItemAttributes(List<ItemAttributeData> itemAttributes) {
        this.itemAttributes = itemAttributes;
    }

    public ItemAttributeData getRootItemAttribute() {
        return rootItemAttribute;
    }

    public void setRootItemAttribute(ItemAttributeData rootItemAttribute) {
        this.rootItemAttribute = rootItemAttribute;
    }
}
