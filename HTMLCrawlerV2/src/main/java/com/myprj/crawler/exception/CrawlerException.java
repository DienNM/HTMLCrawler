package com.myprj.crawler.exception;

/**
 * @author DienNM (DEE)
 */

public class CrawlerException extends Exception{

    private static final long serialVersionUID = 1L;
    
    public CrawlerException() {
    }
    
    public CrawlerException(String message) {
        super(message);
    }

}
