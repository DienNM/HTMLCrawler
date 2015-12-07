package com.myprj.crawler.model.crawl;

import static com.myprj.crawler.enumeration.WorkerStatus.Created;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.myprj.crawler.enumeration.WorkerStatus;
import com.myprj.crawler.model.AuditModel;

/**
 * @author DienNM (DEE)
 */

@Entity
@Access(AccessType.FIELD)
@Table(name = "worker")
public class WorkerModel extends AuditModel {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;
    
    @Column(name = "worker_key", length = 50, nullable=false, unique = true)
    private String key;
    
    @Column(name = "name", length = 50)
    private String name;
    
    @Column(name = "description", length = 100)
    private String description;
    
    @Column(name = "site", length = 100)
    private String site;
    
    @Column(name = "threads")
    private int threads = 1;

    @Column(name = "attempt_times")
    private int attemptTimes = 3;

    @Column(name = "delay_time")
    private int delayTime = 1000;
    
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private WorkerStatus status = Created;
    
    @Column(name = "request_id", length = 25)
    private String requestId;

    @Column(name = "is_built")
    private boolean built;
    
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getThreads() {
        return threads;
    }

    public void setThreads(int threads) {
        this.threads = threads;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDelayTime() {
        return delayTime;
    }

    public void setDelayTime(int delayTime) {
        this.delayTime = delayTime;
    }

    public int getAttemptTimes() {
        return attemptTimes;
    }

    public void setAttemptTimes(int attemptTimes) {
        this.attemptTimes = attemptTimes;
    }

    public WorkerStatus getStatus() {
        return status;
    }

    public void setStatus(WorkerStatus status) {
        this.status = status;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public boolean isBuilt() {
        return built;
    }

    public void setBuilt(boolean built) {
        this.built = built;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
