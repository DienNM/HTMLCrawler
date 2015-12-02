package com.myprj.crawler.domain.config;

import static com.myprj.crawler.enumeration.GlobalStatus.STAGE;

import java.util.List;

import com.myprj.crawler.domain.AuditData;
import com.myprj.crawler.enumeration.GlobalStatus;
import com.myprj.crawler.model.config.CategoryModel;

/**
 * @author DienNM (DEE)
 */

public class CategoryData extends AuditData {
    
    private static final long serialVersionUID = 1L;

    private long id;

    private String name;

    private String description;

    private GlobalStatus status = STAGE;
    
    private long parentCategoryId;

    private CategoryData parentCategory;
    
    public CategoryData() {
    }
    
    public static void toDatas(List<CategoryModel> sources, List<CategoryData> dests) {
        for(CategoryModel source : sources) {
            CategoryData dest = new CategoryData();
            toData(source, dest);
            dests.add(dest);
        }
    }
    
    public static void toModels(List<CategoryData> sources, List<CategoryModel> dests) {
        for(CategoryData source : sources) {
            CategoryModel dest = new CategoryModel();
            toModel(source, dest);
            dests.add(dest);
        }
    }
    
    public static void toData(CategoryModel source, CategoryData dest) {
        dest.setId(source.getId());
        dest.setName(source.getName());
        dest.setDescription(source.getDescription());
        dest.setStatus(source.getStatus());
        dest.setParentCategoryId(source.getParentCategoryId());
        toAuditData(source, dest);
    }
    
    
    
    public static void toModel(CategoryData source, CategoryModel dest) {
        dest.setId(source.getId());
        dest.setName(source.getName());
        dest.setDescription(source.getDescription());
        dest.setStatus(source.getStatus());
        dest.setParentCategoryId(source.getParentCategoryId());
        toAuditModel(source, dest);
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

    public GlobalStatus getStatus() {
        return status;
    }

    public void setStatus(GlobalStatus status) {
        this.status = status;
    }

    public CategoryData getParentCategory() {
        return parentCategory;
    }

    public void setParentCategory(CategoryData parentCategory) {
        this.parentCategory = parentCategory;
    }

    public long getParentCategoryId() {
        return parentCategoryId;
    }

    public void setParentCategoryId(long parentCategoryId) {
        this.parentCategoryId = parentCategoryId;
    }
    
}
