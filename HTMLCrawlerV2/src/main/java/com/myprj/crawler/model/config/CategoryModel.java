package com.myprj.crawler.model.config;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.myprj.crawler.model.AuditModel;

/**
 * @author DienNM (DEE)
 */
@Entity
@Table(name = "category")
public class CategoryModel extends AuditModel {

    private static final long serialVersionUID = 1L;
    
    public static final String TABLE_NAME = "category";
    
    @Id
    @Column(name = "id", length = 50)
    private String id;

    @Column(name = "name", length = 50)
    private String name;

    @Column(name = "description", length = 150)
    private String description;

    @Column(name = "parent_key", length = 50)
    private String parentKey;
    
    public CategoryModel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getParentKey() {
        return parentKey;
    }

    public void setParentKey(String parentKey) {
        this.parentKey = parentKey;
    }
}
