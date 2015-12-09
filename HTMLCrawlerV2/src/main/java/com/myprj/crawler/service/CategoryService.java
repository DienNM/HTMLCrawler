package com.myprj.crawler.service;

import java.util.List;
import java.util.Map;

import com.myprj.crawler.domain.PageResult;
import com.myprj.crawler.domain.Pageable;
import com.myprj.crawler.domain.config.CategoryData;

/**
 * @author DienNM (DEE)
 */

public interface CategoryService {
    
    long count();
    
    CategoryData getById(String id);
    
    List<CategoryData> getByIds(List<String> ids);
    
    List<CategoryData> getAll();
    
    Map<String, CategoryData> getAllMap();
    
    PageResult<CategoryData> getAllWithPaging(Pageable pageable);
    
    List<CategoryData> saveOrUpdate(List<CategoryData> categoryDatas);
    
    CategoryData save(CategoryData category);
    
    CategoryData update(CategoryData category);
    
    void delete(String id);
    
    void delete(List<String> ids);
    
}
