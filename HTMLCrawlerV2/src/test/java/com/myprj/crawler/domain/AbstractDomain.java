package com.myprj.crawler.domain;

import org.junit.Assert;

import com.myprj.crawler.model.AuditModel;

/**
 * @author DienNM (DEE)
 */

public abstract class AbstractDomain {

    public void setAudit(AuditData source) {
        source.setCreatedAt(1);
        source.setCreatedBy("System");
        source.setCreatedAt(2);
        source.setUpdatedBy("System");
    }

    public void setAudit(AuditModel source) {
        source.setCreatedAt(1);
        source.setCreatedBy("System");
        source.setCreatedAt(2);
        source.setUpdatedBy("System");
    }

    public void assertAudilt(AuditData source, AuditModel dest) {
        Assert.assertEquals(source.getCreatedAt(), dest.getCreatedAt());
        Assert.assertEquals(source.getCreatedBy(), dest.getCreatedBy());
        Assert.assertEquals(source.getUpdatedAt(), dest.getUpdatedAt());
        Assert.assertEquals(source.getUpdatedBy(), dest.getUpdatedBy());
    }

    public void assertAudilt(AuditModel source, AuditData dest) {
        Assert.assertEquals(source.getCreatedAt(), dest.getCreatedAt());
        Assert.assertEquals(source.getCreatedBy(), dest.getCreatedBy());
        Assert.assertEquals(source.getUpdatedAt(), dest.getUpdatedAt());
        Assert.assertEquals(source.getUpdatedBy(), dest.getUpdatedBy());
    }

}
