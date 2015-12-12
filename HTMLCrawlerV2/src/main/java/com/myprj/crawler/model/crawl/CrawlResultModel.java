package com.myprj.crawler.model.crawl;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import com.myprj.crawler.enumeration.ResultStatus;
import com.myprj.crawler.model.AuditModel;

/**
 * @author DienNM (DEE)
 */
@Entity
@Table(name = "crawl_result")
public class CrawlResultModel extends AuditModel {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id")
    private CrawlResultId id;

    @Column(name = "request_id", length = 30)
    private String requestId;

    @Column(name = "detail")
    @Lob
    private String detail;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private ResultStatus status;

    @Column(name = "url", length = 1000)
    private String url;

    public CrawlResultModel() {
    }

    public CrawlResultId getId() {
        return id;
    }

    public void setId(CrawlResultId id) {
        this.id = id;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
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

}
