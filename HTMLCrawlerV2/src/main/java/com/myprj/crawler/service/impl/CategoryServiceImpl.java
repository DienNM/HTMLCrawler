package com.myprj.crawler.service.impl;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.myprj.crawler.domain.PageResult;
import com.myprj.crawler.domain.Pageable;
import com.myprj.crawler.domain.config.CategoryData;
import com.myprj.crawler.model.config.CategoryModel;
import com.myprj.crawler.repository.CategoryRepository;
import com.myprj.crawler.service.CategoryService;

/**
 * @author DienNM (DEE)
 */
@Service
public class CategoryServiceImpl implements CategoryService {
    
    private Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    @Transactional
    public CategoryData save(CategoryData category) {
        CategoryModel categoryModel = new CategoryModel();
        CategoryData.toModel(category, categoryModel);
        categoryRepository.save(categoryModel);

        CategoryData persitedCategory = new CategoryData();
        CategoryData.toData(categoryModel, persitedCategory);

        return persitedCategory;
    }

    @Override
    @Transactional
    public void update(CategoryData category) {
        CategoryModel categoryModel = categoryRepository.find(category.getId());
        if(categoryModel == null) {
            throw new InvalidParameterException("Cannot find category: " + category.getId());
        }
        
        categoryModel = new CategoryModel();
        CategoryData.toModel(category, categoryModel);
        categoryRepository.update(categoryModel);

        CategoryData.toData(categoryModel, category);
    }

    @Override
    public CategoryData getById(long id) {
        CategoryModel categoryModel = categoryRepository.find(id);
        if (categoryModel == null) {
            logger.warn("Cannot find category: {}", id);
            return null;
        }
        CategoryData categoryData = new CategoryData();
        CategoryData.toData(categoryModel, categoryData);

        return categoryData;
    }

    @Override
    public List<CategoryData> getAll() {
        List<CategoryModel> categoryModels = categoryRepository.findAll();
        List<CategoryData> categoryDatas = new ArrayList<CategoryData>();

        CategoryData.toDatas(categoryModels, categoryDatas);

        return categoryDatas;
    }

    @Override
    public PageResult<CategoryData> getAllWithPaging(Pageable pageable) {
        PageResult<CategoryModel> pageResult = categoryRepository.findAll(pageable);
        
        PageResult<CategoryData> results = new PageResult<CategoryData>(pageResult.getPageable());
        List<CategoryData> categoryDatas = new ArrayList<CategoryData>();
        CategoryData.toDatas(pageResult.getContent(), categoryDatas);
        results.setContent(categoryDatas);

        return results;
    }

    @Override
    public long count() {
        return categoryRepository.count();
    }

}
