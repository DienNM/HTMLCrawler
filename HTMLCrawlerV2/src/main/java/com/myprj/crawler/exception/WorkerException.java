package com.myprj.crawler.exception;

/**
 * @author DienNM (DEE)
 */

public class WorkerException extends Exception {

    private static final long serialVersionUID = 1L;
    
    public WorkerException() {
    }
    
    public WorkerException(String message) {
        super(message);
    }
    
    public WorkerException(Throwable t) {
        super(t);
    }

}
