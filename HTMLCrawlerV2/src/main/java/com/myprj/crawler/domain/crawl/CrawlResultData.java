package com.myprj.crawler.domain.crawl;

import static com.myprj.crawler.enumeration.ResultStatus.NEW;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.myprj.crawler.domain.AuditData;
import com.myprj.crawler.domain.config.ItemAttributeData;
import com.myprj.crawler.enumeration.ResultStatus;
import com.myprj.crawler.model.crawl.ResultModel;

/**
 * @author DienNM (DEE)
 */

public class CrawlResultData extends AuditData {

    private static final long serialVersionUID = 1L;

    private long id;

    private long itemId;
    
    private long categoryId;
    
    private String url;

    private ResultStatus status = NEW;

    private Map<String, Object> detail = new HashMap<String, Object>();
    
    public CrawlResultData() {
    }
    
    public static void populate(CrawlResultData result, ItemAttributeData attribute, Object value) {
        Map<String, Object> detail = result.getDetail();
        
        // 1|detail|size|large
        Iterator<String> attNames = attribute.parseAttributeNamesFromId();
        if(attNames.hasNext()) {
            populate(detail, attNames, value);
        }
    }
    
    @SuppressWarnings("unchecked")
    public static void populate(Map<String, Object> detail, Iterator<String> attNames, Object value) {
        if(!attNames.hasNext()) {
            return;
        }
        String attName = attNames.next();
        if(detail.containsKey(attName)) {
            Object object = detail.get(attName);
            if(object instanceof Map) {
                populate((Map<String, Object>) object, attNames, value);
            } else if(object instanceof List) {
                List<Object> list = (List<Object>) object;
                list.add(value);
            } else if(object instanceof String) {
                detail.put(attName, object);
            }
        }
        
    }
    

    public static void toDatas(List<ResultModel> sources, List<CrawlResultData> dests) {
        for(ResultModel source : sources) {
            CrawlResultData dest = new CrawlResultData();
            toData(source, dest);
            dests.add(dest);
        }
    }
    
    public static void toModels(List<CrawlResultData> sources, List<ResultModel> dests) {
        for(CrawlResultData source : sources) {
            ResultModel dest = new ResultModel();
            toModel(source, dest);
            dests.add(dest);
        }
    }
    
    public static void toData(ResultModel source, CrawlResultData dest) {
        dest.setId(source.getId());
        dest.setCategoryId(source.getCategoryId());
        dest.setItemId(source.getId());
        dest.setStatus(source.getStatus());
        dest.setUrl(source.getUrl());
        toAuditData(source, dest);
    }
    
    public static void toModel(CrawlResultData source, ResultModel dest) {
        dest.setId(source.getId());
        dest.setCategoryId(source.getCategoryId());
        dest.setItemId(source.getId());
        dest.setStatus(source.getStatus());
        dest.setUrl(source.getUrl());
        toAuditModel(source, dest);
    }
    
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }

    public long getItemId() {
        return itemId;
    }

    public void setItemId(long itemId) {
        this.itemId = itemId;
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
    
}
