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
import com.myprj.crawler.util.StreamUtil;

/**
 * @author DienNM (DEE)
 */
@Service("listImageAttributeHandler")
public class ListImageAttributeHandler extends AttributeHandlerSupport {

    @Override
    public AttributeType getType() {
        return AttributeType.LIST_IMAGE;
    }

    @Override
    @PostConstruct
    public void registerHandler() {
        HandlerRegister.register(getType(), this);
    }

    @Override
    public Object handle(HtmlDocument document, WorkerItemAttributeData current) {
        AttributeSelector cssSelector = current.getSelector();
        return handle(document, cssSelector);
    }

    @Override
    public Object handle(HtmlDocument document, AttributeSelector cssSelector) {
        Elements elements = document.getDocument().select(cssSelector.getSelector());
        if (elements == null || elements.isEmpty()) {
            return null;
        }
        List<String> values = new ArrayList<String>();
        for (Element element : elements) {
            String link = null;
            if (StringUtils.isEmpty(cssSelector.getTargetAttribute())) {
                link = element.text();
            } else {
                link = element.attr(cssSelector.getTargetAttribute());
            }
            link = returnText(link, cssSelector);
            String text = null;
            if (!StringUtils.isEmpty(link)) {
                try {
                    byte[] bytes = StreamUtil.readImage(link);
                    text = StreamUtil.encodeImage(bytes);
                } catch (Exception e) {
                    text = null;
                }
            }
            if (!StringUtils.isEmpty(text)) {
                values.add(text);
            }
        }
        return values;
    }

}
