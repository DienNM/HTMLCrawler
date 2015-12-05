package com.myprj.crawler.web.dto;

import java.io.Serializable;

/**
 * @author DienNM (DEE)
 */

public class RequestError implements Serializable{

    private static final long serialVersionUID = 1L;
    
    private String field;
    private String message;
    
    public RequestError(String field, String message) {
        this.field = field;
        this.message = message;
    }
    
    public String getField() {
        return field;
    }
    public void setField(String field) {
        this.field = field;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    
    

}
