package com.myprj.crawler.domain;

import static com.myprj.crawler.util.CommonUtil.isTextEmpty;

import java.util.Map;
import java.util.regex.Pattern;

import com.myprj.crawler.util.Serialization;

/**
 * @author DienNM (DEE)
 */

public class DataMapping {

    private String name;

    private String ruleCode;

    private String function;

    private Map<String, Object> parameters;

    public DataMapping() {
    }

    @SuppressWarnings("unchecked")
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
        dataMapping.setFunction(right[1]);

        if (right.length > 2) {
            dataMapping.setParameters(Serialization.deserialize(right[2], Map.class));
        }

        if (isTextEmpty(dataMapping.getName()) || isTextEmpty(dataMapping.getRuleCode())
                || isTextEmpty(dataMapping.getFunction())) {
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

    public Map<String, Object> getParameters() {
        return parameters;
    }

    public void setParameters(Map<String, Object> parameters) {
        this.parameters = parameters;
    }
}
