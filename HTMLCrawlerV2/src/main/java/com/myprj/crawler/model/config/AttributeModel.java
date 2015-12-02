package com.myprj.crawler.model.config;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

import com.myprj.crawler.enumeration.AttributeType;
import com.myprj.crawler.model.AuditModel;

/**
 * @author DienNM (DEE)
 */
@Entity
@Table(name = "attribute")
public class AttributeModel extends AuditModel {

    private static final long serialVersionUID = 1L;
    
    @Id
    @Column(length = 100)
    private String id;
    
    @Column(name = "item_id", nullable = false)
    private long itemId;
    
    @Column(name = "name", nullable = false, length = 50)
    private String name;
    
    @Column(name = "description", length = 100)
    private String description;
    
    @Column(name = "parent_id", length = 100)
    private String parentId;
    
    @Column(name = "att_type", length = 15)
    @Enumerated(EnumType.STRING)
    private AttributeType type;
    
    public AttributeModel() {
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

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }
}
