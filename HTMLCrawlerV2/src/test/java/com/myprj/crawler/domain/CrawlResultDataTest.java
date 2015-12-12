package com.myprj.crawler.domain;

import static com.myprj.crawler.util.Serialization.serialize;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.myprj.crawler.domain.crawl.CrawlResultData;
import com.myprj.crawler.enumeration.ResultStatus;
import com.myprj.crawler.model.crawl.CrawlResultId;
import com.myprj.crawler.model.crawl.CrawlResultModel;

/**
 * @author DienNM (DEE)
 */

public class CrawlResultDataTest extends AbstractDomain {

    @Test
    public void testConvertDomain2Entity() {
        CrawlResultData source = new CrawlResultData();
        source.setItemKey("item-key-1");
        source.setCategoryKey("cate-key 1");

        Map<String, Object> obj = new HashMap<String, Object>();
        obj.put("key1", "Value1");
        source.setDetail(obj);
        source.setStatus(ResultStatus.COMPLETED);
        source.setUrl("http://test");

        CrawlResultModel dest = new CrawlResultModel();
        CrawlResultData.toModel(source, dest);

        Assert.assertEquals(source.getCategoryKey(), dest.getId().getCategoryKey());
        Assert.assertEquals(source.getItemKey(), dest.getId().getItemKey());
        Assert.assertEquals(source.getResultKey(), dest.getId().getResultKey());
        Assert.assertEquals(source.getSiteKey(), dest.getId().getSiteKey());
        Assert.assertEquals(source.getStatus(), dest.getStatus());
        Assert.assertEquals(source.getUrl(), dest.getUrl());
        Assert.assertEquals(serialize(source.getDetail()), dest.getDetail());

    }

    @Test
    public void testConvertEntity2Domain() {
        CrawlResultModel source = new CrawlResultModel();
        CrawlResultId id = new CrawlResultId();
        id.setSiteKey("site1");
        id.setCategoryKey("category-1");
        id.setItemKey("item-1");
        id.setResultKey("result-key-1");
        source.setId(id);

        Map<String, Object> obj = new HashMap<String, Object>();
        obj.put("key1", "Value1");
        
        source.setDetail(serialize(obj));
        source.setStatus(ResultStatus.COMPLETED);
        source.setUrl("http://test");

        CrawlResultData dest = new CrawlResultData();
        CrawlResultData.toData(source, dest);

        Assert.assertEquals(id.getCategoryKey(), dest.getCategoryKey());
        Assert.assertEquals(id.getItemKey(), dest.getItemKey());
        Assert.assertEquals(id.getResultKey(), dest.getResultKey());
        Assert.assertEquals(id.getSiteKey(), dest.getSiteKey());
        Assert.assertEquals(source.getStatus(), dest.getStatus());
        Assert.assertEquals(source.getUrl(), dest.getUrl());
        Assert.assertEquals(source.getDetail(), serialize(dest.getDetail()));

    }

}
