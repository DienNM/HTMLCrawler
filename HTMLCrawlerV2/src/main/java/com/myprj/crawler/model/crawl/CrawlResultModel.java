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

    @Column(name = "request_id", length = 30)
    private String requestId;

    @Column(name = "item_key")
    private String itemKey;

    @Column(name = "category_key")
    private String categoryKey;

    @Column(name = "site")
    private String siteKey;

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

    public String getItemKey() {
        return itemKey;
    }

    public void setItemKey(String itemKey) {
        this.itemKey = itemKey;
    }

    public String getCategoryKey() {
        return categoryKey;
    }

    public void setCategoryKey(String categoryKey) {
        this.categoryKey = categoryKey;
    }

    public String getSiteKey() {
        return siteKey;
    }

    public void setSiteKey(String site) {
        this.siteKey = site;
    }

}
