package com.myprj.crawler.service.handler.impl;

import java.util.HashMap;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
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
import com.myprj.crawler.util.Config;
import com.myprj.crawler.util.FileUtil;
import com.myprj.crawler.util.IdGenerator;
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
    public Object handle(HtmlDocument document, WorkerItemData workerItem, WorkerItemAttributeData current) {
        AttributeSelector cssSelector = current.getSelector();
        if (isEmptySelector(cssSelector)) {
            return null;
        }
        HtmlDocument finalDocument = getFinalDocument(document, workerItem, current);
        Elements elements = finalDocument.getDocument().select(cssSelector.getSelector());
        if (elements == null || elements.isEmpty()) {
            return null;
        }
        return getImage(elements, workerItem, cssSelector);
    }

    @Override
    public Object handle(Element element, WorkerItemData workerItem, WorkerItemAttributeData current) {
        AttributeSelector cssSelector = current.getSelector();
        if (isEmptySelector(cssSelector)) {
            return returnNormalizeString(element.text());
        }
        Elements elements = element.select(cssSelector.getSelector());
        if (elements == null || elements.isEmpty()) {
            return null;
        }
        return getImage(elements, workerItem, cssSelector);
    }

    private Object getImage(Elements elements, WorkerItemData workerItem, AttributeSelector cssSelector) {
        if (StringUtils.isEmpty(cssSelector.getTargetAttribute())) {
            cssSelector.setTargetAttribute("src");
        }
        String link = pickupString(elements, cssSelector);
        if (StringUtils.isEmpty(link)) {
            return null;
        }

        String root = Config.get("crawler.images.dir");
        String subDir = WorkerItemData.getImagesDir(workerItem);

        String storedDir = FileUtil.makeDir(root, subDir);
        if (StringUtils.isEmpty(storedDir)) {
            return null;
        }
        String fileName = IdGenerator.generateIdByTime();
        String localImageName = storedDir + fileName;
        try {
            StreamUtil.writeImage(link, localImageName);
        } catch (Exception e) {
            localImageName = null;
        }
        return FileUtil.getDirPath(subDir) + fileName;
    }

    @Override
    public Object handle(HtmlDocument document, AttributeSelector cssSelector) {
        if (isEmptySelector(cssSelector)) {
            return null;
        }
        HtmlDocument finalDocument = getFinalDocument(cssSelector, document, new HashMap<String, HtmlDocument>());
        Elements elements = finalDocument.getDocument().select(cssSelector.getSelector());
        if (elements == null || elements.isEmpty()) {
            return null;
        }
        if (StringUtils.isEmpty(cssSelector.getTargetAttribute())) {
            cssSelector.setTargetAttribute("src");
        }
        String link = pickupString(elements, cssSelector);
        if (StringUtils.isEmpty(link)) {
            return null;
        }

        try {
            byte[] bytes = StreamUtil.readImage(link);
            return StreamUtil.encodeImage(bytes);
        } catch (Exception e) {
            return null;
        }
    }

}
