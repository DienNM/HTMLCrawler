package com.myprj.crawler.util;

/**
 * @author DienNM (DEE)
 */

public class Pageable {
    
    private int page = 0;
    private int pageSize = 10;
    
    public Pageable() {
    }
    
    public Pageable(int page, int pageSize) {
        this.setPage(page);
        this.setPageSize(pageSize);
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
    
}
