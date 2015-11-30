package com.myprj.crawler.repository.impl;

import java.util.HashMap;
import java.util.Map;

import com.myprj.crawler.model.config.CategoryModel;
import com.myprj.crawler.repository.CategoryRepository;

/**
 * @author DienNM (DEE)
 */

public class DefaultCategoryRepository implements CategoryRepository {

    private static Map<Long, CategoryModel> repo = new HashMap<Long, CategoryModel>();

    @Override
    public CategoryModel save(CategoryModel category) {
        repo.put(category.getId(), category);
        return category;
    }

    @Override
    public CategoryModel find(long id) {
        return repo.get(id);
    }

}
