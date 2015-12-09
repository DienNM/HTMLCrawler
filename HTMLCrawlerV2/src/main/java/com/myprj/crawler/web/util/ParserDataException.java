package com.myprj.crawler.web.util;


/**
 * @author DienNM (DEE)
 */

public class ParserDataException extends Exception {

    private static final long serialVersionUID = 1L;
    
    public ParserDataException(String message) {
        super(message);
    }
    
    public ParserDataException(Throwable t) {
        super(t);
    }
    
}
