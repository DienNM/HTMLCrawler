package com.myprj.crawler.repository;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.myprj.crawler.AbstractTest;
import com.myprj.crawler.model.config.ItemModel;

/**
 * @author DienNM (DEE)
 */

public class ItemRepositoryTest extends AbstractTest {
    
    @Autowired
    private ItemRepository itemRepository;
    
    @Test
    @Transactional
    public void testFindByCategory() {
        ItemModel item1 = new ItemModel();
        item1.setCategoryId("1");
        item1.setName("Item 1");
        item1.setKey("item-key-1");
        
        ItemModel item2 = new ItemModel();
        item2.setCategoryId("1");
        item2.setName("Item 2");
        item2.setKey("item-key-2");
        
        ItemModel item3 = new ItemModel();
        item3.setCategoryId("2");
        item3.setName("Item 3");
        item3.setKey("item-key-3");
        
        itemRepository.save(item1);
        itemRepository.save(item2);
        itemRepository.save(item3);
        
        List<ItemModel> itemModels = itemRepository.findByCategory("1");
        Assert.assertEquals(2, itemModels.size());
        
        itemModels = itemRepository.findByCategory("2");
        Assert.assertEquals(1, itemModels.size());
        
        itemModels = itemRepository.findByCategory("3");
        Assert.assertEquals(0, itemModels.size());
    }
    
}
