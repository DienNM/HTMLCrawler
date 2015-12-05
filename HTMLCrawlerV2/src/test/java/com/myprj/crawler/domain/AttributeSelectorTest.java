package com.myprj.crawler.domain;

import org.junit.Assert;
import org.junit.Test;

import com.myprj.crawler.domain.config.AttributeSelector;
import com.myprj.crawler.enumeration.SelectorSource;

/**
 * @author DienNM (DEE)
 */

public class AttributeSelectorTest {

    @Test
    public void testAttributeSelector() {
        AttributeSelector attributeSelector = new AttributeSelector("div.select a.link{{href}}", SelectorSource.I);
        Assert.assertEquals(SelectorSource.I, attributeSelector.getSource());
        Assert.assertEquals("div.select a.link", attributeSelector.getSelector());
        Assert.assertEquals("href", attributeSelector.getTargetAttribute());
        Assert.assertEquals("div.select a.link{{href}}", attributeSelector.getText());
    }

}
