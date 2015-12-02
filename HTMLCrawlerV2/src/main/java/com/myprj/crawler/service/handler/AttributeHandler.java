package com.myprj.crawler.service.handler;

import com.myprj.crawler.domain.HtmlDocument;
import com.myprj.crawler.domain.config.AttributeSelector;
import com.myprj.crawler.enumeration.AttributeType;

/**
 * @author DienNM (DEE)
 */

public interface AttributeHandler {
    
    Object handle(HtmlDocument document, AttributeSelector cssSelector);
    
    void registerHandler();
    
    AttributeType getType();
    
}
