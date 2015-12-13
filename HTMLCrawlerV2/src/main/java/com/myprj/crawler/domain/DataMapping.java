package com.myprj.crawler.domain;

import static org.apache.commons.lang3.StringUtils.isEmpty;

import java.util.regex.Pattern;

/**
 * @author DienNM (DEE)
 */

public class DataMapping {

    private String name;

    private String ruleCode;

    private String function;

    public DataMapping() {
    }

    public static DataMapping parse(String line) {
        DataMapping dataMapping = new DataMapping();

        // jobType=R000-001|jobType
        int index = line.indexOf("=");
        dataMapping.setName(line.substring(0, index));
        if (index == line.length() - 1) {
            return dataMapping;
        }
        String[] right = line.substring(index + 1).split(Pattern.quote("|"));
        dataMapping.setRuleCode(right[0]);

        if (right.length == 2) {
            dataMapping.setFunction(right[1]);
        }
        if (isEmpty(dataMapping.getName()) || isEmpty(dataMapping.getRuleCode())) {
            return null;
        }
        return dataMapping;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRuleCode() {
        return ruleCode;
    }

    public void setRuleCode(String ruleCode) {
        this.ruleCode = ruleCode;
    }

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
    }
}
