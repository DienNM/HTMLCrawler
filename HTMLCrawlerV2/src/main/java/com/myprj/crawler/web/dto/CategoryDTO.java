package com.myprj.crawler.web.dto;

import com.myprj.crawler.annotation.DataTransfer;

/**
 * @author DienNM (DEE)
 */

public class CategoryDTO extends AuditTDO {

    private static final long serialVersionUID = 1L;

    @DataTransfer("id")
    private long id;

    @DataTransfer("key")
    private String key;

    @DataTransfer("name")
    private String name;

    @DataTransfer("description")
    private String description;

    @DataTransfer("parentKey")
    private String parentKey;

    public CategoryDTO() {
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

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getParentKey() {
        return parentKey;
    }

    public void setParentKey(String parentKey) {
        this.parentKey = parentKey;
    }

}
