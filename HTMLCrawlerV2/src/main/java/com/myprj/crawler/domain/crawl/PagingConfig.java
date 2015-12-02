package com.myprj.crawler.domain.crawl;

import static com.myprj.crawler.domain.crawl.PagingConfig.WorkerListType.PAGING;

import java.io.Serializable;

/**
 * @author DienNM (DEE)
 */

public class PagingConfig implements Serializable {
    
    private static final long serialVersionUID = 1L;

    private String end;
    
    private String start;
    
    private WorkerListType workerListType = PAGING;
    
    enum WorkerListType {
        PAGING,
        DATE
    }
    
    public PagingConfig() {
    }
    
    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public WorkerListType getWorkerListType() {
        return workerListType;
    }

    public void setWorkerListType(WorkerListType workerListType) {
        this.workerListType = workerListType;
    }

}
