package com.myprj.crawler.util;

import java.io.Serializable;

import com.myprj.crawler.domain.config.AttributeSelector;
import com.myprj.crawler.enumeration.SelectorSource;

/**
 * @author DienNM (DEE)
 */

public class ItemAttributeConfig implements Serializable{
    
    private static final long serialVersionUID = 1L;

    private String attributeId;
    
    private SelectorSource source;
    
    private AttributeSelector selector;

    public String getAttributeId() {
        return attributeId;
    }

    public void setAttributeId(String attributeId) {
        this.attributeId = attributeId;
    }

    public SelectorSource getSource() {
        return source;
    }

    public void setSource(SelectorSource source) {
        this.source = source;
    }

    public AttributeSelector getSelector() {
        return selector;
    }

    public void setSelector(AttributeSelector selector) {
        this.selector = selector;
    }
    
}
