package com.myprj.crawler.service.rule;

import java.io.Serializable;
import java.util.List;

import com.myprj.crawler.domain.RuleScriptData;

/**
 * @author DienNM (DEE)
 */

public class RuleRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<RuleScriptData> ruleScripts;
    
    private Object targetObject;
    
    public RuleRequest() {
    }

    public List<RuleScriptData> getRuleScripts() {
        return ruleScripts;
    }

    public void setRuleScripts(List<RuleScriptData> ruleScripts) {
        this.ruleScripts = ruleScripts;
    }

    public Object getTargetObject() {
        return targetObject;
    }

    public void setTargetObject(Object targetObject) {
        this.targetObject = targetObject;
    }
}