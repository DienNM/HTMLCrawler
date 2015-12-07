package com.myprj.crawler.domain;

import org.junit.Assert;
import org.junit.Test;

import com.myprj.crawler.domain.config.CategoryData;
import com.myprj.crawler.model.config.CategoryModel;

/**
 * @author DienNM (DEE)
 */

public class CategoryDataTest extends AbstractDomain {

    @Test
    public void testConvertDomain2Entity() {
        CategoryData source = new CategoryData();
        source.setDescription("Category Description");
        source.setId(1);
        source.setKey("1");
        source.setName("Name 1");
        source.setParentKey("category-key");
        setAudit(source);

        CategoryModel dest = new CategoryModel();
        CategoryData.toModel(source, dest);

        Assert.assertEquals(source.getName(), dest.getName());
        Assert.assertEquals(source.getId(), dest.getId());
        Assert.assertEquals(source.getDescription(), dest.getDescription());
        Assert.assertEquals(source.getParentKey(), dest.getParentKey());
        Assert.assertEquals(source.getKey(), dest.getKey());

        assertAudilt(source, dest);
    }

    @Test
    public void testConvertEntity2Domain() {
        CategoryModel source = new CategoryModel();
        source.setDescription("Category Description");
        source.setId(1);
        source.setKey("1");
        source.setName("Name 1");
        source.setParentKey("category-key");
        setAudit(source);

        CategoryData dest = new CategoryData();
        CategoryData.toData(source, dest);

        Assert.assertEquals(source.getName(), dest.getName());
        Assert.assertEquals(source.getId(), dest.getId());
        Assert.assertEquals(source.getDescription(), dest.getDescription());
        Assert.assertEquals(source.getParentKey(), dest.getParentKey());
        Assert.assertEquals(source.getKey(), dest.getKey());

        assertAudilt(source, dest);
    }

}
