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
import com.myprj.crawler.model.config.AttributeModel;

/**
 * @author DienNM (DEE)
 */

public class AttributeRepositoryTest extends AbstractTest {
    
    @Autowired
    private AttributeRepository attributeRepository;
    
    @Before
    public void startUp() {
        prepareData();
    }
    
    @Transactional
    private void prepareData() {
        AttributeModel attribute1 = new AttributeModel();
        attribute1.setId("1|content");
        attribute1.setName("content");
        attribute1.setItemId(1);
        attribute1.setParentId(null);
        attribute1.setType(AttributeType.OBJECT);
        
        AttributeModel attribute2 = new AttributeModel();
        attribute2.setId("1|content|name");
        attribute2.setName("name");
        attribute2.setItemId(1);
        attribute2.setParentId(attribute1.getId());
        attribute2.setType(AttributeType.TEXT);
        
        AttributeModel attribute3 = new AttributeModel();
        attribute3.setId("1|content|price");
        attribute3.setName("price");
        attribute3.setItemId(1);
        attribute3.setParentId(attribute1.getId());
        attribute3.setType(AttributeType.TEXT);
        
        AttributeModel attribute4 = new AttributeModel();
        attribute4.setId("2|content");
        attribute4.setName("content");
        attribute4.setParentId(null);
        attribute4.setType(AttributeType.OBJECT);
        
        attributeRepository.save(attribute1);
        attributeRepository.save(attribute2);
        attributeRepository.save(attribute3);
        attributeRepository.save(attribute4);
    }
    
    @After
    public void tearDown() {
        
    }
    
    @Test
    @Transactional
    public void testFindChildren() {
        List<AttributeModel> children = attributeRepository.findChildren("1|content");
        Assert.assertEquals(2, children.size());
        for(AttributeModel child : children) {
            if(child.getId().equals("1|content|name")) {
                Assert.assertEquals("name", child.getName());
            } else if(child.getId().equals("1|content|price")) {
                Assert.assertEquals("price", child.getName());
            } else {
                Assert.fail();
            }
        }
        
    }
    
}
