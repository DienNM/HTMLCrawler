package com.myprj.crawler.util;

import java.security.InvalidParameterException;
import java.util.regex.Pattern;

import org.jsoup.helper.StringUtil;

import com.myprj.crawler.domain.config.AttributeSelector;
import com.myprj.crawler.enumeration.SelectorSource;

/**
 * @author DienNM (DEE)
 */

public class AttributeSelectorUtil {
    
    // I@CSS||E@CSS||E@CSS
    public static AttributeSelector parseAttritbuteSelectors(String input) {
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

    public static AttributeSelector parseAttritbuteSelector(String input) {
        int firstIndexOfAT = input.indexOf("@");
        String source = input.substring(0, firstIndexOfAT);
        String css = input.substring(firstIndexOfAT + 1);
        return new AttributeSelector(css, SelectorSource.fromString(source));
    }
}
