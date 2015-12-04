package com.myprj.crawler.domain;

import java.io.Serializable;

import com.myprj.crawler.annotation.DataTransfer;
import com.myprj.crawler.model.AuditModel;

/**
 * @author DienNM (DEE)
 */

public class AuditData implements Serializable{

    private static final long serialVersionUID = 1L;
    
    @DataTransfer("createdAt")
    private long createdAt;

    @DataTransfer("createdBy")
    private String createdBy;

    @DataTransfer("updatedAt")
    private long updatedAt;

    @DataTransfer("updatedBy")
    private String updatedBy;
    
    public static void toAuditData(AuditModel source, AuditData dest) {
        dest.setCreatedAt(source.getCreatedAt());
        dest.setCreatedBy(source.getCreatedBy());
        dest.setUpdatedAt(source.getUpdatedAt());
        dest.setUpdatedBy(source.getUpdatedBy());
    }
    
    public static void toAuditModel(AuditData source, AuditModel dest) {
        dest.setCreatedAt(source.getCreatedAt());
        dest.setCreatedBy(source.getCreatedBy());
        dest.setUpdatedAt(source.getUpdatedAt());
        dest.setUpdatedBy(source.getUpdatedBy());
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public long getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(long updatedAt) {
        this.updatedAt = updatedAt;
    }
}
