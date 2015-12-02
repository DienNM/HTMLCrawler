package com.myprj.crawler.domain.config;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.myprj.crawler.enumeration.SelectorSource;

/**
 * @author DienNM (DEE)
 */

public class AttributeSelector {

    private SelectorSource source = SelectorSource.I;
    
    private String url;
    
    private String text;

    private String selector;

    private String targetAttribute;
    
    public AttributeSelector(String text, SelectorSource source) {
        this.text = text;
        this.source = source;
        this.selector = text;
        Pattern patten = Pattern.compile("\\{\\{(\\w+)\\}\\}");
        Matcher matcher = patten.matcher(text);
        while (matcher.find()) {
            int index = matcher.start();
            this.selector = text.substring(0, index);
            this.targetAttribute = matcher.group(1);
        }
    }

    public AttributeSelector(String text) {
        this(text, SelectorSource.I);
    }

    public String getSelector() {
        return selector;
    }

    public void setSelector(String selector) {
        this.selector = selector;
    }

    public String getTargetAttribute() {
        return targetAttribute;
    }

    public void setTargetAttribute(String targetAttribute) {
        this.targetAttribute = targetAttribute;
    }

    public SelectorSource getSource() {
        return source;
    }

    public void setSource(SelectorSource source) {
        this.source = source;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
