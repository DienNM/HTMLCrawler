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
@Table(name = "item_attribute")
public class ItemAttributeModel extends AuditModel {

    private static final long serialVersionUID = 1L;
    
    @Id
    @Column(name = "id", length = 100)
    private String id;
    
    @Column(name = "name", length = 20)
    private String name;
    
    @Column(name = "item_id")
    private long itemId;

    @Column(name = "worker_item_id")
    private long workerItemId;

    @Column(name = "is_root")
    private boolean root = false;
    
    @Column(name = "attribute_id", length = 100)
    private String attributeId;

    @Column(name = "selector_json", length = 1000)
    private String selectorJson;
    
    @Column(name = "parent_id")
    private String parentId;
    
    @Column(name = "att_type")
    @Enumerated(EnumType.STRING)
    private AttributeType type;
    
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


    public String getParentId() {
        return parentId;
    }


    public void setParentId(String parentId) {
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getWorkerItemId() {
        return workerItemId;
    }

    public void setWorkerItemId(long workerItemId) {
        this.workerItemId = workerItemId;
    }
}
