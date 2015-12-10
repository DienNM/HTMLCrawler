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

import com.myprj.crawler.enumeration.CrawlType;
import com.myprj.crawler.enumeration.Level;
import com.myprj.crawler.model.AuditModel;

/**
 * @author DienNM (DEE)
 */
@Entity
@Table(name = "worker_item")
public class WorkerItemModel extends AuditModel implements Comparable<WorkerItemModel> {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    @Column(name = "worker_id")
    private long workerId;
    
    @Column(name = "item_key", length = 50)
    private String itemKey;
    
    @Column(name = "site_key", length = 50)
    private String siteKey;

    @Column(name = "url", length = 150)
    private String url;

    @Column(name = "level", length = 10)
    @Enumerated(EnumType.STRING)
    private Level level = Level0;
    
    @Column(name = "crawl_type", length = 15)
    @Enumerated(EnumType.STRING)
    private CrawlType crawlType;

    // JSON {"attribute1_id" : "@..."}
    @Column(name = "level0_selector", length = 1000)
    private String level0Selector;

    // JSON {"toPage" : "1"}
    @Column(name = "paging_config", length = 150)
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

    public CrawlType getCrawlType() {
        return crawlType;
    }

    public void setCrawlType(CrawlType crawlType) {
        this.crawlType = crawlType;
    }

    public String getLevel0Selector() {
        return level0Selector;
    }

    public void setLevel0Selector(String level0Selector) {
        this.level0Selector = level0Selector;
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

    public String getItemKey() {
        return itemKey;
    }

    public void setItemKey(String itemKey) {
        this.itemKey = itemKey;
    }

    public String getSiteKey() {
        return siteKey;
    }

    public void setSiteKey(String siteKey) {
        this.siteKey = siteKey;
    }

}
