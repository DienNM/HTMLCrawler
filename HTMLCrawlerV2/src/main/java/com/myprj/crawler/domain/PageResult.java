package com.myprj.crawler.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * @author DienNM (DEE)
 */

public class PageResult<T> {
    
    private List<T> content = new ArrayList<T>();
    
    private Pageable pageable;
    
    private long totalPages;
    
    private long totalRecords;

    public List<T> getContent() {
        return content;
    }
    
    public static <S, D> void copy(PageResult<S> source, PageResult<D> dest) {
        dest.setPageable(source.getPageable());
        dest.setTotalPages(source.getTotalPages());
        dest.setTotalRecords(source.getTotalRecords());
    }

    public void setContent(List<T> content) {
        this.content = content;
    }

    public Pageable getPageable() {
        return pageable;
    }

    public void setPageable(Pageable pageable) {
        this.pageable = pageable;
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
