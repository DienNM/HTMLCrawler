package com.myprj.crawler.model.crawl;

import com.myprj.crawler.enumeration.Level;
import com.myprj.crawler.enumeration.WorkerItemTargetType;
import com.myprj.crawler.model.AuditModel;

/**
 * @author DienNM (DEE)
 */

public class WorkerItemModel extends AuditModel implements Comparable<WorkerItemModel> {

    private static final long serialVersionUID = 1L;

    private long id;

    private long workerId;

    private Level level = Level.Level0;

    private String url;
    
    private String nextUrl;

    private WorkerItemTargetType targetType;

    // JSON {"attribute1_id" : "@..."}
    private String attributeCssSelectors;

    // JSON {"toPage" : "1"}
    private String workerItemPagingConfig;
    
    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public WorkerItemTargetType getTargetType() {
        return targetType;
    }

    public void setTargetType(WorkerItemTargetType targetType) {
        this.targetType = targetType;
    }

    public String getAttributeCssSelectors() {
        return attributeCssSelectors;
    }

    public void setAttributeCssSelectors(String attributeCssSelectors) {
        this.attributeCssSelectors = attributeCssSelectors;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getWorkerId() {
        return workerId;
    }

    public void setWorkerId(long workerId) {
        this.workerId = workerId;
    }

    @Override
    public int compareTo(WorkerItemModel o) {
        if (o == null) {
            return 1;
        }
        if (this.getLevel().getOrder() >= o.getLevel().getOrder()) {
            return 1;
        }
        return -1;
    }

    public String getWorkerItemPagingConfig() {
        return workerItemPagingConfig;
    }

    public void setWorkerItemPagingConfig(String WorkerItemPagingConfig) {
        this.workerItemPagingConfig = WorkerItemPagingConfig;
    }

    public String getNextUrl() {
        return nextUrl;
    }

    public void setNextUrl(String nextUrl) {
        this.nextUrl = nextUrl;
    }

}
