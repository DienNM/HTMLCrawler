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
    
    CategoryData getById(long id);
    
    CategoryData getByKey(String key);
    
    List<CategoryData> getAll();
    
    Map<String, CategoryData> getAllMap();
    
    PageResult<CategoryData> getAllWithPaging(Pageable pageable);
    
    List<CategoryData> saveOrUpdate(List<CategoryData> categoryDatas);
    
    CategoryData save(CategoryData category);
    
    CategoryData update(CategoryData category);
    
    void delete(long id);
    
    void delete(List<Long> ids);
    
}
