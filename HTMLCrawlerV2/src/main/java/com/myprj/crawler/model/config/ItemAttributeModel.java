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
public class ItemAttributeModel extends AuditModel {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    
    @Column(name = "item_id")
    private long itemId;
    

    @Column(name = "root")
    private boolean root = false;
    
    @Column(name = "attribute_id", length = 100)
    private String attributeId;

    @Column(name = "selector_json", length = 150)
    private String selectorJson;
    
    @Column(name = "parent_id")
    private long parentId = -1;
    
    @Column(name = "att_type")
    @Enumerated(EnumType.STRING)
    private AttributeType type;
    
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


    public String getAttributeId() {
        return attributeId;
    }


    public void setAttributeId(String attributeId) {
        this.attributeId = attributeId;
    }


    public String getSelectorJson() {
        return selectorJson;
    }


    public void setSelectorJson(String selectorJson) {
        this.selectorJson = selectorJson;
    }


    public long getParentId() {
        return parentId;
    }


    public void setParentId(long parentId) {
        this.parentId = parentId;
    }

    public AttributeType getType() {
        return type;
    }

    public void setType(AttributeType type) {
        this.type = type;
    }

    public boolean isRoot() {
        return root;
    }

    public void setRoot(boolean root) {
        this.root = root;
    }
}
