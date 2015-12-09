package com.myprj.crawler.model.config;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.myprj.crawler.model.AuditModel;

/**
 * @author DienNM (DEE)
 */
@Entity
@Table(name = "item")
public class ItemModel extends AuditModel {

    private static final long serialVersionUID = 1L;
    
    public static final String TABLE_NAME = "item";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", length = 50)
    private String id;
    
    @Column(name = "name", length = 50, nullable = false)
    private String name;

    @Column(name = "category_id", nullable = false)
    private String categoryId;

    @Column(name = "description", length = 150)
    private String description;
    
    @Column(name = "is_built")
    private boolean built = false;
    
    @Column(name = "sample_content_json", length = 1000)
    private String sampleContentJson;

    public ItemModel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
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
    
    public String getSampleContentJson() {
        return sampleContentJson;
    }
    
    public void setSampleContentJson(String sampleContentJson) {
        this.sampleContentJson = sampleContentJson;
    }

    public boolean isBuilt() {
        return built;
    }

    public void setBuilt(boolean built) {
        this.built = built;
    }

}
