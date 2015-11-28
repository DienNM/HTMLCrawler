package com.myprj.crawler.exception;

/**
 * @author DienNM (DEE)
 */

public class ProjectCrawlerException extends Exception {

    private static final long serialVersionUID = 1L;
    
    public ProjectCrawlerException(String message) {
        super(message);
    }

    public ProjectCrawlerException(Throwable t) {
        super(t);
    }

}
