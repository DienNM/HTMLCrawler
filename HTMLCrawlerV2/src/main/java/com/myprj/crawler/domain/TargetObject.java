package com.myprj.crawler.domain;

import com.myprj.crawler.enumeration.AttributeType;

/**
 * @author DienNM (DEE)
 */

public class TargetObject {
    
    private AttributeType attributeType;
    
    private String attributeName;
    
    public TargetObject() {
    }
    
    

    public AttributeType getAttributeType() {
        return attributeType;
    }

    public void setAttributeType(AttributeType attributeType) {
        this.attributeType = attributeType;
    }

    public String getAttributeName() {
        return attributeName;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }
    
}
