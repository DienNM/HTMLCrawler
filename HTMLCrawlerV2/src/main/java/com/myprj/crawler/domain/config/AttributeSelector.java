package com.myprj.crawler.domain.config;

import java.security.InvalidParameterException;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.helper.StringUtil;

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
    
    public AttributeSelector(String text) {
        this(text, SelectorSource.I);
    }
    
    // I@CSS||E@CSS||E@CSS
    public static AttributeSelector parseSelectors(String input) {
        if (StringUtil.isBlank(input)) {
            return null;
        }
        String[] texts = input.split(Pattern.quote("|") + Pattern.quote("|"));
        if (texts.length == 0) {
            throw new InvalidParameterException("Cannot parse CSS-Selector value: " + input);
        }
        String e0 = texts[0];
        AttributeSelector targetSelector = parseAttritbuteSelector(e0);

        for (int i = 1; i < texts.length; i++) {
            String ex = texts[i];
            AttributeSelector externalSelector = parseAttritbuteSelector(ex);
            targetSelector.getExternalSelectors().add(externalSelector);
        }

        return targetSelector;
    }

    private static AttributeSelector parseAttritbuteSelector(String input) {
        if(StringUtils.isEmpty(input)) {
            return null;
        }
        if(!input.startsWith("I@") && !input.startsWith("E@")) {
            return new AttributeSelector(input, SelectorSource.I);
        }
        int firstIndexOfAT = input.indexOf("@");
        String source = input.substring(0, firstIndexOfAT);
        String css = input.substring(firstIndexOfAT + 1);
        return new AttributeSelector(css, SelectorSource.fromString(source));
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
