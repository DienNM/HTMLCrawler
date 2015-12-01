package com.myprj.crawler.service.handler.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import com.myprj.crawler.domain.HtmlDocument;
import com.myprj.crawler.domain.worker.CssSelector;
import com.myprj.crawler.enumeration.AttributeType;
import com.myprj.crawler.service.handler.AttributeHandlerSupport;
import com.myprj.crawler.service.handler.HandlerRegister;
import com.myprj.crawler.util.Serialization;

/**
 * @author DienNM (DEE)
 */
@Service("listAttributeHandler")
public class ListAttributeHandler extends AttributeHandlerSupport{
    
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
    public Object handle(HtmlDocument document, CssSelector cssSelector) {
        List<String> values = new ArrayList<String>();
        Elements elements = document.getDocument().select(cssSelector.getSelector());
        if(elements == null || elements.isEmpty()) {
            return Serialization.serialize(values);
        }
        for(Element element : elements) {
            if(StringUtils.isEmpty(cssSelector.getTargetAttribute())) {
                values.add(element.text());
            } else {
                values.add(element.attr(cssSelector.getTargetAttribute()));
            }
        }
        return values;
    }

}
