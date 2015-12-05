package com.myprj.crawler.domain;

import org.junit.Assert;
import org.junit.Test;

import com.myprj.crawler.domain.config.AttributeData;
import com.myprj.crawler.enumeration.AttributeType;
import com.myprj.crawler.model.config.AttributeModel;

/**
 * @author DienNM (DEE)
 */

public class AttributeDataTest extends AbstractDomain {

    @Test
    public void testConvertDomain2Entity() {
        AttributeData source = new AttributeData();
        source.setId("1");
        source.setItemId(1);
        source.setName("Att1");
        source.setParentId("2");
        source.setRoot(true);
        source.setType(AttributeType.OBJECT);
        setAudit(source);
        
        AttributeModel dest = new AttributeModel();
        AttributeData.toModel(source, dest);
     
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
        AttributeModel source = new AttributeModel();
        source.setId("1");
        source.setItemId(1);
        source.setName("Att1");
        source.setParentId("2");
        source.setRoot(true);
        source.setType(AttributeType.OBJECT);
        setAudit(source);
        
        AttributeData dest = new AttributeData();
        AttributeData.toData(source, dest);
     
        Assert.assertEquals(source.getId(), dest.getId());
        Assert.assertEquals(source.getItemId(), dest.getItemId());
        Assert.assertEquals(source.getName(), dest.getName());
        Assert.assertEquals(source.getParentId(), dest.getParentId());
        Assert.assertEquals(source.isRoot(), dest.isRoot());
        Assert.assertEquals(source.getType(), dest.getType());
        
        assertAudilt(source, dest);
    }
    
}
