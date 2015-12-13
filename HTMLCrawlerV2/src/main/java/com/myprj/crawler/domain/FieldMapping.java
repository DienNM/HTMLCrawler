package com.myprj.crawler.domain;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

/**
 * @author DienNM (DEE)
 */

public class FieldMapping {

    private String attributeName;

    private Map<String, String> rules = new HashMap<String, String>();

    public FieldMapping() {
    }

    public static FieldMapping parse(String line) {
        FieldMapping fieldMapping = new FieldMapping();
        int index = line.indexOf("=");
        fieldMapping.setAttributeName(line.substring(0, index));
        if (index == line.length() - 1) {
            return fieldMapping;
        }
        String secPart = line.substring(index + 1);
        String[] rules = secPart.split(Pattern.quote("|"));
        for (String ruleText : rules) {
            if (StringUtils.isEmpty(ruleText)) {
                continue;
            }
            String[] ruleInfo = ruleText.split(";");
            fieldMapping.getRules().put(ruleInfo[0], ruleInfo[1]);
        }

        return fieldMapping;
    }

    public static void main(String[] args) {
        System.out.println(FieldMapping.parse("nm="));
    }

    public String getAttributeName() {
        return attributeName;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

    public Map<String, String> getRules() {
        return rules;
    }

    public void setRules(Map<String, String> rules) {
        this.rules = rules;
    }

}
