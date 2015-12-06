package com.myprj.crawler.domain.crawl;

import static com.myprj.crawler.util.Serialization.deserialize;
import static com.myprj.crawler.util.Serialization.serialize;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.myprj.crawler.annotation.DataTransfer;
import com.myprj.crawler.annotation.EntityTransfer;
import com.myprj.crawler.domain.AuditData;
import com.myprj.crawler.domain.config.AttributeSelector;
import com.myprj.crawler.domain.config.ItemAttributeData;
import com.myprj.crawler.domain.config.ItemData;
import com.myprj.crawler.enumeration.CrawlType;
import com.myprj.crawler.enumeration.Level;
import com.myprj.crawler.model.crawl.WorkerItemModel;
import com.myprj.crawler.util.converter.EntityConverter;
import com.myprj.crawler.util.converter.ObjectConverter;

/**
 * @author DienNM (DEE)
 */

public class WorkerItemData extends AuditData {

    private static final long serialVersionUID = 1L;

    @DataTransfer("id")
    @EntityTransfer("id")
    private long id;

    @DataTransfer("workerId")
    @EntityTransfer("worker_id")
    private long workerId;

    @DataTransfer("itemId")
    @EntityTransfer("item_id")
    private long itemId = -1;

    @DataTransfer("url")
    @EntityTransfer("url")
    private String url;

    @DataTransfer("level")
    @EntityTransfer("level")
    private Level level;

    @DataTransfer("crawlType")
    @EntityTransfer("crawl_type")
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

    public static void copy(WorkerItemData source, WorkerItemData dest) {
        dest.setId(source.getId());
        dest.setLevel(source.getLevel());
        dest.setCrawlType(source.getCrawlType());
        dest.setUrl(source.getUrl());
        dest.setWorkerId(source.getWorkerId());
        dest.setItemId(source.getItemId());
        dest.setPagingConfig(source.getPagingConfig());
        dest.setRootItemAttribute(source.getRootItemAttribute());
        dest.setLevel0Selector(source.getLevel0Selector());
    }

    public static void toDatas(List<WorkerItemModel> sources, List<WorkerItemData> dests) {
        for (WorkerItemModel source : sources) {
            WorkerItemData dest = new WorkerItemData();
            toData(source, dest);
            dests.add(dest);
        }
    }

    public static void toModels(List<WorkerItemData> sources, List<WorkerItemModel> dests) {
        for (WorkerItemData source : sources) {
            WorkerItemModel dest = new WorkerItemModel();
            toModel(source, dest);
            dests.add(dest);
        }
    }

    public static void toData(WorkerItemModel source, WorkerItemData dest) {
        EntityConverter.convert2Data(source, dest, new ObjectConverter<WorkerItemModel, WorkerItemData>() {
            @Override
            public void convert(WorkerItemModel src, WorkerItemData dest) {
                if (src.getPagingConfig() != null) {
                    dest.setPagingConfig(deserialize(src.getPagingConfig(), PagingConfig.class));
                }
                if (!StringUtils.isEmpty(src.getLevel0Selector())) {
                    dest.setLevel0Selector(deserialize(src.getLevel0Selector(), AttributeSelector.class));
                }
            }
        });

    }

    public static void toModel(WorkerItemData source, WorkerItemModel dest) {
        EntityConverter.convert2Entity(source, dest, new ObjectConverter<WorkerItemData, WorkerItemModel>() {
            @Override
            public void convert(WorkerItemData src, WorkerItemModel dest) {
                if (src.getPagingConfig() != null) {
                    dest.setPagingConfig(serialize(src.getPagingConfig()));
                }
                if (src.getLevel0Selector() != null) {
                    dest.setLevel0Selector(serialize(src.getLevel0Selector()));
                }
            }
        });
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
