package com.myprj.crawler.service.rule;

import java.io.Serializable;

/**
 * @author DienNM (DEE)
 */

public class RuleRequest implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private String ruleCode;

    private String functionName;

    private Object evaluateObject;
    
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

}