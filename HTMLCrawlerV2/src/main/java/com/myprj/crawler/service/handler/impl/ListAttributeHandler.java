package com.myprj.crawler.service.handler.impl;

import java.util.ArrayList;
import java.util.List;

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

/**
 * @author DienNM (DEE)
 */
@Service("listAttributeHandler")
public class ListAttributeHandler extends AttributeHandlerSupport {

    @Override
    public AttributeType getType() {
        return AttributeType.LIST;
    }

    @Override
    @PostConstruct
    public void registerHandler() {
        HandlerRegister.register(getType(), this);
    }

    @Override
    public Object handle(HtmlDocument document, WorkerItemAttributeData current) {
        List<WorkerItemAttributeData> children = current.getChildren();
        List<Object> values = new ArrayList<Object>();
        if(children == null || children.isEmpty()) {
            return values;
        }
        WorkerItemAttributeData child = children.get(0);
        AttributeSelector cssSelector = child.getSelector();
        if(isEmptySelector(cssSelector)) {
            return values;
        }
        Elements elements = document.getDocument().select(cssSelector.getSelector());
        if (elements == null || elements.isEmpty()) {
            return values;
        }
        for (Element element : elements) {
            Object object = HandlerRegister.getHandler(child.getType()).handle(element, child);
            if(object != null) {
                values.add(object);
            }
        }
        return values;
    }

    @Override
    public Object handle(Element element, WorkerItemAttributeData current) {
        List<WorkerItemAttributeData> children = current.getChildren();
        List<Object> values = new ArrayList<Object>();
        if(children == null || children.isEmpty()) {
            return values;
        }
        WorkerItemAttributeData child = children.get(0);
        AttributeSelector cssSelector = child.getSelector();
        if(isEmptySelector(cssSelector)) {
            return values;
        }
        Elements elements = element.select(cssSelector.getSelector());
        if (elements == null || elements.isEmpty()) {
            return values;
        }
        for (Element elm : elements) {
            Object object = HandlerRegister.getHandler(child.getType()).handle(elm, child);
            if(object != null) {
                values.add(object);
            }
        }
        return values;
    }

    @Override
    public Object handle(HtmlDocument document, AttributeSelector cssSelector) {
        List<Object> values = new ArrayList<Object>();
        if(isEmptySelector(cssSelector)) {
            return values;
        }
        Elements elements = document.getDocument().select(cssSelector.getSelector());
        if (elements == null || elements.isEmpty()) {
            return values;
        }
        for (Element element : elements) {
            String text = pickupString(element, cssSelector);
            if(!StringUtils.isEmpty(text)) {
                values.add(text);
            }
        }
        return values;
    }

}
