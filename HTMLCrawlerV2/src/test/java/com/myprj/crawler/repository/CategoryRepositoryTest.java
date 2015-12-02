package com.myprj.crawler.repository;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.myprj.crawler.AbstractTest;
import com.myprj.crawler.model.config.CategoryModel;

/**
 * @author DienNM (DEE)
 */

public class CategoryRepositoryTest extends AbstractTest {

    @Autowired
    private CategoryRepository categoryRepository;
    
    @Test
    @Transactional
    public void testCount() {
        CategoryModel category1 = new CategoryModel();
        category1.setName("Category 1");
        category1.setDescription("Category Description 1");
        category1.setParentCategoryId(-1);

        CategoryModel category2 = new CategoryModel();
        category2.setName("Category 2");
        category2.setDescription("Category Description 2");
        category2.setParentCategoryId(-1);

        categoryRepository.save(category1);
        categoryRepository.save(category2);
        
        Assert.assertEquals(2, categoryRepository.count());
    }

    @Test
    @Transactional
    public void testFindAll() {
        CategoryModel category1 = new CategoryModel();
        category1.setName("Category 1");
        category1.setDescription("Category Description 1");
        category1.setParentCategoryId(-1);

        CategoryModel category2 = new CategoryModel();
        category2.setName("Category 2");
        category2.setDescription("Category Description 2");
        category2.setParentCategoryId(-1);

        categoryRepository.save(category1);
        categoryRepository.save(category2);

        Assert.assertNotNull(category1.getId());
        Assert.assertNotNull(category2.getId());

        List<CategoryModel> categories = categoryRepository.findAll();
        Assert.assertEquals(2, categories.size());
    }

    @Test
    @Transactional
    public void testFindById() {
        CategoryModel category1 = new CategoryModel();
        category1.setName("Category 1");
        category1.setDescription("Category Description 1");
        category1.setParentCategoryId(-1);

        categoryRepository.save(category1);

        CategoryModel category = categoryRepository.find(category1.getId());
        Assert.assertNotNull(category);
    }

    @Test
    @Transactional
    public void testSave() {
        CategoryModel category1 = new CategoryModel();
        category1.setName("Category 1");
        category1.setDescription("Category Description 1");
        category1.setParentCategoryId(-1);

        categoryRepository.save(category1);

        CategoryModel category = categoryRepository.find(category1.getId());
        Assert.assertNotNull(category);
    }

    @Test
    @Transactional
    public void testUpdate() {
        CategoryModel category1 = new CategoryModel();
        category1.setName("Category 1");
        category1.setDescription("Category Description 1");
        category1.setParentCategoryId(-1);

        categoryRepository.save(category1);

        CategoryModel category = categoryRepository.find(category1.getId());
        Assert.assertNotNull(category);
        Assert.assertEquals("Category 1", category.getName());

        category.setName("New Name");
        categoryRepository.update(category);

        category = categoryRepository.find(category1.getId());
        Assert.assertNotNull(category);
        Assert.assertEquals("New Name", category.getName());
    }

    @Test
    @Transactional
    public void testDelete() {
        CategoryModel category1 = new CategoryModel();
        category1.setName("Category 1");
        category1.setDescription("Category Description 1");
        category1.setParentCategoryId(-1);

        categoryRepository.save(category1);

        CategoryModel category = categoryRepository.find(category1.getId());
        Assert.assertNotNull(category);
        
        categoryRepository.delete(category);
        category = categoryRepository.find(category1.getId());
        Assert.assertNull(category);
    }

}
