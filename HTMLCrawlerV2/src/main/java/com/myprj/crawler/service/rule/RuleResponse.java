package com.myprj.crawler.service.rule;

import java.util.HashMap;

/**
 * @author DienNM (DEE)
 */

public class RuleResponse extends HashMap<String, Object> {

    private static final long serialVersionUID = 1L;

    public static final String RULE_RESULT = "result";

    public void setRuleScript(Object object) {
        put(RULE_RESULT, object);
    }

    public Object getRuleScript() {
        return get(RULE_RESULT);
    }
}
