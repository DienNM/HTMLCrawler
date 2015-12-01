package com.myprj.crawler.service.handler.impl;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.select.Elements;

import com.myprj.crawler.domain.HtmlDocument;
import com.myprj.crawler.domain.worker.CssSelector;
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
    public Object handle(HtmlDocument document, CssSelector cssSelector) {
        Elements elements = document.getDocument().select(cssSelector.getSelector());
        if(elements == null || elements.isEmpty()) {
            return null;
        }
        if(StringUtils.isEmpty(cssSelector.getTargetAttribute())) {
            return elements.first().html();
        } 
        return elements.first().attr(cssSelector.getTargetAttribute());
    }

}
