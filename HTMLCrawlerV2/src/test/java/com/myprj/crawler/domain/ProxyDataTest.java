package com.myprj.crawler.domain;

import org.junit.Assert;
import org.junit.Test;

import com.myprj.crawler.domain.crawl.ProxyData;
import com.myprj.crawler.model.ProxyModel;

/**
 * @author DienNM (DEE)
 */

public class ProxyDataTest extends AbstractDomain {
    @Test
    public void testConvertDomain2Entity() {
        ProxyData source = new ProxyData();
        source.setId(1);
        source.setIp("10.10.10.10");
        source.setPort(1234);
        source.setReachable(true);
        setAudit(source);

        ProxyModel dest = new ProxyModel();
        ProxyData.toModel(source, dest);

        Assert.assertEquals(source.getId(), dest.getId());
        Assert.assertEquals(source.getIp(), dest.getIp());
        Assert.assertEquals(source.getPort(), dest.getPort());
        Assert.assertEquals(source.isReachable(), dest.isReachable());

        assertAudilt(source, dest);
    }

    @Test
    public void testConvertEntity2Domain() {
        ProxyModel source = new ProxyModel();
        source.setId(1);
        source.setIp("10.10.10.10");
        source.setPort(1234);
        source.setReachable(true);

        setAudit(source);

        ProxyData dest = new ProxyData();
        ProxyData.toData(source, dest);

        Assert.assertEquals(source.getId(), dest.getId());
        Assert.assertEquals(source.getIp(), dest.getIp());
        Assert.assertEquals(source.getPort(), dest.getPort());
        Assert.assertEquals(source.isReachable(), dest.isReachable());

        assertAudilt(source, dest);
    }
}