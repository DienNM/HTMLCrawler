package com.myprj.crawler.domain;

/**
 * @author DienNM (DEE)
 **/

public class Result<T> {
    
    private T content;

    private String link;
    
    public Result() {
    }
    
    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
