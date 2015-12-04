package com.myprj.crawler.web.dto;

import java.io.Serializable;

import com.myprj.crawler.annotation.DataTransfer;
import com.myprj.crawler.web.mapping.ObjectCreation;

/**
 * @author DienNM (DEE)
 */

public class ItemDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @DataTransfer("id")
    private long id;

    @DataTransfer("name")
    private String name;

    @DataTransfer("categoryId")
    private long categoryId;

    @DataTransfer("description")
    private String description;

    @DataTransfer("built")
    private boolean built;
    
    public static ObjectCreation<ItemDTO> creation() {
        return new ObjectCreation<ItemDTO>() {
            
            @Override
            public ItemDTO create() {
                return new ItemDTO();
            }
        };
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

    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isBuilt() {
        return built;
    }

    public void setBuilt(boolean built) {
        this.built = built;
    }
}
