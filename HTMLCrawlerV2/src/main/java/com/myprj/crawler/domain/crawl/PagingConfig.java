package com.myprj.crawler.domain.crawl;

import java.io.Serializable;

/**
 * @author DienNM (DEE)
 */

public class PagingConfig implements Serializable {
    
    private static final long serialVersionUID = 1L;

    private String end = "1";
    
    private String start = "1";
    
    @Override
    public String toString() {
        return String.format("start=%s, end=%s", start, end);
    }
    
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

}
