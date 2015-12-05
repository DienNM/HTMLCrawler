package com.myprj.crawler.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * @author DienNM (DEE)
 */

public class PageResult<T> {
    
    private List<T> content = new ArrayList<T>();
    
    private Pageable pageable;
    
    public PageResult() {
    }
    
    public PageResult(Pageable pageable) {
        this.pageable = pageable;
    }

    public List<T> getContent() {
        return content;
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
    
}
