package com.myprj.crawler.service.handler;

import com.myprj.crawler.domain.HtmlDocument;
import com.myprj.crawler.domain.config.AttributeSelector;
import com.myprj.crawler.domain.config.WorkerItemAttributeData;
import com.myprj.crawler.enumeration.AttributeType;

/**
 * @author DienNM (DEE)
 */

public interface AttributeHandler {
    
    Object handle(HtmlDocument document,  WorkerItemAttributeData current);
    
    Object handle(HtmlDocument document,  AttributeSelector attributeSelector);
    
    void registerHandler();
    
    AttributeType getType();
    
}
