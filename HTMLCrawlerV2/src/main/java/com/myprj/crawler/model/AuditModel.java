package com.myprj.crawler.model;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

/**
 * @author DienNM (DEE)
 */

@MappedSuperclass
public class AuditModel implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Column(name = "createdAt")
    private long createdAt;

    @Column(name = "createdBy", length = 50)
    private String createdBy;

    @Column(name = "updatedAt")
    private long updatedAt;

    @Column(name = "updatedBy", length = 50)
    private String updatedBy;
    
    public AuditModel() {
    }
    
    @PrePersist
    public void prePersistAudit() {
        createdAt = Calendar.getInstance().getTimeInMillis();
        createdBy = "System";
    }
    
    @PreUpdate
    public void preUpdateAudit() {
        updatedAt = Calendar.getInstance().getTimeInMillis();
        updatedBy = "System";
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
