package com.myprj.crawler.model.config;

import com.myprj.crawler.enumeration.GlobalStatus;
import com.myprj.crawler.model.AuditModel;

/**
 * @author DienNM (DEE)
 */

public class ItemModel extends AuditModel {

    private static final long serialVersionUID = 1L;

    private long id;

    private long categoryId;

    private String name;

    private String description;

    private GlobalStatus status = GlobalStatus.ONLINE;
    
    public ItemModel() {
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

}
