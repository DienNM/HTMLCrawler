package com.myprj.crawler.service.handler.impl;

import org.jsoup.select.Elements;

import com.myprj.crawler.domain.HtmlDocument;
import com.myprj.crawler.enumeration.AttributeType;
import com.myprj.crawler.service.handler.AttributeHandlerSupport;

/**
 * @author DienNM (DEE)
 */

public class HtmlAttributeHandler extends AttributeHandlerSupport {
    
    @Override
    public AttributeType getType() {
        return AttributeType.HTML;
    }
    
    public HtmlAttributeHandler() {
        registerHandler();
    }

    @Override
    public Object handle(HtmlDocument document, String cssSelector) {
        Elements elements = document.getDocument().select(cssSelector);
        if(elements == null || elements.isEmpty()) {
            return null;
        }
        return elements.first().html();
    }

}
