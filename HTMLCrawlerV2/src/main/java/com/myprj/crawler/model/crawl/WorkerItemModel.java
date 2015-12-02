package com.myprj.crawler.model.crawl;

import static com.myprj.crawler.enumeration.Level.Level0;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.myprj.crawler.enumeration.Level;
import com.myprj.crawler.enumeration.WorkerItemType;
import com.myprj.crawler.model.AuditModel;

/**
 * @author DienNM (DEE)
 */
@Entity
@Table(name = "worker_item")
public class WorkerItemModel extends AuditModel implements Comparable<WorkerItemModel> {

    private static final long serialVersionUID = 1L;
    
    public static final String TABLE_NAME = "worker_item";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "worker_id")
    private long workerId;

    @Column(name = "url", length = 150)
    private String url;

    @Column(name = "next_url", length = 150)
    private String nextUrl;

    @Column(name = "level", length = 10)
    @Enumerated(EnumType.STRING)
    private Level level = Level0;
    
    private WorkerItemType targetType;

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

    public WorkerItemType getTargetType() {
        return targetType;
    }

    public void setTargetType(WorkerItemType targetType) {
        this.targetType = targetType;
    }

    public String getCssSelectors() {
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
