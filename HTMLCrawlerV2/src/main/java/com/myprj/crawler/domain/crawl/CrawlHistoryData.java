package com.myprj.crawler.domain.crawl;

import static com.myprj.crawler.enumeration.CrawlStatus.Created;

import java.util.List;

import com.myprj.crawler.domain.AuditData;
import com.myprj.crawler.enumeration.CrawlStatus;
import com.myprj.crawler.model.crawl.CrawlHistoryModel;

/**
 * @author DienNM (DEE)
 */

public class CrawlHistoryData extends AuditData{
    
    private static final long serialVersionUID = 1L;

    private long id;
    
    private long workerId;
    
    private long eolDate = 0;
    
    private String message;
    
    private long timeTaken;
    
    private CrawlStatus status = Created;
    
    public CrawlHistoryData() {
    }
    
    public static void toDatas(List<CrawlHistoryModel> sources, List<CrawlHistoryData> dests) {
        for(CrawlHistoryModel source : sources) {
            CrawlHistoryData dest = new CrawlHistoryData();
            toData(source, dest);
            dests.add(dest);
        }
    }
    
    public static void toModels(List<CrawlHistoryData> sources, List<CrawlHistoryModel> dests) {
        for(CrawlHistoryData source : sources) {
            CrawlHistoryModel dest = new CrawlHistoryModel();
            toModel(source, dest);
            dests.add(dest);
        }
    }
    
    public static void toData(CrawlHistoryModel source, CrawlHistoryData dest) {
        dest.setId(source.getId());
        dest.setWorkerId(source.getWorkerId());
        dest.setEolDate(source.getEolDate());
        dest.setMessage(source.getMessage());
        dest.setStatus(source.getStatus());
        dest.setTimeTaken(source.getTimeTaken());
    }
    
    public static void toModel(CrawlHistoryData source, CrawlHistoryModel dest) {
        dest.setId(source.getId());
        dest.setWorkerId(source.getWorkerId());
        dest.setEolDate(source.getEolDate());
        dest.setMessage(source.getMessage());
        dest.setStatus(source.getStatus());
        dest.setTimeTaken(source.getTimeTaken());
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
    
}
