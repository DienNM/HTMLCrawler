package com.myprj.crawler.domain.crawl;

import static com.myprj.crawler.enumeration.ResultStatus.NEW;
import static com.myprj.crawler.util.Serialization.deserialize;
import static com.myprj.crawler.util.Serialization.serialize;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.myprj.crawler.annotation.DataTransfer;
import com.myprj.crawler.annotation.EntityTransfer;
import com.myprj.crawler.domain.AuditData;
import com.myprj.crawler.enumeration.ResultStatus;
import com.myprj.crawler.model.crawl.CrawlResultId;
import com.myprj.crawler.model.crawl.CrawlResultModel;
import com.myprj.crawler.service.mapping.Mapping;
import com.myprj.crawler.util.converter.EntityConverter;
import com.myprj.crawler.util.converter.ObjectConverter;

/**
 * @author DienNM (DEE)
 */

public class CrawlResultData extends AuditData {

    private static final long serialVersionUID = 1L;

    @DataTransfer("resultKey")
    @EntityTransfer("result_key")
    private String resultKey;

    @DataTransfer("itemKey")
    @EntityTransfer("item_key")
    private String itemKey;

    @DataTransfer("categoryKey")
    @EntityTransfer("category_key")
    private String categoryKey;

    @DataTransfer("siteKey")
    @EntityTransfer("site_key")
    private String siteKey;

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

    public CrawlResultData() {
    }

    @SuppressWarnings("unchecked")
    public Mapping getDataAsMapping() {
        return new Mapping((Map<String, Object>) detail.get("content"));
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
                CrawlResultId id = src.getId();
                dest.setCategoryKey(id.getCategoryKey());
                dest.setItemKey(id.getItemKey());
                dest.setSiteKey(id.getSiteKey());
                dest.setResultKey(id.getResultKey());

                dest.setDetail(deserialize(src.getDetail(), Map.class));
            }
        });
    }

    public static void toModel(CrawlResultData source, CrawlResultModel dest) {
        EntityConverter.convert2Entity(source, dest, new ObjectConverter<CrawlResultData, CrawlResultModel>() {
            @Override
            public void convert(CrawlResultData src, CrawlResultModel dest) {
                CrawlResultId id = new CrawlResultId();
                id.setCategoryKey(src.getCategoryKey());
                id.setItemKey(src.getItemKey());
                id.setSiteKey(src.getSiteKey());
                id.setResultKey(src.getResultKey());
                dest.setId(id);
                dest.setDetail(serialize(src.getDetail()));
            }
        });
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

    public String getSiteKey() {
        return siteKey;
    }

    public void setSiteKey(String site) {
        this.siteKey = site;
    }

    public String getResultKey() {
        return resultKey;
    }

    public void setResultKey(String resultKey) {
        this.resultKey = resultKey;
    }

}
