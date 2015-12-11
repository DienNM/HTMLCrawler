package com.myprj.crawler.service.handler.impl;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import com.myprj.crawler.domain.HtmlDocument;
import com.myprj.crawler.domain.config.AttributeSelector;
import com.myprj.crawler.domain.config.WorkerItemAttributeData;
import com.myprj.crawler.enumeration.AttributeType;
import com.myprj.crawler.service.handler.AttributeHandlerSupport;
import com.myprj.crawler.service.handler.HandlerRegister;
import com.myprj.crawler.util.StreamUtil;

/**
 * @author DienNM (DEE)
 */
@Service("imageAttributeHandler")
public class ImageAttributeHandler extends AttributeHandlerSupport {

    @Override
    public AttributeType getType() {
        return AttributeType.IMAGE;
    }

    @Override
    @PostConstruct
    public void registerHandler() {
        HandlerRegister.register(getType(), this);
    }

    @Override
    public Object handle(HtmlDocument document, WorkerItemAttributeData current) {
        AttributeSelector cssSelector = current.getSelector();
        if(isEmptySelector(cssSelector)) {
            return null;
        }
        Elements elements = document.getDocument().select(cssSelector.getSelector());
        if (elements == null || elements.isEmpty()) {
            return null;
        }
        return getBytes(elements, cssSelector);
    }

    @Override
    public Object handle(Element element, WorkerItemAttributeData current) {
        AttributeSelector cssSelector = current.getSelector();
        if(isEmptySelector(cssSelector)) {
            return returnNormalizeString(element.text());
        }
        Elements elements = element.select(cssSelector.getSelector());
        if (elements == null || elements.isEmpty()) {
            return null;
        }
        return getBytes(elements, cssSelector);
    }
    
    private Object getBytes(Elements elements, AttributeSelector cssSelector) {
        if(StringUtils.isEmpty(cssSelector.getTargetAttribute())) {
            cssSelector.setTargetAttribute("src");
        }
        String link = pickupString(elements, cssSelector);
        if(StringUtils.isEmpty(link)) {
            return null;
        }
        try {
            byte[] bytes = StreamUtil.readImage(link);
            return StreamUtil.encodeImage(bytes);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Object handle(HtmlDocument document, AttributeSelector cssSelector) {
        if(isEmptySelector(cssSelector)) {
            return null;
        }
        Elements elements = document.getDocument().select(cssSelector.getSelector());
        if (elements == null || elements.isEmpty()) {
            return null;
        }
        return getBytes(elements, cssSelector);
    }

}
