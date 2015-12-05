package com.myprj.crawler.domain.crawl;

import static com.myprj.crawler.util.Serialization.deserialize;
import static com.myprj.crawler.util.Serialization.serialize;

import java.util.ArrayList;
import java.util.List;

import com.myprj.crawler.annotation.DataTransfer;
import com.myprj.crawler.domain.AuditData;
import com.myprj.crawler.domain.config.AttributeSelector;
import com.myprj.crawler.domain.config.ItemAttributeData;
import com.myprj.crawler.domain.config.ItemData;
import com.myprj.crawler.enumeration.CrawlType;
import com.myprj.crawler.enumeration.Level;
import com.myprj.crawler.model.crawl.WorkerItemModel;

/**
 * @author DienNM (DEE)
 */

public class WorkerItemData extends AuditData {

    private static final long serialVersionUID = 1L;
    
    @DataTransfer("id")
    private long id;

    @DataTransfer("workerId")
    private long workerId;

    @DataTransfer("itemId")
    private long itemId = -1;

    @DataTransfer("url")
    private String url;

    @DataTransfer("level")
    private Level level;

    @DataTransfer("crawlType")
    private CrawlType crawlType;
    
    // For DETAIL
    private ItemData item;

    @DataTransfer("rootItemAttribute")
    private ItemAttributeData rootItemAttribute;
    
    // For List Crawler to get next link
    @DataTransfer("level0Selector")
    private AttributeSelector level0Selector;
    
    // For List Crawler
    @DataTransfer("pagingConfig")
    private PagingConfig pagingConfig = new PagingConfig();
    
    public WorkerItemData() {
    }
    
    public static List<WorkerItemData> toDatas(List<WorkerItemModel> sources) {
        List<WorkerItemData> dests = new ArrayList<WorkerItemData>();
        toDatas(sources, dests);
        return dests;
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
        dest.setItemId(source.getItemId());
        dest.setPagingConfig(source.getPagingConfig());
    }
    
    
    public static void toData(WorkerItemModel source, WorkerItemData dest) {
        dest.setId(source.getId());
        dest.setLevel(source.getLevel());
        dest.setCrawlType(source.getCrawlType());
        dest.setUrl(source.getUrl());
        dest.setWorkerId(source.getWorkerId());
        dest.setItemId(source.getItemId());
        if(source.getPagingConfig() != null) {
            dest.setPagingConfig(deserialize(source.getPagingConfig(), PagingConfig.class));
        }
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

    public AttributeSelector getLevel0Selector() {
        return level0Selector;
    }

    public void setLevel0Selector(AttributeSelector level0Selector) {
        this.level0Selector = level0Selector;
    }

    public long getItemId() {
        return itemId;
    }

    public void setItemId(long itemId) {
        this.itemId = itemId;
    }

    public ItemAttributeData getRootItemAttribute() {
        return rootItemAttribute;
    }

    public void setRootItemAttribute(ItemAttributeData rootItemAttribute) {
        this.rootItemAttribute = rootItemAttribute;
    }

}
