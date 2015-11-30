package com.myprj.crawler.repository;

import com.myprj.crawler.model.config.CategoryModel;

/**
 * @author DienNM (DEE)
 */

public interface CategoryRepository {
    
    CategoryModel save(CategoryModel category);
    
    CategoryModel find(long id);
    
}
