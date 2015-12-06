package com.myprj.crawler.service.handler;

import com.myprj.crawler.domain.HtmlDocument;
import com.myprj.crawler.domain.config.ItemAttributeData;
import com.myprj.crawler.enumeration.AttributeType;

/**
 * @author DienNM (DEE)
 */

public interface AttributeHandler {
    
    Object handle(HtmlDocument document,  ItemAttributeData current);
    
    void registerHandler();
    
    AttributeType getType();
    
}
