package com.myprj.crawler.domain.crawl;

import static com.myprj.crawler.util.Serialization.deserialize;
import static com.myprj.crawler.util.Serialization.serialize;

import java.util.List;

import com.myprj.crawler.domain.AuditData;
import com.myprj.crawler.domain.config.AttributeSelector;
import com.myprj.crawler.domain.config.ItemData;
import com.myprj.crawler.enumeration.Level;
import com.myprj.crawler.enumeration.CrawlType;
import com.myprj.crawler.model.crawl.WorkerItemModel;

/**
 * @author DienNM (DEE)
 */

public class WorkerItemData extends AuditData {

    private static final long serialVersionUID = 1L;
    
    private long id;

    private long workerId;

    private String url;

    private Level level;
    
    private CrawlType crawlType;
    
    // For DETAIL
    private ItemData item;
    
    // For List Crawler to get next link
    private AttributeSelector linkSelector;
    
    // For List Crawler
    private PagingConfig pagingConfig;
    
    public WorkerItemData() {
    }
    
    public static void toDatas(List<WorkerItemModel> sources, List<WorkerItemData> dests) {
        for(WorkerItemModel source : sources) {
            WorkerItemData dest = new WorkerItemData();
            toData(source, dest);
            dests.add(dest);
        }
    }
    
    public static void toModels(List<WorkerItemData> sources, List<WorkerItemModel> dests) {
        for(WorkerItemData source : sources) {
            WorkerItemModel dest = new WorkerItemModel();
            toModel(source, dest);
            dests.add(dest);
        }
    }
    
    public static void copy(WorkerItemData source, WorkerItemData dest) {
        dest.setId(source.getId());
        dest.setLevel(source.getLevel());
        dest.setCrawlType(source.getCrawlType());
        dest.setUrl(source.getUrl());
        dest.setWorkerId(source.getWorkerId());
        dest.setPagingConfig(source.getPagingConfig());
    }
    
    
    public static void toData(WorkerItemModel source, WorkerItemData dest) {
        dest.setId(source.getId());
        dest.setLevel(source.getLevel());
        dest.setCrawlType(source.getCrawlType());
        dest.setUrl(source.getUrl());
        dest.setWorkerId(source.getWorkerId());
        if(source.getPagingConfig() != null) {
            dest.setPagingConfig(deserialize(source.getPagingConfig(), PagingConfig.class));
        }
        toAuditData(source, dest);
    }
    
    public static void toModel(WorkerItemData source, WorkerItemModel dest) {
        dest.setId(source.getId());
        dest.setLevel(source.getLevel());
        dest.setCrawlType(source.getCrawlType());
        dest.setUrl(source.getUrl());
        dest.setWorkerId(source.getWorkerId());
        if(source.getPagingConfig() != null) {
            dest.setPagingConfig(serialize(source.getPagingConfig()));
        }
        toAuditModel(source, dest);
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public CrawlType getCrawlType() {
        return crawlType;
    }

    public void setCrawlType(CrawlType type) {
        this.crawlType = type;
    }

    public PagingConfig getPagingConfig() {
        return pagingConfig;
    }

    public void setPagingConfig(PagingConfig pagingConfig) {
        this.pagingConfig = pagingConfig;
    }

    public ItemData getItem() {
        return item;
    }

    public void setItem(ItemData item) {
        this.item = item;
    }

    public AttributeSelector getLinkSelector() {
        return linkSelector;
    }

    public void setLinkSelector(AttributeSelector linkSelector) {
        this.linkSelector = linkSelector;
    }

}
