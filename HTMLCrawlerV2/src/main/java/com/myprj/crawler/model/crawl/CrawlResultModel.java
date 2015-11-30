package com.myprj.crawler.model.crawl;

import java.util.HashMap;
import java.util.Map;

import com.myprj.crawler.enumeration.ResultStatus;
import com.myprj.crawler.model.AuditModel;

/**
 * @author DienNM (DEE)
 */

public class CrawlResultModel extends AuditModel {

    private static final long serialVersionUID = 1L;

    private long id;

    private long categoryId;

    private long itemId;

    private Map<Long, Object> detail = new HashMap<Long, Object>();

    private ResultStatus status = ResultStatus.MISSING;

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

    public Map<Long, Object> getDetail() {
        return detail;
    }

    public void setDetail(Map<Long, Object> detail) {
        this.detail = detail;
    }

    public ResultStatus getStatus() {
        return status;
    }

    public void setStatus(ResultStatus status) {
        this.status = status;
    }

}
