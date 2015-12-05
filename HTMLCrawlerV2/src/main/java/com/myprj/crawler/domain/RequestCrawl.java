package com.myprj.crawler.domain;

/**
 * @author DienNM (DEE)
 */

public class RequestCrawl {
    
    private long workerId;
    
    private String type;
    
    private String requestId;

    public long getWorkerId() {
        return workerId;
    }

    public void setWorkerId(long workerId) {
        this.workerId = workerId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }
}
