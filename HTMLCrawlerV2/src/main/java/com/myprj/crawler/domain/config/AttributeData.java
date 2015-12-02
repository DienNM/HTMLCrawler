package com.myprj.crawler.domain.config;

import java.util.ArrayList;
import java.util.List;

import com.myprj.crawler.domain.AuditData;
import com.myprj.crawler.enumeration.AttributeType;
import com.myprj.crawler.model.config.AttributeModel;

/**
 * @author DienNM (DEE)
 */

public class AttributeData extends AuditData{
    
    private static final long serialVersionUID = 1L;

    private String id;
    
    private long itemId;
    
    private String name;
    
    private AttributeType type;
    
    private AttributeData parent;
    
    private List<AttributeData> children = new ArrayList<AttributeData>();
    
    public AttributeData() {
        
    }
    
    public static void toDatas(List<AttributeModel> sources, List<AttributeData> dests) {
        for(AttributeModel source : sources) {
            AttributeData dest = new AttributeData();
            toData(source, dest);
            dests.add(dest);
        }
    }
    
    public static void toModels(List<AttributeData> sources, List<AttributeModel> dests) {
        for(AttributeData source : sources) {
            AttributeModel dest = new AttributeModel();
            toModel(source, dest);
            dests.add(dest);
        }
    }
    
    public static void toData(AttributeModel source, AttributeData dest) {
        dest.setId(String.valueOf(source.getId()));
        dest.setName(source.getName());
        dest.setItemId(source.getItemId());
        dest.setType(source.getType());
        toAuditData(source, dest);
    }
    
    
    
    public static void toModel(AttributeData source, AttributeModel dest) {
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

    public AttributeData getParent() {
        return parent;
    }

    public void setParent(AttributeData parent) {
        this.parent = parent;
    }

    public List<AttributeData> getChildren() {
        return children;
    }

    public void setChildren(List<AttributeData> children) {
        this.children = children;
    }
}
