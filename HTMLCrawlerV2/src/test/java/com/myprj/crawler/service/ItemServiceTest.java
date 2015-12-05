package com.myprj.crawler.service;

import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.myprj.crawler.AbstractTest;
import com.myprj.crawler.domain.config.ItemData;
import com.myprj.crawler.repository.ItemRepository;

/**
 * @author DienNM (DEE)
 */

public class ItemServiceTest extends AbstractTest {
    
    @Autowired
    private ItemService itemService;
    
    @Autowired
    private ItemRepository itemRepository;
    
    private ItemData item1;
    private ItemData item2;
    
    @Before
    @Transactional
    public void startUp() {
        item1 = new ItemData();
        item1.setName("Item 1");
        item1.setCategoryId(1);
        
        item2 = new ItemData();
        item2.setName("Item 2");
        item2.setCategoryId(1);
        
        itemService.save(item1);
        itemService.save(item2);
    }
    
    @After
    @Transactional
    public void tearDown() {
        itemRepository.deleteAll();
    }
    
    @Test
    @Transactional
    public void testGetAll() {
        List<ItemData> items = itemService.getAll();
        Assert.assertEquals(2, items.size());
    }
    
}
