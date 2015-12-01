package com.myprj.crawler.service.handler;

import com.myprj.crawler.domain.HtmlDocument;
import com.myprj.crawler.domain.worker.CssSelector;
import com.myprj.crawler.enumeration.AttributeType;

/**
 * @author DienNM (DEE)
 */

public interface AttributeHandler {
    
    Object handle(HtmlDocument document, CssSelector cssSelector);
    
    void registerHandler();
    
    AttributeType getType();
    
}
