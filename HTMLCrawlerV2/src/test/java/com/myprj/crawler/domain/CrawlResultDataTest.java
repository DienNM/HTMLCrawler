package com.myprj.crawler.domain;

import static com.myprj.crawler.util.Serialization.serialize;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.myprj.crawler.domain.crawl.CrawlResultData;
import com.myprj.crawler.enumeration.ResultStatus;
import com.myprj.crawler.model.crawl.CrawlResultModel;

/**
 * @author DienNM (DEE)
 */

public class CrawlResultDataTest extends AbstractDomain {

    @Test
    public void testConvertDomain2Entity() {
        CrawlResultData source = new CrawlResultData();
        source.setId(1);
        source.setCategoryId(2);
        source.setItemId(100);

        Map<String, Object> obj = new HashMap<String, Object>();
        obj.put("key1", "Value1");
        source.setDetail(obj);
        source.setStatus(ResultStatus.COMPLETED);
        source.setUrl("http://test");

        setAudit(source);

        CrawlResultModel dest = new CrawlResultModel();
        CrawlResultData.toModel(source, dest);

        Assert.assertEquals(source.getCategoryId(), dest.getCategoryId());
        Assert.assertEquals(source.getId(), dest.getId());
        Assert.assertEquals(source.getItemId(), dest.getItemId());
        Assert.assertEquals(source.getStatus(), dest.getStatus());
        Assert.assertEquals(source.getUrl(), dest.getUrl());
        Assert.assertEquals(serialize(source.getDetail()), dest.getDetail());

        assertAudilt(source, dest);
    }

    @Test
    public void testConvertEntity2Domain() {
        CrawlResultModel source = new CrawlResultModel();
        source.setId(1);
        source.setCategoryId(2);
        source.setItemId(100);

        Map<String, Object> obj = new HashMap<String, Object>();
        obj.put("key1", "Value1");
        
        source.setDetail(serialize(obj));
        source.setStatus(ResultStatus.COMPLETED);
        source.setUrl("http://test");

        setAudit(source);

        CrawlResultData dest = new CrawlResultData();
        CrawlResultData.toData(source, dest);

        Assert.assertEquals(source.getCategoryId(), dest.getCategoryId());
        Assert.assertEquals(source.getId(), dest.getId());
        Assert.assertEquals(source.getItemId(), dest.getItemId());
        Assert.assertEquals(source.getStatus(), dest.getStatus());
        Assert.assertEquals(source.getUrl(), dest.getUrl());
        Assert.assertEquals(source.getDetail(), serialize(dest.getDetail()));

        assertAudilt(source, dest);
    }

}
