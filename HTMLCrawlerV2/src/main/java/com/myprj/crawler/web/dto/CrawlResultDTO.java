package com.myprj.crawler.web.dto;

import static com.myprj.crawler.enumeration.ResultStatus.NEW;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.myprj.crawler.annotation.DataTransfer;
import com.myprj.crawler.enumeration.ResultStatus;

/**
 * @author DienNM (DEE)
 */

public class CrawlResultDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @DataTransfer("id")
    private long id;

    @DataTransfer("itemKey")
    private String itemKey;

    @DataTransfer("categoryKey")
    private String categoryKey;

    @DataTransfer("site")
    private String site;

    @DataTransfer("requestId")
    private String requestId;

    @DataTransfer("url")
    private String url;

    @DataTransfer("status")
    private ResultStatus status = NEW;

    @DataTransfer("detail")
    private Map<String, Object> detail = new HashMap<String, Object>();

    @DataTransfer("createdAt")
    private long createdAt;
    
    public CrawlResultDTO() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public ResultStatus getStatus() {
        return status;
    }

    public void setStatus(ResultStatus status) {
        this.status = status;
    }

    public Map<String, Object> getDetail() {
        return detail;
    }

    public void setDetail(Map<String, Object> detail) {
        this.detail = detail;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
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

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }
    
    
}
