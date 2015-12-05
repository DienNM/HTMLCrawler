package com.myprj.crawler.service;

import java.util.List;

import com.myprj.crawler.domain.PageResult;
import com.myprj.crawler.domain.Pageable;
import com.myprj.crawler.domain.config.CategoryData;

/**
 * @author DienNM (DEE)
 */

public interface CategoryService {
    
    long count();
    
    CategoryData getById(long id);
    
    List<CategoryData> getAll();
    
    PageResult<CategoryData> getAllWithPaging(Pageable pageable);
    
    CategoryData save(CategoryData category);
}
