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

    @DataTransfer("itemId")
    private long itemId;

    @DataTransfer("categoryWorker")
    private String categoryWorker;

    @DataTransfer("requestId")
    private String requestId;

    @DataTransfer("categoryId")
    private long categoryId;

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

    public long getItemId() {
        return itemId;
    }

    public void setItemId(long itemId) {
        this.itemId = itemId;
    }

    public String getCategoryWorker() {
        return categoryWorker;
    }

    public void setCategoryWorker(String categoryWorker) {
        this.categoryWorker = categoryWorker;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
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
    
    
}
