package com.myprj.crawler.service.handler;

/**
 * @author DienNM (DEE)
 */

public abstract class AttributeHandlerSupport implements AttributeHandler {
    
    protected String returnNormalizeString(Object object) {
        if(object == null) {
            return "";
        }
        return object.toString().trim();
    }
    
}
