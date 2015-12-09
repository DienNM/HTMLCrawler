package com.myprj.crawler.domain;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.myprj.crawler.domain.config.ItemContent;
import com.myprj.crawler.domain.config.ItemData;
import com.myprj.crawler.model.config.ItemModel;
import com.myprj.crawler.util.Serialization;

/**
 * @author DienNM (DEE)
 */

public class ItemDataTest extends AbstractDomain {
    @Test
    public void testConvertDomain2Entity() {
        ItemData source = new ItemData();
        source.setId(1);
        source.setCategoryId("1");
        source.setBuilt(true);
        source.setDescription("Description");
        source.setName("Name");

        ItemContent itemContent = new ItemContent();
        Map<String, Object> obj = new HashMap<String, Object>();
        obj.put("key1", "Value1");
        itemContent.setContent(obj);
        source.setSampleContent(itemContent);

        setAudit(source);

        ItemModel dest = new ItemModel();
        ItemData.toModel(source, dest);

        Assert.assertEquals(source.getCategoryId(), dest.getCategoryId());
        Assert.assertEquals(source.getId(), dest.getId());
        Assert.assertEquals(source.getDescription(), dest.getDescription());
        Assert.assertEquals(source.getName(), dest.getName());
        Assert.assertEquals(Serialization.serialize(source.getSampleContent()), dest.getSampleContentJson());
        Assert.assertEquals(source.isBuilt(), dest.isBuilt());

        assertAudilt(source, dest);
    }

    @Test
    public void testConvertEntity2Domain() {
        ItemModel source = new ItemModel();
        source.setId(1);
        source.setCategoryId("1");
        source.setBuilt(true);
        source.setDescription("Description");
        source.setName("Name");

        ItemContent itemContent = new ItemContent();
        Map<String, Object> obj = new HashMap<String, Object>();
        obj.put("key1", "Value1");
        itemContent.setContent(obj);
        source.setSampleContentJson(Serialization.serialize(itemContent));

        setAudit(source);

        ItemData dest = new ItemData();
        ItemData.toData(source, dest);

        Assert.assertEquals(source.getCategoryId(), dest.getCategoryId());
        Assert.assertEquals(source.getId(), dest.getId());
        Assert.assertEquals(source.getDescription(), dest.getDescription());
        Assert.assertEquals(source.getName(), dest.getName());
        Assert.assertEquals(source.getSampleContentJson(), Serialization.serialize(dest.getSampleContent()));
        Assert.assertEquals(source.isBuilt(), dest.isBuilt());

        assertAudilt(source, dest);
    }
}
