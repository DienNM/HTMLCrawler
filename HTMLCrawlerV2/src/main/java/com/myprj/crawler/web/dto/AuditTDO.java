package com.myprj.crawler.web.dto;

import java.io.Serializable;

import com.myprj.crawler.annotation.DataTransfer;

/**
 * @author DienNM (DEE)
 */

public abstract class AuditTDO implements Serializable{
    
    private static final long serialVersionUID = 1L;

    @DataTransfer("createdAt")
    private long createdAt;

    @DataTransfer("createdBy")
    private String createdBy;

    @DataTransfer("updatedAt")
    private long updatedAt;

    @DataTransfer("updatedBy")
    private String updatedBy;

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

    public long getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(long updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }
}
