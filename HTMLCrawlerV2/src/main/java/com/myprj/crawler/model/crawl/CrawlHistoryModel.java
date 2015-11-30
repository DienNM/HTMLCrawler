package com.myprj.crawler.model.crawl;

import java.util.Date;

import com.myprj.crawler.enumeration.CrawlStatus;
import com.myprj.crawler.model.AuditModel;

/**
 * @author DienNM (DEE)
 */

public class CrawlHistoryModel extends AuditModel{

    private static final long serialVersionUID = 1L;
    
    private long id;
    
    private long workerId;
    
    private Date eolDate;
    
    private CrawlStatus status = CrawlStatus.Created;
    
    private String message;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public CrawlStatus getStatus() {
        return status;
    }

    public void setStatus(CrawlStatus status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getWorkerId() {
        return workerId;
    }

    public void setWorkerId(long workerId) {
        this.workerId = workerId;
    }

    public Date getEolDate() {
        return eolDate;
    }

    public void setEolDate(Date eolDate) {
        this.eolDate = eolDate;
    } 

}
