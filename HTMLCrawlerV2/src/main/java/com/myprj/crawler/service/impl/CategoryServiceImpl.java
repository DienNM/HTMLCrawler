package com.myprj.crawler.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public CategoryData update(CategoryData category) {
        CategoryModel categoryModel = new CategoryModel();
        CategoryData.toModel(category, categoryModel);
        categoryRepository.update(categoryModel);

        CategoryData persitedCategory = new CategoryData();
        CategoryData.toData(categoryModel, persitedCategory);

        return persitedCategory;
    }

    @Override
    public CategoryData getById(String id) {
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
    public List<CategoryData> getByIds(List<String> ids) {
        List<CategoryModel> categoryModels = categoryRepository.find(ids);
        List<CategoryData> categoryDatas = new ArrayList<CategoryData>();

        CategoryData.toDatas(categoryModels, categoryDatas);

        return categoryDatas;
    }
    
    @Override
    public List<CategoryData> getAll() {
        List<CategoryModel> categoryModels = categoryRepository.findAll();
        List<CategoryData> categoryDatas = new ArrayList<CategoryData>();

        CategoryData.toDatas(categoryModels, categoryDatas);

        return categoryDatas;
    }
    
    @Override
    public Map<String, CategoryData> getAllMap() {
        Map<String, CategoryData> map = new HashMap<String, CategoryData>();
        List<CategoryData> categories = getAll();
        for(CategoryData category : categories) {
            map.put(category.getId(), category);
        }
        return map;
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

    @Override
    @Transactional
    public List<CategoryData> saveOrUpdate(List<CategoryData> categoryDatas) {
        List<CategoryData> persistedCategories = new ArrayList<CategoryData>();
        for (CategoryData category : categoryDatas) {
            CategoryModel categoryModel = categoryRepository.find(category.getId());
            if (categoryModel == null) {
                categoryModel = new CategoryModel();
                CategoryData.toModel(category, categoryModel);
                categoryRepository.save(categoryModel);
            } else {
                categoryModel.setName(category.getName());
                categoryModel.setDescription(category.getDescription());
                categoryModel.setParentKey(category.getParentKey());
                categoryRepository.update(categoryModel);
            }
            CategoryData persisted = new CategoryData();
            CategoryData.toData(categoryModel, persisted);
            persistedCategories.add(persisted);
        }
        return persistedCategories;
    }

    @Override
    @Transactional
    public void delete(String id) {
        CategoryModel categoryModel = categoryRepository.find(id);
        if(categoryModel != null) {
            categoryRepository.updateParentKey(categoryModel.getId(), categoryModel.getParentKey());
            categoryRepository.deleteById(id);
        }
    }

    @Override
    @Transactional
    public void delete(List<String> ids) {
        List<CategoryModel> categoryModels = new ArrayList<CategoryModel>();
        for(String id : ids) {
            CategoryModel categoryModel = categoryRepository.find(id);
            if(categoryModel == null) {
                logger.error("Category Id " + id + " not found");
                return;
            }
            categoryModels.add(categoryModel);
        }
        
        
        for(CategoryModel categoryModel : categoryModels) {
            categoryRepository.updateParentKey(categoryModel.getId(), categoryModel.getParentKey());
            categoryRepository.deleteById(categoryModel.getId());
        }
    }

}
