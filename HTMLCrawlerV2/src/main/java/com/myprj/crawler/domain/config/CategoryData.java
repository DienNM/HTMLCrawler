package com.myprj.crawler.domain.config;

import java.util.List;

import com.myprj.crawler.annotation.DataTransfer;
import com.myprj.crawler.annotation.EntityTransfer;
import com.myprj.crawler.domain.AuditData;
import com.myprj.crawler.model.config.CategoryModel;
import com.myprj.crawler.util.converter.EntityConverter;

/**
 * @author DienNM (DEE)
 */

public class CategoryData extends AuditData {

    private static final long serialVersionUID = 1L;

    @DataTransfer("id")
    @EntityTransfer("id")
    private String id;

    @DataTransfer("name")
    @EntityTransfer("name")
    private String name;

    @DataTransfer("description")
    @EntityTransfer("description")
    private String description;

    @DataTransfer("parentKey")
    @EntityTransfer("parent_key")
    private String parentKey;

    private CategoryData parentCategory;

    public CategoryData() {
    }

    public static void toDatas(List<CategoryModel> sources, List<CategoryData> dests) {
        for (CategoryModel source : sources) {
            CategoryData dest = new CategoryData();
            toData(source, dest);
            dests.add(dest);
        }
    }

    public static void toModels(List<CategoryData> sources, List<CategoryModel> dests) {
        for (CategoryData source : sources) {
            CategoryModel dest = new CategoryModel();
            toModel(source, dest);
            dests.add(dest);
        }
    }

    public static void toData(CategoryModel source, CategoryData dest) {
        EntityConverter.convert2Data(source, dest);
    }

    public static void toModel(CategoryData source, CategoryModel dest) {
        EntityConverter.convert2Entity(source, dest);
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

    public CategoryData getParentCategory() {
        return parentCategory;
    }

    public void setParentCategory(CategoryData parentCategory) {
        this.parentCategory = parentCategory;
    }

    public String getParentKey() {
        return parentKey;
    }

    public void setParentKey(String parentKey) {
        this.parentKey = parentKey;
    }

}
