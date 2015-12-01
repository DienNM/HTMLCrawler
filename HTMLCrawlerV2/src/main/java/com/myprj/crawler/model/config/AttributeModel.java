package com.myprj.crawler.model.config;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.myprj.crawler.enumeration.AttributeType;
import com.myprj.crawler.model.AuditModel;

/**
 * @author DienNM (DEE)
 */
@Entity
@Table(name = "item_attribute")
public class AttributeModel extends AuditModel {

    private static final long serialVersionUID = 1L;
    
    public static final String TABLE_NAME = "item_attribute";
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    
    @Column(name = "item_id", nullable = false)
    private long itemId;
    
    @Column(name = "name", nullable = false, length = 50)
    private String name;
    
    @Column(name = "description", length = 100)
    private String description;
    
    @Column(name = "att_type", length = 15)
    @Enumerated(EnumType.STRING)
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
