package com.myprj.crawler.model.config;

import java.io.Serializable;

import com.myprj.crawler.enumeration.AttributeType;

/**
 * @author DienNM (DEE)
 */

public class AttributeModel implements Serializable{

    private static final long serialVersionUID = 1L;
    
    private long id;
    
    private long itemId;
    
    private String name;
    
    private String description;
    
    private AttributeType type;
    
    public AttributeModel() {
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

    public AttributeType getType() {
        return type;
    }

    public void setType(AttributeType type) {
        this.type = type;
    }
}
