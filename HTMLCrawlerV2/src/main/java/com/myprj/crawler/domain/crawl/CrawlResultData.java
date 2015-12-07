package com.myprj.crawler.domain.crawl;

import static com.myprj.crawler.enumeration.ResultStatus.NEW;
import static com.myprj.crawler.util.Serialization.deserialize;
import static com.myprj.crawler.util.Serialization.serialize;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.myprj.crawler.annotation.DataTransfer;
import com.myprj.crawler.annotation.EntityTransfer;
import com.myprj.crawler.enumeration.ResultStatus;
import com.myprj.crawler.model.crawl.CrawlResultModel;
import com.myprj.crawler.util.converter.EntityConverter;
import com.myprj.crawler.util.converter.ObjectConverter;

/**
 * @author DienNM (DEE)
 */

public class CrawlResultData implements Serializable {

    private static final long serialVersionUID = 1L;

    @DataTransfer("id")
    @EntityTransfer("id")
    private long id;

    @DataTransfer("itemKey")
    @EntityTransfer("item_key")
    private String itemKey;
    
    @DataTransfer("categoryKey")
    @EntityTransfer("category_key")
    private String categoryKey;
    
    @DataTransfer("site")
    @EntityTransfer("site")
    private String site;

    @DataTransfer("requestId")
    @EntityTransfer("request_id")
    private String requestId;

    @DataTransfer("url")
    @EntityTransfer("url")
    private String url;

    @DataTransfer("status")
    @EntityTransfer("status")
    private ResultStatus status = NEW;

    @DataTransfer("detail")
    private Map<String, Object> detail = new HashMap<String, Object>();

    @DataTransfer("createdAt")
    @EntityTransfer("createdAt")
    private long createdAt;

    public CrawlResultData() {
    }

    public static void toDatas(List<CrawlResultModel> sources, List<CrawlResultData> dests) {
        for (CrawlResultModel source : sources) {
            CrawlResultData dest = new CrawlResultData();
            toData(source, dest);
            dests.add(dest);
        }
    }

    public static void toModels(List<CrawlResultData> sources, List<CrawlResultModel> dests) {
        for (CrawlResultData source : sources) {
            CrawlResultModel dest = new CrawlResultModel();
            toModel(source, dest);
            dests.add(dest);
        }
    }

    @SuppressWarnings("unchecked")
    public static void toData(CrawlResultModel source, CrawlResultData dest) {
        EntityConverter.convert2Data(source, dest, new ObjectConverter<CrawlResultModel, CrawlResultData>() {
            @Override
            public void convert(CrawlResultModel src, CrawlResultData dest) {
                dest.setDetail(deserialize(src.getDetail(), Map.class));
            }
        });
    }

    public static void toModel(CrawlResultData source, CrawlResultModel dest) {
        EntityConverter.convert2Entity(source, dest, new ObjectConverter<CrawlResultData, CrawlResultModel>() {
            @Override
            public void convert(CrawlResultData src, CrawlResultModel dest) {
                dest.setDetail(serialize(src.getDetail()));
            }
        });
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Map<String, Object> getDetail() {
        return detail;
    }

    public void setDetail(Map<String, Object> detail) {
        this.detail = detail;
    }

    public ResultStatus getStatus() {
        return status;
    }

    public void setStatus(ResultStatus status) {
        this.status = status;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public long getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }
    
    public String getRequestId() {
        return requestId;
    }
    
    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getItemKey() {
        return itemKey;
    }

    public void setItemKey(String itemKey) {
        this.itemKey = itemKey;
    }

    public String getCategoryKey() {
        return categoryKey;
    }

    public void setCategoryKey(String categoryKey) {
        this.categoryKey = categoryKey;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

}
