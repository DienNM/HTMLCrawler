package com.myprj.crawler.service.rule;

import java.io.Serializable;
import java.util.Map;

/**
 * @author DienNM (DEE)
 */

public class RuleRequest implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private String ruleCode;

    private String functionName;

    private Object evaluateObject;
    
    private String attributeName;
    
    private Map<String, Object> parameters;
    
    public RuleRequest() {
    }

    public String getRuleCode() {
        return ruleCode;
    }

    public void setRuleCode(String ruleCode) {
        this.ruleCode = ruleCode;
    }

    public Object getEvaluateObject() {
        return evaluateObject;
    }

    public void setEvaluateObject(Object evaluateObject) {
        this.evaluateObject = evaluateObject;
    }

    public String getFunctionName() {
        return functionName;
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }

    public String getAttributeName() {
        return attributeName;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

    public Map<String, Object> getParameters() {
        return parameters;
    }

    public void setParameters(Map<String, Object> parameters) {
        this.parameters = parameters;
    }

}