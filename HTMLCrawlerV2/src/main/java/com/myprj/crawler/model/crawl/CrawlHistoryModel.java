package com.myprj.crawler.model.crawl;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.myprj.crawler.enumeration.CrawlStatus;

/**
 * @author DienNM (DEE)
 */
@Entity
@Table(name = "crawl_history")
public class CrawlHistoryModel implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    
    @Column(name = "worker_id", nullable = false)
    private long workerId;
    
    @Column(name = "eol_date")
    private long eolDate = 0;
    
    @Column(name = "status", length = 10)
    @Enumerated(EnumType.STRING)
    private CrawlStatus status;
    
    @Column(name = "message", length = 200)
    private String message;

    @Column(name = "time_taken")
    private long timeTaken;
    
    @Column(name = "error_links", length = 4000)
    private String errorLinks;
    
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

    public long getEolDate() {
        return eolDate;
    }

    public void setEolDate(long eolDate) {
        this.eolDate = eolDate;
    }

    public long getTimeTaken() {
        return timeTaken;
    }

    public void setTimeTaken(long timeTaken) {
        this.timeTaken = timeTaken;
    }

    public String getErrorLinks() {
        return errorLinks;
    }

    public void setErrorLinks(String errorLinks) {
        this.errorLinks = errorLinks;
    }

}
