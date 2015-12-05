package com.myprj.crawler.domain;

import org.junit.Assert;
import org.junit.Test;

import com.myprj.crawler.domain.crawl.CrawlHistoryData;
import com.myprj.crawler.enumeration.CrawlStatus;
import com.myprj.crawler.model.crawl.CrawlHistoryModel;

/**
 * @author DienNM (DEE)
 */

public class CrawlHistoryDataTest extends AbstractDomain {
    
    @Test
    public void testConvertDomain2Entity() {
        CrawlHistoryData source = new CrawlHistoryData();
        source.setId(1);
        source.setEolDate(1000);
        source.setErrorLinks("Error Links");
        source.setMessage("Crawled some sites");
        source.setStatus(CrawlStatus.CRAWLED);
        source.setTimeTaken(1500);
        source.setWorkerId(20);

        CrawlHistoryModel dest = new CrawlHistoryModel();
        CrawlHistoryData.toModel(source, dest);

        Assert.assertEquals(source.getId(), dest.getId());
        Assert.assertEquals(source.getEolDate(), dest.getEolDate());
        Assert.assertEquals(source.getTimeTaken(), dest.getTimeTaken());
        Assert.assertEquals(source.getWorkerId(), dest.getWorkerId());
        Assert.assertEquals(source.getMessage(), dest.getMessage());
        Assert.assertEquals(source.getStatus(), dest.getStatus());
        Assert.assertEquals(source.getErrorLinks(), dest.getErrorLinks());
    }

    @Test
    public void testConvertEntity2Domain() {
        CrawlHistoryModel source = new CrawlHistoryModel();
        source.setId(1);
        source.setEolDate(1000);
        source.setErrorLinks("Error Links");
        source.setMessage("Crawled some sites");
        source.setStatus(CrawlStatus.CRAWLED);
        source.setTimeTaken(1500);
        source.setWorkerId(20);

        CrawlHistoryData dest = new CrawlHistoryData();
        CrawlHistoryData.toData(source, dest);

        Assert.assertEquals(source.getId(), dest.getId());
        Assert.assertEquals(source.getEolDate(), dest.getEolDate());
        Assert.assertEquals(source.getTimeTaken(), dest.getTimeTaken());
        Assert.assertEquals(source.getWorkerId(), dest.getWorkerId());
        Assert.assertEquals(source.getMessage(), dest.getMessage());
        Assert.assertEquals(source.getStatus(), dest.getStatus());
        Assert.assertEquals(source.getErrorLinks(), dest.getErrorLinks());
    }
}
