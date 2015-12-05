package com.myprj.crawler.repository;

import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.myprj.crawler.AbstractTest;
import com.myprj.crawler.model.config.ItemAttributeModel;

/**
 * @author DienNM (DEE)
 */

public class ItemAttributeRepositoryTest extends AbstractTest {
    
    @Autowired
    private ItemAttributeRepository itemAttributeRepository;
    
    @Before
    @Transactional
    public void startUp() {
        ItemAttributeModel itemAttribute1 = new ItemAttributeModel();
        itemAttribute1.setId("1|1|content");
        itemAttribute1.setAttributeId("1|content");
        

        ItemAttributeModel itemAttribute2 = new ItemAttributeModel();
        itemAttribute2.setId("1|1|content|name");
        itemAttribute2.setAttributeId("1|content|name");
        itemAttribute2.setParentId("1|1|content");
        

        ItemAttributeModel itemAttribute3 = new ItemAttributeModel();
        itemAttribute3.setId("1|1|content|price");
        itemAttribute3.setAttributeId("1|content|price");
        itemAttribute3.setParentId("1|1|content");
        
        itemAttributeRepository.save(itemAttribute1);
        itemAttributeRepository.save(itemAttribute2);
        itemAttributeRepository.save(itemAttribute3);
    }
    
    @After
    @Transactional
    public void tearDown() {
        itemAttributeRepository.deleteAll();
    }
    
    @Test
    @Transactional
    public void testFindChildren() {
        List<ItemAttributeModel> itemAttributes = itemAttributeRepository.findChildren("1|1|content");
        Assert.assertEquals(2, itemAttributes.size());
        
        itemAttributes = itemAttributeRepository.findChildren("2|1|content");
        Assert.assertEquals(0, itemAttributes.size());
    }
}
