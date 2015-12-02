package com.myprj.crawler.domain.crawl;

import static com.myprj.crawler.enumeration.WorkerStatus.Created;

import java.util.ArrayList;
import java.util.List;

import com.myprj.crawler.domain.AuditData;
import com.myprj.crawler.enumeration.WorkerStatus;
import com.myprj.crawler.model.crawl.WorkerModel;

/**
 * @author DienNM (DEE)
 */

public class WorkerData extends AuditData {

    private static final long serialVersionUID = 1L;
    
    private long id;
    
    private String name;
    
    private String description;
    
    private int threads = 1;

    private int attemptTimes = 3;

    private int delayTime = 1000;
    
    private WorkerStatus status = Created;
    
    private List<WorkerItemData> workerItems = new ArrayList<WorkerItemData>();
    
    public WorkerData() {
    }
    
    public static void toDatas(List<WorkerModel> sources, List<WorkerData> dests) {
        for(WorkerModel source : sources) {
            WorkerData dest = new WorkerData();
            toData(source, dest);
            dests.add(dest);
        }
    }
    
    public static void toModels(List<WorkerData> sources, List<WorkerModel> dests) {
        for(WorkerData source : sources) {
            WorkerModel dest = new WorkerModel();
            toModel(source, dest);
            dests.add(dest);
        }
    }
    
    public static void toData(WorkerModel source, WorkerData dest) {
        dest.setId(source.getId());
        dest.setName(source.getName());
        dest.setDescription(source.getDescription());
        dest.setAttemptTimes(source.getAttemptTimes());
        dest.setDelayTime(source.getDelayTime());
        dest.setStatus(source.getStatus());
        dest.setThreads(source.getThreads());
        toAuditData(source, dest);
    }
    
    
    
    public static void toModel(WorkerData source, WorkerModel dest) {
        dest.setId(source.getId());
        dest.setName(source.getName());
        dest.setDescription(source.getDescription());
        dest.setAttemptTimes(source.getAttemptTimes());
        dest.setDelayTime(source.getDelayTime());
        dest.setStatus(source.getStatus());
        dest.setThreads(source.getThreads());
        toAuditModel(source, dest);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public int getThreads() {
        return threads;
    }

    public void setThreads(int threads) {
        this.threads = threads;
    }

    public int getAttemptTimes() {
        return attemptTimes;
    }

    public void setAttemptTimes(int attemptTimes) {
        this.attemptTimes = attemptTimes;
    }

    public int getDelayTime() {
        return delayTime;
    }

    public void setDelayTime(int delayTime) {
        this.delayTime = delayTime;
    }

    public WorkerStatus getStatus() {
        return status;
    }

    public void setStatus(WorkerStatus status) {
        this.status = status;
    }

    public List<WorkerItemData> getWorkerItems() {
        return workerItems;
    }

    public void setWorkerItems(List<WorkerItemData> workerItems) {
        this.workerItems = workerItems;
    }

}
