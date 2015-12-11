package com.myprj.crawler.service.handler;

import static com.myprj.crawler.util.AttributeSelectorUtil.parseAttritbuteSelectors;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.myprj.crawler.domain.HtmlDocument;
import com.myprj.crawler.domain.config.AttributeSelector;
import com.myprj.crawler.domain.config.WorkerItemAttributeData;
import com.myprj.crawler.domain.crawl.WorkerItemData;
import com.myprj.crawler.service.handler.impl.TextAttributeHandler;
import com.myprj.crawler.util.HtmlDownloader;

/**
 * @author DienNM (DEE)
 */

public abstract class AttributeHandlerSupport implements AttributeHandler {

    protected Logger logger = LoggerFactory.getLogger(AttributeHandlerSupport.class);

    private final static String EMPTY = "";
    
    protected HtmlDocument getFinalDocument(HtmlDocument document, WorkerItemData workerItem, WorkerItemAttributeData current) {
        HtmlDocument finalDocument = getFinalDocument(current.getSelector(), document, workerItem.getHtmlCaches());
        return finalDocument;
    }

    protected HtmlDocument getFinalDocument(AttributeSelector selector, HtmlDocument htmlDocument, Map<String, HtmlDocument> htmlCache) {
        if(selector == null) {
            return htmlDocument;
        }
        List<AttributeSelector> attributeSelectors = selector.getExternalSelectors();
        String currentURL = htmlDocument.getUrl();
        htmlCache.put(currentURL, htmlDocument);
        if(attributeSelectors.isEmpty()) {
            return htmlDocument;
        }
        
        for(AttributeSelector cssSelector : attributeSelectors) {
            HtmlDocument current = new HtmlDocument();
            String link = getLink(htmlCache.get(currentURL).getDocument(), cssSelector);
            if(StringUtils.isEmpty(link)) {
                break;
            }
            current = htmlCache.get(link);
            Document nextDocument = null;
            if(current == null) {
                nextDocument = HtmlDownloader.download(link);
                current = new HtmlDocument(nextDocument);
            } else {
                nextDocument = current.getDocument();
            }
            if(nextDocument != null) {
                htmlCache.put(link, current);
                currentURL = link;
            }
        }
        return htmlCache.get(currentURL);
    }
    
    public static void main(String[] args) {
        TextAttributeHandler textAttributeHandler = new TextAttributeHandler();
        Document document = HtmlDownloader.download("https://vieclam24h.vn/kd-bat-dong-san/chuyen-vien-tu-van-cskh-luong-8-15-trieu-c81p1id1968479.html");
        
        String s2 = "E@#cols-right > div.content_cols-right.pt_16.pl_24.pb_24 > div.box_chi_tiet_cong_viec.bg_white.mt16.box_shadow > div:nth-child(1) > div > p > a{{href}}";
        String s1 = "I@#cols-right > div.content_cols-right.pt_16.pl_24.pb_24 > div.box_chi_tiet_nha_tuyen_dung.bg_white.mt16.box_shadow > div > div.col-xs-9 > div.mb_30.mt_10 > p:nth-child(1) > i";
        
        AttributeSelector selector = parseAttritbuteSelectors(s1 + "||" + s2);
        
        Map<String, HtmlDocument> caches = new HashMap<String, HtmlDocument>();
        HtmlDocument htmlDocument = textAttributeHandler.getFinalDocument(selector, new HtmlDocument(document), caches);
        
        System.out.println("Cache: " + caches.keySet());
        System.out.println(htmlDocument.getUrl());
    }
    
    protected String getLink(Document document, AttributeSelector selector) {
        Elements elements = document.select(selector.getSelector());
        return pickupString(elements, selector);
    }

    protected boolean isEmptySelector(AttributeSelector cssSelector) {
        return cssSelector == null || StringUtils.isEmpty(cssSelector.getSelector());
    }

    protected String pickupString(Elements elements, AttributeSelector cssSelector) {
        if (elements == null || elements.isEmpty()) {
            return EMPTY;
        }
        return pickupString(elements.first(), cssSelector);
    }

    protected String pickupString(Element element, AttributeSelector cssSelector) {
        String text = null;
        if (StringUtils.isEmpty(cssSelector.getTargetAttribute())) {
            text = element.text();
        } else {
            text = element.attr(cssSelector.getTargetAttribute());
        }
        return returnText(text, cssSelector);
    }

    protected String returnText(Object object, AttributeSelector attributeSelector) {
        String text = returnNormalizeString(object);
        if (StringUtils.isEmpty(attributeSelector.getExpression())) {
            return text;
        }
        try {
            Pattern pattern = Pattern.compile(attributeSelector.getExpression());
            Matcher matcher = pattern.matcher(text);
            while (matcher.find()) {
                return matcher.group();
            }
        } catch (Exception e) {
            logger.warn("Error during evaluate expression: " + attributeSelector.getExpression());
        }
        return text;

    }

    protected String returnNormalizeString(Object object) {
        if (object == null) {
            return "";
        }
        return object.toString().trim();
    }

}
