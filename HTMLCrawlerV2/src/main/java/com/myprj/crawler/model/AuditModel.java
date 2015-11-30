package com.myprj.crawler.model;

import java.io.Serializable;

/**
 * @author DienNM (DEE)
 */

public class AuditModel implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private long createdAt;
    
    private String createBy;
    
    private long updatedAt;
    
    private String updateBy;
    
    public AuditModel() {
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public long getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(long updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }
}
