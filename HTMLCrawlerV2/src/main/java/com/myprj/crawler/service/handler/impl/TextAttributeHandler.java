package com.myprj.crawler.service.handler.impl;

import java.util.HashMap;

import javax.annotation.PostConstruct;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
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
@Service("textAttributeHandler")
public class TextAttributeHandler extends AttributeHandlerSupport {

    private final static String EMPTY = "";

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
    public Object handle(HtmlDocument document, WorkerItemData workerItem, WorkerItemAttributeData current) {
        AttributeSelector cssSelector = current.getSelector();
        if (isEmptySelector(cssSelector)) {
            return EMPTY;
        }
        HtmlDocument finalDocument = getFinalDocument(document, workerItem, current);
        Elements elements = finalDocument.getDocument().select(cssSelector.getSelector());
        return pickupString(elements, cssSelector);
    }

    @Override
    public Object handle(Element element, WorkerItemData workerItem, WorkerItemAttributeData current) {
        AttributeSelector cssSelector = current.getSelector();
        if (isEmptySelector(cssSelector)) {
            return returnNormalizeString(element.text());
        }
        Elements elements = element.select(cssSelector.getSelector());
        return pickupString(elements, cssSelector);
    }

    @Override
    public Object handle(HtmlDocument document, AttributeSelector cssSelector) {
        if (isEmptySelector(cssSelector)) {
            return EMPTY;
        }
        HtmlDocument finalDocument = getFinalDocument(cssSelector, document, new HashMap<String, HtmlDocument>());
        Elements elements = finalDocument.getDocument().select(cssSelector.getSelector());
        return pickupString(elements, cssSelector);
    }

}
