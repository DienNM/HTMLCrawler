package com.myprj.crawler.service;

import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.myprj.crawler.AbstractTest;
import com.myprj.crawler.domain.PageResult;
import com.myprj.crawler.domain.Pageable;
import com.myprj.crawler.domain.config.CategoryData;
import com.myprj.crawler.repository.CategoryRepository;

/**
 * @author DienNM (DEE)
 */

public class CategoryServiceTest extends AbstractTest{
    
    @Autowired
    private CategoryService categoryService;
    
    @Autowired
    private CategoryRepository categoryRepository;
    
    private CategoryData category1;
    private CategoryData category2;
    
    @Before
    @Transactional
    public void startUp() {
        category1 = new CategoryData();
        category1.setName("Category 1");
        category1.setDescription("Description 1");
        category1.setParentCategoryId(10);
        category1 = categoryService.save(category1);
        
        category2 = new CategoryData();
        category2.setName("Category 2");
        category2.setDescription("Description 2");
        category2.setParentCategoryId(10);
        category2.setParentCategoryId(category1.getId());
        category2 = categoryService.save(category2);
    }
    
    @After
    @Transactional
    public void tearDown() {
        categoryRepository.deleteAll();
    }
    
    @Test
    @Transactional
    public void testSave() {
        CategoryData category = new CategoryData();
        category.setName("Category 1");
        category.setDescription("Description 1");
        category.setParentCategoryId(10);
        category = categoryService.save(category);
        
        CategoryData categoryData = categoryService.getById(category.getId());
        Assert.assertNotNull(categoryData);
        Assert.assertNotNull(categoryData.getCreatedBy());
        Assert.assertNotNull(categoryData.getCreatedAt());
    }
    
    @Test
    @Transactional
    public void testCount() {
        Assert.assertEquals(2, categoryRepository.count());
    }
    
    @Test
    @Transactional
    public void testGetAll() {
        List<CategoryData> categoryDatas = categoryService.getAll();
        Assert.assertEquals(2, categoryDatas.size());
    }
    
    @Test
    @Transactional
    public void testGetAllWithPaging() {
        PageResult<CategoryData> pageResult = categoryService.getAllWithPaging(new Pageable(1, 0));
        Assert.assertEquals(1, pageResult.getContent().size());
        Assert.assertEquals(2, pageResult.getPageable().getTotalPages());
        Assert.assertEquals(0, pageResult.getPageable().getCurrentPage());
        Assert.assertEquals(1, pageResult.getPageable().getPageSize());
        Assert.assertEquals(2, pageResult.getPageable().getTotalRecords());
        
        pageResult = categoryService.getAllWithPaging(new Pageable(1, 1));
        Assert.assertEquals(1, pageResult.getContent().size());
        Assert.assertEquals(2, pageResult.getPageable().getTotalPages());
        Assert.assertEquals(1, pageResult.getPageable().getCurrentPage());
        Assert.assertEquals(1, pageResult.getPageable().getPageSize());
        Assert.assertEquals(2, pageResult.getPageable().getTotalRecords());
    }
    
}
