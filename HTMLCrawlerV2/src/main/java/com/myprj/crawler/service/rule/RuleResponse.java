package com.myprj.crawler.service.rule;

import java.util.HashMap;
import java.util.List;

/**
 * @author DienNM (DEE)
 */

public class RuleResponse extends HashMap<String, Object> {

    private static final long serialVersionUID = 1L;

    public static final String RULE_RESULTS = "results";

    public void setRuleScripts(List<RuleResult> results) {
        super.put(RULE_RESULTS, results);
    }

    @SuppressWarnings("unchecked")
    public List<RuleResult> getRuleScripts() {
        return (List<RuleResult>) super.get(RULE_RESULTS);
    }
}
