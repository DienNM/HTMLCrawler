package com.myprj.crawler.domain.config;

import static com.myprj.crawler.util.Serialization.deserialize;
import static com.myprj.crawler.util.Serialization.serialize;

import java.util.ArrayList;
import java.util.List;

import com.myprj.crawler.domain.AuditData;
import com.myprj.crawler.model.config.ItemModel;

/**
 * @author DienNM (DEE)
 */

public class ItemData extends AuditData {

    private static final long serialVersionUID = 1L;

    private long id;

    private String name;

    private long categoryId;

    private String description;
    
    private boolean built;

    private ItemContent sampleContent;

    private List<AttributeData> attributes = new ArrayList<AttributeData>();

    public ItemData() {
    }

    public static void toDatas(List<ItemModel> sources, List<ItemData> dests) {
        for (ItemModel source : sources) {
            ItemData dest = new ItemData();
            toData(source, dest);
            dests.add(dest);
        }
    }

    public static void toModels(List<ItemData> sources, List<ItemModel> dests) {
        for (ItemData source : sources) {
            ItemModel dest = new ItemModel();
            toModel(source, dest);
            dests.add(dest);
        }
    }

    public static void toData(ItemModel source, ItemData dest) {
        dest.setId(source.getId());
        dest.setName(source.getName());
        dest.setDescription(source.getDescription());
        dest.setCategoryId(source.getCategoryId());
        dest.setBuilt(source.isBuilt());
        dest.setSampleContent(deserialize(source.getSampleContentJson(), ItemContent.class));
        toAuditData(source, dest);
    }

    public static void toModel(ItemData source, ItemModel dest) {
        dest.setId(source.getId());
        dest.setName(source.getName());
        dest.setDescription(source.getDescription());
        dest.setCategoryId(source.getCategoryId());
        dest.setBuilt(source.isBuilt());
        dest.setSampleContentJson(serialize(source.getSampleContent()));
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

    public List<AttributeData> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<AttributeData> attributes) {
        this.attributes = attributes;
    }

    public ItemContent getSampleContent() {
        return sampleContent;
    }

    public void setSampleContent(ItemContent sampleontent) {
        this.sampleContent = sampleontent;
    }

    public boolean isBuilt() {
        return built;
    }

    public void setBuilt(boolean built) {
        this.built = built;
    }
}
