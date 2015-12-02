package com.myprj.crawler.domain;

/**
 * @author DienNM (DEE)
 */

public class Pageable {

    private int pageSize;
    
    private int pageIndex;

    public Pageable(int pageSize, int pageIndex) {
        this.pageSize = pageSize;
        this.pageIndex = pageIndex;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public int getPageSize() {
        return pageSize;
    }
}
