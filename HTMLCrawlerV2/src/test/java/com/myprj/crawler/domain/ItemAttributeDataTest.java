package com.myprj.crawler.domain;

import org.junit.Assert;
import org.junit.Test;

import com.myprj.crawler.domain.config.ItemAttributeData;
import com.myprj.crawler.enumeration.AttributeType;
import com.myprj.crawler.model.config.ItemAttributeModel;

/**
 * @author DienNM (DEE)
 */

public class ItemAttributeDataTest extends AbstractDomain {

    @Test
    public void testConvertDomain2Entity() {
        ItemAttributeData source = new ItemAttributeData();
        source.setId("1");
        source.setItemId("1");
        source.setName("Att1");
        source.setParentId("2");
        source.setRoot(true);
        source.setType(AttributeType.OBJECT);
        setAudit(source);
        
        ItemAttributeModel dest = new ItemAttributeModel();
        ItemAttributeData.toModel(source, dest);
     
        Assert.assertEquals(source.getId(), dest.getId());
        Assert.assertEquals(source.getItemId(), dest.getItemId());
        Assert.assertEquals(source.getName(), dest.getName());
        Assert.assertEquals(source.getParentId(), dest.getParentId());
        Assert.assertEquals(source.isRoot(), dest.isRoot());
        Assert.assertEquals(source.getType(), dest.getType());
        
        assertAudilt(source, dest);
    }
    
    @Test
    public void testConvertEntity2Domain() {
        ItemAttributeModel source = new ItemAttributeModel();
        source.setId("1");
        source.setItemId("1");
        source.setName("Att1");
        source.setParentId("2");
        source.setRoot(true);
        source.setType(AttributeType.OBJECT);
        setAudit(source);
        
        ItemAttributeData dest = new ItemAttributeData();
        ItemAttributeData.toData(source, dest);
     
        Assert.assertEquals(source.getId(), dest.getId());
        Assert.assertEquals(source.getItemId(), dest.getItemId());
        Assert.assertEquals(source.getName(), dest.getName());
        Assert.assertEquals(source.getParentId(), dest.getParentId());
        Assert.assertEquals(source.isRoot(), dest.isRoot());
        Assert.assertEquals(source.getType(), dest.getType());
        
        assertAudilt(source, dest);
    }
    
}
