package com.myprj.crawler.model.crawl;

import static com.myprj.crawler.enumeration.WorkerStatus.Created;

import java.util.ArrayList;
import java.util.List;

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
import javax.persistence.Transient;

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
    private long id;
    
    @Column(name = "name", length = 50)
    private String name;
    
    @Column(name = "description", length = 10)
    private String description;
    
    @Column(name = "threads")
    private int threads = 1;

    @Column(name = "attempt_times")
    private int attemptTimes = 3;

    @Column(name = "delay_time")
    private int delayTime = 1000;
    
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private WorkerStatus status = Created;
    
    @Transient
    private List<WorkerItemModel> workerItems = new ArrayList<WorkerItemModel>();

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

    public List<WorkerItemModel> getWorkerItems() {
        return workerItems;
    }

    public void setWorkerItems(List<WorkerItemModel> workerItems) {
        this.workerItems = workerItems;
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
}