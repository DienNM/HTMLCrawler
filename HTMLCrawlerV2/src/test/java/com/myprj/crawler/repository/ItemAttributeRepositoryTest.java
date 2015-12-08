package com.myprj.crawler.repository;

import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.myprj.crawler.AbstractTest;
import com.myprj.crawler.enumeration.AttributeType;
import com.myprj.crawler.model.config.ItemAttributeModel;

/**
 * @author DienNM (DEE)
 */

public class ItemAttributeRepositoryTest extends AbstractTest {
    
    @Autowired
    private ItemAttributeRepository itemAttributeRepository;
    
    @Before
    public void startUp() {
        prepareData();
    }
    
    @Transactional
    private void prepareData() {
        ItemAttributeModel itemAttribute1 = new ItemAttributeModel();
        itemAttribute1.setId("1|content");
        itemAttribute1.setName("content");
        itemAttribute1.setItemId(1);
        itemAttribute1.setParentId(null);
        itemAttribute1.setType(AttributeType.OBJECT);
        itemAttribute1.setRoot(true);
        
        ItemAttributeModel itemAttribute2 = new ItemAttributeModel();
        itemAttribute2.setId("1|content|name");
        itemAttribute2.setName("name");
        itemAttribute2.setItemId(1);
        itemAttribute2.setParentId(itemAttribute1.getId());
        itemAttribute2.setType(AttributeType.TEXT);
        
        ItemAttributeModel itemAttribute3 = new ItemAttributeModel();
        itemAttribute3.setId("1|content|price");
        itemAttribute3.setName("price");
        itemAttribute3.setItemId(1);
        itemAttribute3.setParentId(itemAttribute1.getId());
        itemAttribute3.setType(AttributeType.TEXT);
        
        ItemAttributeModel itemAttribute4 = new ItemAttributeModel();
        itemAttribute4.setId("2|content");
        itemAttribute4.setName("content");
        itemAttribute4.setParentId(null);
        itemAttribute4.setType(AttributeType.OBJECT);
        itemAttribute4.setItemId(2);
        
        itemAttributeRepository.save(itemAttribute1);
        itemAttributeRepository.save(itemAttribute2);
        itemAttributeRepository.save(itemAttribute3);
        itemAttributeRepository.save(itemAttribute4);
    }
    
    @After
    @Transactional
    public void tearDown() {
        itemAttributeRepository.deleteAll();
    }
    
    @Test
    @Transactional
    public void testFindChildren() {
        List<ItemAttributeModel> children = itemAttributeRepository.findChildren("1|content");
        Assert.assertEquals(2, children.size());
        for(ItemAttributeModel child : children) {
            if(child.getId().equals("1|content|name")) {
                Assert.assertEquals("name", child.getName());
            } else if(child.getId().equals("1|content|price")) {
                Assert.assertEquals("price", child.getName());
            } else {
                Assert.fail();
            }
        }
    }
    
    @Test
    @Transactional
    public void testFindByItemId() {
        List<ItemAttributeModel> attributes = itemAttributeRepository.findByItemId(1);
        Assert.assertEquals(3, attributes.size());
        
        attributes = itemAttributeRepository.findByItemId(2);
        Assert.assertEquals(1, attributes.size());
        
        attributes = itemAttributeRepository.findByItemId(3);
        Assert.assertEquals(0, attributes.size());
    }
    
    @Test
    @Transactional
    public void testFindRootByItemId() {
        ItemAttributeModel attribute = itemAttributeRepository.findRootByItemId(1);
        Assert.assertNotNull(attribute);
        Assert.assertEquals("1|content", attribute.getId());
    }
    
}
