package com.myprj.crawler.domain;

import org.junit.Assert;
import org.junit.Test;

import com.myprj.crawler.domain.crawl.WorkerData;
import com.myprj.crawler.enumeration.WorkerStatus;
import com.myprj.crawler.model.crawl.WorkerModel;

/**
 * @author DienNM (DEE)
 */

public class WorkerDataTest extends AbstractDomain {
    @Test
    public void testConvertDomain2Entity() {
        WorkerData source = new WorkerData();
        source.setId(1);
        source.setAttemptTimes(10);
        source.setDelayTime(5000);
        source.setSite("Site 1");
        source.setStatus(WorkerStatus.Failed);
        source.setThreads(5);
        source.setDescription("Description");
        source.setName("Name");
        setAudit(source);

        WorkerModel dest = new WorkerModel();
        WorkerData.toModel(source, dest);

        Assert.assertEquals(source.getId(), dest.getId());
        Assert.assertEquals(source.getAttemptTimes(), dest.getAttemptTimes());
        Assert.assertEquals(source.getDelayTime(), dest.getDelayTime());
        Assert.assertEquals(source.getDescription(), dest.getDescription());
        Assert.assertEquals(source.getName(), dest.getName());
        Assert.assertEquals(source.getSite(), dest.getSite());
        Assert.assertEquals(source.getStatus(), dest.getStatus());
        Assert.assertEquals(source.getThreads(), dest.getThreads());

        assertAudilt(source, dest);
    }

    @Test
    public void testConvertEntity2Domain() {
        WorkerModel source = new WorkerModel();
        source.setId(1);
        source.setAttemptTimes(10);
        source.setDelayTime(5000);
        source.setSite("Site 1");
        source.setStatus(WorkerStatus.Failed);
        source.setThreads(5);
        source.setDescription("Description");
        source.setName("Name");

        WorkerData dest = new WorkerData();
        WorkerData.toData(source, dest);

        Assert.assertEquals(source.getId(), dest.getId());
        Assert.assertEquals(source.getAttemptTimes(), dest.getAttemptTimes());
        Assert.assertEquals(source.getDelayTime(), dest.getDelayTime());
        Assert.assertEquals(source.getDescription(), dest.getDescription());
        Assert.assertEquals(source.getName(), dest.getName());
        Assert.assertEquals(source.getSite(), dest.getSite());
        Assert.assertEquals(source.getStatus(), dest.getStatus());
        Assert.assertEquals(source.getThreads(), dest.getThreads());

        assertAudilt(source, dest);
    }
}
