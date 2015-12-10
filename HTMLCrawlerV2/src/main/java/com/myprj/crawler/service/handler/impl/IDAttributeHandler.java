package com.myprj.crawler.service.handler.impl;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import com.myprj.crawler.domain.HtmlDocument;
import com.myprj.crawler.domain.config.AttributeSelector;
import com.myprj.crawler.domain.config.WorkerItemAttributeData;
import com.myprj.crawler.enumeration.AttributeType;
import com.myprj.crawler.service.handler.AttributeHandlerSupport;
import com.myprj.crawler.service.handler.HandlerRegister;
import com.myprj.crawler.util.HtmlDownloader;

/**
 * @author DienNM (DEE)
 */
@Service("idAttributeHandler")
public class IDAttributeHandler extends AttributeHandlerSupport {

    @Override
    public AttributeType getType() {
        return AttributeType.ID;
    }

    @Override
    @PostConstruct
    public void registerHandler() {
        HandlerRegister.register(getType(), this);
    }

    @Override
    public Object handle(HtmlDocument document, WorkerItemAttributeData current) {
        
        AttributeSelector cssSelector  = current.getSelector();
        
        current.getSiteKey();
        
        Elements elements = document.getDocument().select(cssSelector.getSelector());
        if (elements == null || elements.isEmpty()) {
            return null;
        }
        String text = null;
        if (StringUtils.isEmpty(cssSelector.getTargetAttribute())) {
            text = elements.first().text();
        } else {
            text = elements.first().attr(cssSelector.getTargetAttribute());
        }
        System.out.println(text);
        return returnNormalizeString(text);
    }
    
    public static void main(String[] args) {
        IDAttributeHandler attributeHandler = new IDAttributeHandler();
        
        Document document = HtmlDownloader.download("http://m.chotot.vn/quan-tan-binh/mua-ban-xe-may/airblade-fi-dau-nho-19989366.htm");
        HtmlDocument htmlDocument = new HtmlDocument(document);
        
        WorkerItemAttributeData current = new WorkerItemAttributeData();
        AttributeSelector attributeSelector = new AttributeSelector("head > title");
        current.setSelector(attributeSelector);
        
        attributeHandler.handle(htmlDocument, current);
        
    }

}
