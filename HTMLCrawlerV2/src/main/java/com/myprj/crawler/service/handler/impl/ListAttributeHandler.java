package com.myprj.crawler.service.handler.impl;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.myprj.crawler.domain.HtmlDocument;
import com.myprj.crawler.enumeration.AttributeType;
import com.myprj.crawler.service.handler.AttributeHandlerSupport;
import com.myprj.crawler.util.Serialization;

/**
 * @author DienNM (DEE)
 */

public class ListAttributeHandler extends AttributeHandlerSupport{
    
    @Override
    public AttributeType getType() {
        return AttributeType.LIST;
    }
    
    public ListAttributeHandler() {
        registerHandler();
    }

    @Override
    public Object handle(HtmlDocument document, String cssSelector) {
        List<String> values = new ArrayList<String>();
        Elements elements = document.getDocument().select(cssSelector);
        if(elements == null || elements.isEmpty()) {
            return Serialization.serialize(values);
        }
        for(Element element : elements) {
            values.add(element.text());
        }
        return values;
    }

}
