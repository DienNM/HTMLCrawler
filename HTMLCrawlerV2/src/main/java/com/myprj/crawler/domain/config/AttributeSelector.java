package com.myprj.crawler.domain.config;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.myprj.crawler.enumeration.SelectorSource;

/**
 * @author DienNM (DEE)
 */

public class AttributeSelector {
    
    private static Pattern patten = Pattern.compile("\\{\\{(\\w+)\\}\\}");
    private static Pattern patten1 = Pattern.compile("/(.*)/");

    private SelectorSource source = SelectorSource.I;
    
    private String url;
    
    private String text;

    private String selector;

    private String targetAttribute;
    
    private String expression;
    
    private List<AttributeSelector> externalSelectors = new LinkedList<AttributeSelector>();
    
    // div.test a{{a}}
    public AttributeSelector(String text, SelectorSource source) {
        this.text = text;
        this.source = source;
        this.selector = text;
        Matcher matcher = patten.matcher(text);
        boolean hasAtt = false;
        while (matcher.find()) {
            int index = matcher.start();
            this.selector = text.substring(0, index);
            this.targetAttribute = matcher.group(1);
            hasAtt = true;
        }
        
        matcher = patten1.matcher(text);
        while (matcher.find()) {
            if(!hasAtt) {
                int index = matcher.start();
                this.selector = text.substring(0, index);
            }
            this.expression = matcher.group(1);
        }
    }
    
    public static void main(String[] args) {
        AttributeSelector attributeSelector = new AttributeSelector("div.test a{{a}}/\\d{8}/");
        System.out.println(attributeSelector.getSelector());
        System.out.println(attributeSelector.getTargetAttribute());
        System.out.println(attributeSelector.getExpression());
    }

    public AttributeSelector(String text) {
        this(text, SelectorSource.I);
    }
    
    @Override
    public String toString() {
        return String.format("[S=%s], CSS=%s {{%s}}, Externals: %s", source, selector, targetAttribute, externalSelectors.toString());
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

    public List<AttributeSelector> getExternalSelectors() {
        return externalSelectors;
    }

    public void setExternalSelectors(List<AttributeSelector> externalSelectors) {
        this.externalSelectors = externalSelectors;
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

}
