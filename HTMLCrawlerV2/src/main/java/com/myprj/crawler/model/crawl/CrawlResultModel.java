package com.myprj.crawler.model.crawl;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import com.myprj.crawler.enumeration.ResultStatus;

/**
 * @author DienNM (DEE)
 */
@Entity
@Table(name = "crawl_result")
public class CrawlResultModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    @Column(name = "request_id")
    private String requestId;

    @Column(name = "item_id")
    private long itemId;

    @Column(name = "category_id")
    private long categoryId;
    
    @Column(name = "detail")
    @Lob
    private String detail;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private ResultStatus status;
    
    @Column(name = "url")
    private String url;
    
    @Column(name = "createdAt")
    private long createdAt;
    
    @PrePersist
    public void prePersistAudit() {
        createdAt = Calendar.getInstance().getTimeInMillis();
    }

    public CrawlResultModel() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }

    public long getItemId() {
        return itemId;
    }

    public void setItemId(long itemId) {
        this.itemId = itemId;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public ResultStatus getStatus() {
        return status;
    }

    public void setStatus(ResultStatus status) {
        this.status = status;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    
    public long getCreatedAt() {
        return this.createdAt;
    }
    
    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

}
