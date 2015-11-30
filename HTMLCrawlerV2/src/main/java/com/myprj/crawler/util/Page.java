package com.myprj.crawler.util;

import java.util.List;

/**
 * @author DienNM (DEE)
 */

public class Page<T> {
    
    private List<T> contents;
    private Pageable pageable;
    
    public Page(List<T> contents, Pageable pageable) {
        this.setContents(contents);
        this.setPageable(pageable);
    }
    
    public List<T> getContents() {
        return contents;
    }
    public void setContents(List<T> contents) {
        this.contents = contents;
    }
    public Pageable getPageable() {
        return pageable;
    }
    public void setPageable(Pageable pageable) {
        this.pageable = pageable;
    }
    
}
