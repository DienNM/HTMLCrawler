package com.myprj.crawler.model.crawl;

import static com.myprj.crawler.enumeration.WorkerStatus.Created;

import java.util.ArrayList;
import java.util.List;

import com.myprj.crawler.enumeration.WorkerStatus;
import com.myprj.crawler.model.AuditModel;

/**
 * @author DienNM (DEE)
 */

public class WorkerModel extends AuditModel {

    private static final long serialVersionUID = 1L;
    
    private long id;
    
    private String name;
    
    private String description;
    
    private int threads = 2;
    
    private int attemptTimes;
    
    private int delayTime = 500;
    
    private WorkerStatus status = Created;
    
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
