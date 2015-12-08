package com.myprj.crawler.service.handler.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
@Service("listObjectAttributeHandler")
public class ListObjectAttributeHandler extends AttributeHandlerSupport {

    @Override
    public AttributeType getType() {
        return AttributeType.LIST_OBJECT;
    }

    @Override
    @PostConstruct
    public void registerHandler() {
        HandlerRegister.register(getType(), this);
    }

    // [{ key : "", value : ""}]
    @Override
    public Object handle(HtmlDocument document, WorkerItemAttributeData current) {
        AttributeSelector cssSelector = current.getSelector();

        List<Map<String, Object>> results = new ArrayList<Map<String, Object>>();
        Elements elements = document.getDocument().select(cssSelector.getSelector());
        if (elements == null || elements.isEmpty()) {
            return results;
        }
        WorkerItemAttributeData elementObject = current.getChildren().get(0);
        List<WorkerItemAttributeData> actualObjects = elementObject.getChildren();
        for (Element element : elements) {
            Map<String, Object> map = new HashMap<String, Object>();
            for (WorkerItemAttributeData obj : actualObjects) {
                AttributeSelector subSelector = obj.getSelector();
                if (subSelector == null) {
                    continue;
                }
                Object rs = null;
                Elements elementChildren = element.select(subSelector.getSelector());
                if (elementChildren == null || elementChildren.isEmpty()) {
                    continue;
                }
                if (StringUtils.isEmpty(subSelector.getTargetAttribute())) {
                    rs = elementChildren.text();
                } else {
                    rs = elementChildren.attr(subSelector.getTargetAttribute());
                }
                String text = returnNormalizeString(rs);
                if (!StringUtils.isEmpty(text)) {
                    map.put(obj.getName(), rs);
                }
            }
            if (!map.isEmpty()) {
                results.add(map);
            }
        }
        return results;
    }

}
