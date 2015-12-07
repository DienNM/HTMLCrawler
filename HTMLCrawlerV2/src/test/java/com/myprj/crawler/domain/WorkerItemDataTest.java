package com.myprj.crawler.domain;

import static com.myprj.crawler.util.Serialization.serialize;

import org.junit.Assert;
import org.junit.Test;

import com.myprj.crawler.domain.config.AttributeSelector;
import com.myprj.crawler.domain.crawl.PagingConfig;
import com.myprj.crawler.domain.crawl.WorkerItemData;
import com.myprj.crawler.enumeration.CrawlType;
import com.myprj.crawler.enumeration.Level;
import com.myprj.crawler.model.crawl.WorkerItemModel;

/**
 * @author DienNM (DEE)
 */

public class WorkerItemDataTest extends AbstractDomain {
    @Test
    public void testConvertDomain2Entity() {
        WorkerItemData source = new WorkerItemData();
        source.setId(1);
        source.setCrawlType(CrawlType.DETAIL);
        source.setItemKey("key1");
        source.setLevel(Level.Level2);
        AttributeSelector attributeSelector = new AttributeSelector("div.select{{href}}");
        source.setLevel0Selector(attributeSelector);

        PagingConfig pagingConfig = new PagingConfig();
        pagingConfig.setEnd("2");
        pagingConfig.setStart("0");
        source.setPagingConfig(pagingConfig);

        source.setUrl("http://test");
        source.setWorkerId(100);

        setAudit(source);

        WorkerItemModel dest = new WorkerItemModel();
        WorkerItemData.toModel(source, dest);

        Assert.assertEquals(source.getId(), dest.getId());
        Assert.assertEquals(source.getItemKey(), dest.getItemKey());
        Assert.assertEquals(source.getWorkerId(), dest.getWorkerId());
        Assert.assertEquals(source.getCrawlType(), dest.getCrawlType());
        Assert.assertEquals(source.getLevel(), dest.getLevel());
        Assert.assertEquals(serialize(source.getLevel0Selector()), dest.getLevel0Selector());
        Assert.assertEquals(serialize(source.getPagingConfig()), dest.getPagingConfig());
        Assert.assertEquals(source.getUrl(), dest.getUrl());

        assertAudilt(source, dest);
    }

    @Test
    public void testConvertEntity2Domain() {
        WorkerItemModel source = new WorkerItemModel();
        source.setId(1);
        source.setCrawlType(CrawlType.DETAIL);
        source.setItemKey("key1");
        source.setLevel(Level.Level2);
        AttributeSelector attributeSelector = new AttributeSelector("div.select{{href}}");
        
        source.setLevel0Selector(serialize(attributeSelector));

        PagingConfig pagingConfig = new PagingConfig();
        pagingConfig.setEnd("2");
        pagingConfig.setStart("0");
        source.setPagingConfig(serialize(pagingConfig));

        source.setUrl("http://test");
        source.setWorkerId(100);

        setAudit(source);

        WorkerItemData dest = new WorkerItemData();
        WorkerItemData.toData(source, dest);

        Assert.assertEquals(source.getId(), dest.getId());
        Assert.assertEquals(source.getItemKey(), dest.getItemKey());
        Assert.assertEquals(source.getWorkerId(), dest.getWorkerId());
        Assert.assertEquals(source.getCrawlType(), dest.getCrawlType());
        Assert.assertEquals(source.getLevel(), dest.getLevel());
        Assert.assertEquals(source.getLevel0Selector(), serialize(dest.getLevel0Selector()));
        Assert.assertEquals(source.getPagingConfig(), serialize(dest.getPagingConfig()));
        Assert.assertEquals(source.getUrl(), dest.getUrl());

        assertAudilt(source, dest);
    }
}
