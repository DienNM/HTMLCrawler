package com.myprj.crawler.repository.impl;

import org.springframework.stereotype.Repository;

import com.myprj.crawler.model.config.CategoryModel;
import com.myprj.crawler.repository.CategoryRepository;

/**
 * @author DienNM (DEE)
 */
@Repository
public class DefaultCategoryRepository extends DefaultGenericDao<CategoryModel, Long> implements CategoryRepository {

    @Override
    protected Class<CategoryModel> getTargetClass() {
        return CategoryModel.class;
    }

}
