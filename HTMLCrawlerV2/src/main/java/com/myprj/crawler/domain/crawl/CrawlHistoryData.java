package com.myprj.crawler.domain.crawl;

import java.io.Serializable;
import java.util.List;

import com.myprj.crawler.annotation.EntityTransfer;
import com.myprj.crawler.enumeration.CrawlStatus;
import com.myprj.crawler.model.crawl.CrawlHistoryModel;
import com.myprj.crawler.util.converter.EntityConverter;

/**
 * @author DienNM (DEE)
 */

public class CrawlHistoryData implements Serializable {

    private static final long serialVersionUID = 1L;

    @EntityTransfer("id")
    private long id;

    @EntityTransfer("worker_id")
    private long workerId;

    @EntityTransfer("eol_date")
    private long eolDate = 0;

    @EntityTransfer("message")
    private String message;

    @EntityTransfer("time_taken")
    private long timeTaken;

    @EntityTransfer("status")
    private CrawlStatus status;

    @EntityTransfer("error_links")
    private String errorLinks;

    public CrawlHistoryData() {
    }

    public static void toDatas(List<CrawlHistoryModel> sources, List<CrawlHistoryData> dests) {
        for (CrawlHistoryModel source : sources) {
            CrawlHistoryData dest = new CrawlHistoryData();
            toData(source, dest);
            dests.add(dest);
        }
    }

    public static void toModels(List<CrawlHistoryData> sources, List<CrawlHistoryModel> dests) {
        for (CrawlHistoryData source : sources) {
            CrawlHistoryModel dest = new CrawlHistoryModel();
            toModel(source, dest);
            dests.add(dest);
        }
    }

    public static void toData(CrawlHistoryModel source, CrawlHistoryData dest) {
        EntityConverter.convert2Data(source, dest);
    }

    public static void toModel(CrawlHistoryData source, CrawlHistoryModel dest) {
        EntityConverter.convert2Entity(source, dest);
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

    public long getEolDate() {
        return eolDate;
    }

    public void setEolDate(long eolDate) {
        this.eolDate = eolDate;
    }

    public CrawlStatus getStatus() {
        return status;
    }

    public void setStatus(CrawlStatus status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getTimeTaken() {
        return timeTaken;
    }

    public void setTimeTaken(long timeTaken) {
        this.timeTaken = timeTaken;
    }

    public String getErrorLinks() {
        return errorLinks;
    }

    public void setErrorLinks(String errorLinks) {
        this.errorLinks = errorLinks;
    }

}
