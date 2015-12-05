package com.myprj.crawler.domain;

import com.myprj.crawler.annotation.DataTransfer;

/**
 * @author DienNM (DEE)
 */

public class Pageable {

    @DataTransfer("pageSize")
    private int pageSize;

    @DataTransfer("currentPage")
    private int currentPage;
    
    @DataTransfer("totalPages")
    private long totalPages;

    @DataTransfer("totalRecords")
    private long totalRecords;

    public Pageable(int pageSize, int currentPage) {
        this.pageSize = pageSize;
        this.currentPage = currentPage;
    }
    
    public Pageable(Pageable pageable) {
        this.pageSize = pageable.getPageSize();
        this.currentPage = pageable.getCurrentPage();
        this.totalPages = pageable.getTotalPages();
        this.totalRecords = pageable.getTotalRecords();
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getCurrentPage() {
        return currentPage;
    }
    
    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
    
    public long getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(long totalPages) {
        this.totalPages = totalPages;
    }

    public long getTotalRecords() {
        return totalRecords;
    }

    public void setTotalRecords(long totalRecords) {
        this.totalRecords = totalRecords;
    }

}
