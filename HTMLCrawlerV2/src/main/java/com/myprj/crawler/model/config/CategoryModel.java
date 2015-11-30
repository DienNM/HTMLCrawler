package com.myprj.crawler.model.config;

import static com.myprj.crawler.enumeration.GlobalStatus.STAGE;

import com.myprj.crawler.enumeration.GlobalStatus;
import com.myprj.crawler.model.AuditModel;

/**
 * @author DienNM (DEE)
 */

public class CategoryModel extends AuditModel {

    private static final long serialVersionUID = 1L;

    private long id;

    private String name;

    private String description;
    
    private GlobalStatus status = STAGE;
    
    private long parentCategoryId = -1;
    
    public CategoryModel() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public long getParentCategoryId() {
        return parentCategoryId;
    }

    public void setParentCategoryId(long parentCategoryId) {
        this.parentCategoryId = parentCategoryId;
    }

    public GlobalStatus getStatus() {
        return status;
    }

    public void setStatus(GlobalStatus status) {
        this.status = status;
    }

}
