package com.myprj.crawler.model.crawl;

import static com.myprj.crawler.enumeration.Level.Level0;

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

    private String url;
    
    private String nextUrl;
    
    private Level level = Level0;
    
    private WorkerItemTargetType targetType;

    // JSON {"attribute1_id" : "@..."}
    private String cssSelectors;

    // JSON {"toPage" : "1"}
    private String pagingConfig;
    
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

    public String getcssSelectors() {
        return cssSelectors;
    }

    public void setCssSelectors(String cssSelectors) {
        this.cssSelectors = cssSelectors;
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

    public String getPagingConfig() {
        return pagingConfig;
    }

    public void setPagingConfig(String pagingConfig) {
        this.pagingConfig = pagingConfig;
    }

    public String getNextUrl() {
        return nextUrl;
    }

    public void setNextUrl(String nextUrl) {
        this.nextUrl = nextUrl;
    }

}
