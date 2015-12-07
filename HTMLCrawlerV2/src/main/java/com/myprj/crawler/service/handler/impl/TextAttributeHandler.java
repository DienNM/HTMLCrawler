package com.myprj.crawler.service.handler.impl;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import com.myprj.crawler.domain.HtmlDocument;
import com.myprj.crawler.domain.config.AttributeSelector;
import com.myprj.crawler.domain.config.ItemAttributeData;
import com.myprj.crawler.enumeration.AttributeType;
import com.myprj.crawler.service.handler.AttributeHandlerSupport;
import com.myprj.crawler.service.handler.HandlerRegister;

/**
 * @author DienNM (DEE)
 */
@Service("textAttributeHandler")
public class TextAttributeHandler extends AttributeHandlerSupport {

    @Override
    public AttributeType getType() {
        return AttributeType.TEXT;
    }

    @Override
    @PostConstruct
    public void registerHandler() {
        HandlerRegister.register(getType(), this);
    }

    @Override
    public Object handle(HtmlDocument document, ItemAttributeData current) {
        
        AttributeSelector cssSelector  = current.getSelector();
        
        Elements elements = document.getDocument().select(cssSelector.getSelector());
        if (elements == null || elements.isEmpty()) {
            return null;
        }
        String text = null;
        if (StringUtils.isEmpty(cssSelector.getTargetAttribute())) {
            text = elements.first().text();
        }
        text = elements.first().attr(cssSelector.getTargetAttribute());
        
        if(StringUtils.isEmpty(text)) {
            return "";
        }
        return text.trim();
    }

}
