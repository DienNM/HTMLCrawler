package com.myprj.crawler.service.rule;

import java.io.Serializable;
import java.util.Map;

/**
 * @author DienNM (DEE)
 */

public class RuleRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    private String attribute;

    private Object evaluateObject;

    private Map<String, Object> parameters;

    public RuleRequest() {
    }

    public Object getEvaluateObject() {
        return evaluateObject;
    }

    public void setEvaluateObject(Object evaluateObject) {
        this.evaluateObject = evaluateObject;
    }

    public Map<String, Object> getParameters() {
        return parameters;
    }

    public void setParameters(Map<String, Object> parameters) {
        this.parameters = parameters;
    }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }
}