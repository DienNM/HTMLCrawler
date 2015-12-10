package com.myprj.crawler.service.handler;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.myprj.crawler.domain.config.AttributeSelector;

/**
 * @author DienNM (DEE)
 */

public abstract class AttributeHandlerSupport implements AttributeHandler {
    
    protected Logger logger = LoggerFactory.getLogger(AttributeHandlerSupport.class);
    
    protected String returnText(Object object, AttributeSelector attributeSelector) {
        String text = returnNormalizeString(object);
        if(StringUtils.isEmpty(attributeSelector.getExpression())) {
            return text;
        }
        try {
            Pattern pattern = Pattern.compile(attributeSelector.getExpression());
            Matcher matcher = pattern.matcher(text);
            while(matcher.find()) {
                return matcher.group();
            }
        } catch(Exception  e) {
            logger.warn("Error during evaluate expression: " + attributeSelector.getExpression());
        }
        return text;
        
    }
    
    protected String returnNormalizeString(Object object) {
        if(object == null) {
            return "";
        }
        return object.toString().trim();
    }
    
}
