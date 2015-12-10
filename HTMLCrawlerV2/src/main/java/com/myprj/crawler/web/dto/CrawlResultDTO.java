package com.myprj.crawler.web.dto;

import static com.myprj.crawler.enumeration.ResultStatus.NEW;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.myprj.crawler.annotation.DataTransfer;
import com.myprj.crawler.domain.crawl.CrawlResultData;
import com.myprj.crawler.enumeration.ResultStatus;
import com.myprj.crawler.util.converter.DomainConverter;
import com.myprj.crawler.util.converter.ObjectConverter;

/**
 * @author DienNM (DEE)
 */

public class CrawlResultDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @DataTransfer("id")
    private long id;

    @DataTransfer("resultKey")
    private String resultKey;

    @DataTransfer("itemKey")
    private String itemKey;

    @DataTransfer("categoryKey")
    private String categoryKey;

    @DataTransfer("siteKey")
    private String siteKey;

    @DataTransfer("requestId")
    private String requestId;

    @DataTransfer("url")
    private String url;

    @DataTransfer("status")
    private ResultStatus status = NEW;

    @DataTransfer("detail")
    private Map<String, Object> detail = new HashMap<String, Object>();

    @DataTransfer("createdAt")
    private long createdAt;
    
    public static void toDTOs(List<CrawlResultData> sources, List<CrawlResultDTO> dests) {
        for(CrawlResultData source : sources) {
            CrawlResultDTO dest = new CrawlResultDTO();
            toDTO(source, dest);
            dests.add(dest);
        }
    }
    
    public static void toDTO(CrawlResultData source, CrawlResultDTO dest) {
        DomainConverter.convert(source, dest, new ObjectConverter<CrawlResultData, CrawlResultDTO>() {
            @Override
            public void convert(CrawlResultData src, CrawlResultDTO dest) {
            }
        });
    }
    
    public CrawlResultDTO() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public ResultStatus getStatus() {
        return status;
    }

    public void setStatus(ResultStatus status) {
        this.status = status;
    }

    public Map<String, Object> getDetail() {
        return detail;
    }

    public void setDetail(Map<String, Object> detail) {
        this.detail = detail;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
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

    public void setSiteKey(String siteKey) {
        this.siteKey = siteKey;
    }

    public String getResultKey() {
        return resultKey;
    }

    public void setResultKey(String resultKey) {
        this.resultKey = resultKey;
    }
    
    
}
