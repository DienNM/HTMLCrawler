package com.myprj.crawler.repository;

import java.util.List;

import com.myprj.crawler.model.config.CategoryModel;

/**
 * @author DienNM (DEE)
 */
public interface CategoryRepository extends GenericDao<CategoryModel, String> {
    
    List<CategoryModel> findByParentKey(String parentKey);
    
    void updateParentKey(String oldParentKey, String newParentKey);
    
}
