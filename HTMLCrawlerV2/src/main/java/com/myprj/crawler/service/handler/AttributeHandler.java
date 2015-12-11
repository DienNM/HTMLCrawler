package com.myprj.crawler.service.handler;

import org.jsoup.nodes.Element;

import com.myprj.crawler.domain.HtmlDocument;
import com.myprj.crawler.domain.config.AttributeSelector;
import com.myprj.crawler.domain.config.WorkerItemAttributeData;
import com.myprj.crawler.domain.crawl.WorkerItemData;
import com.myprj.crawler.enumeration.AttributeType;

/**
 * @author DienNM (DEE)
 */

public interface AttributeHandler {

    void registerHandler();

    AttributeType getType();

    Object handle(HtmlDocument document, WorkerItemData workerItem, WorkerItemAttributeData current);

    Object handle(Element element, WorkerItemData workerItem, WorkerItemAttributeData current);

    Object handle(HtmlDocument document, AttributeSelector attributeSelector);

}
