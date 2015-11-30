package com.myprj.crawler.model.crawl;

import com.myprj.crawler.enumeration.CrawlStatus;
import com.myprj.crawler.model.AuditModel;

/**
 * @author DienNM (DEE)
 */

public class CrawlHistory extends AuditModel{

    private static final long serialVersionUID = 1L;
    
    private long id;
    
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

}
