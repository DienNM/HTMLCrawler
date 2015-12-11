package com.myprj.crawler.service.handler.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;

import com.myprj.crawler.domain.HtmlDocument;
import com.myprj.crawler.domain.config.AttributeSelector;
import com.myprj.crawler.domain.config.WorkerItemAttributeData;
import com.myprj.crawler.domain.crawl.WorkerItemData;
import com.myprj.crawler.enumeration.AttributeType;
import com.myprj.crawler.service.handler.AttributeHandlerSupport;
import com.myprj.crawler.service.handler.HandlerRegister;

/**
 * @author DienNM (DEE)
 */
@Service("objectAttributeHandler")
public class ObjectAttributeHandler extends AttributeHandlerSupport {

    @Override
    public AttributeType getType() {
        return AttributeType.OBJECT;
    }

    @Override
    @PostConstruct
    public void registerHandler() {
        HandlerRegister.register(getType(), this);
    }
    
    @Override
    public Object handle(HtmlDocument document, WorkerItemData workerItem, WorkerItemAttributeData current) {
        List<WorkerItemAttributeData> children = current.getChildren();
        Map<String, Object> values = new HashMap<String, Object>();
        for (WorkerItemAttributeData child : children) {
            Object object = HandlerRegister.getHandler(child.getType()).handle(document, workerItem, child);
            values.put(child.getName(), object);
        }
        return values;
    }

    @Override
    public Object handle(Element element, WorkerItemData workerItem, WorkerItemAttributeData current) {
        List<WorkerItemAttributeData> children = current.getChildren();
        Map<String, Object> values = new HashMap<String, Object>();
        for (WorkerItemAttributeData child : children) {
            Object object = HandlerRegister.getHandler(child.getType()).handle(element, workerItem, child);
            values.put(child.getName(), object);
        }
        return values;
    }

    @Override
    public Object handle(HtmlDocument document, AttributeSelector attributeSelector) {
        return null;
    }

}
