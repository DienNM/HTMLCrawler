package com.myprj.crawler.web.dto;

import java.io.Serializable;

import com.myprj.crawler.annotation.DataTransfer;
import com.myprj.crawler.web.mapping.ObjectCreation;

/**
 * @author DienNM (DEE)
 */

public class CategoryDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @DataTransfer("id")
    private long id;

    @DataTransfer("name")
    private String name;

    @DataTransfer("description")
    private String description;

    @DataTransfer("parentCategoryId")
    private long parentCategoryId;
    
    public static ObjectCreation<CategoryDTO> creation() {
        return new ObjectCreation<CategoryDTO>() {
            
            @Override
            public CategoryDTO create() {
                return new CategoryDTO();
            }
        };
    }

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

    public long getParentCategoryId() {
        return parentCategoryId;
    }

    public void setParentCategoryId(long parentCategoryId) {
        this.parentCategoryId = parentCategoryId;
    }

}
