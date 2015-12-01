package com.myprj.crawler.domain.worker;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author DienNM (DEE)
 */

public class CssSelector {
    
    private String selector;
    
    private String targetAttribute;
    
    public CssSelector(String text) {
        this.selector = text;
        Pattern patten = Pattern.compile("\\{\\{(\\w+)\\}\\}");
        Matcher matcher = patten.matcher(text);
        while(matcher.find()) {
            int index = matcher.start();
            this.selector = text.substring(0, index);
            this.targetAttribute = matcher.group(1);
        }
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
    
}
