package com.myprj.crawler.service.rule;

import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myprj.crawler.domain.RuleScriptData;
import com.myprj.crawler.service.RuleScriptService;

/**
 * @author DienNM (DEE)
 */

@Service
public class RuleEngine {

    private Logger logger = LoggerFactory.getLogger(RuleEngine.class);

    @Autowired
    private RuleScriptService reRuleScriptService;

    ScriptEngineManager manager = new ScriptEngineManager();

    ScriptEngine engine = manager.getEngineByName("JavaScript");

    private Map<String, Invocable> scriptCache;

    @PostConstruct
    public void init() {
        this.scriptCache = new HashMap<String, Invocable>();
    }

    public void clearCache() {
        if (scriptCache != null) {
            scriptCache.clear();
        }
    }

    public RuleResponse perform(RuleRequest request) {
        logger.debug("Running script: " + request.getRuleCode());
        RuleResponse response = null;
        try {
            Invocable invocable = receiveScript(request.getRuleCode());
            response = (RuleResponse) invocable.invokeFunction(request.getFunctionName(), request.getEvaluateObject());
        } catch (Exception e) {
            logger.error(e.getMessage());
            logger.debug("{}", e);
        }
        return response;
    }

    private synchronized Invocable receiveScript(String scriptCode) throws Exception {
        Invocable invocable = this.scriptCache.get(scriptCode);
        if (invocable == null) {
            RuleScriptData ruleScript = reRuleScriptService.get(scriptCode);
            if (ruleScript == null) {
                throw new InvalidParameterException("Rule Script: " + scriptCode + " not found");
            }
            if (!ruleScript.isEnabled()) {
                throw new InvalidParameterException("Rule Script: " + scriptCode + " is disabled");
            }
            engine.eval(ruleScript.getScript());
            invocable = (Invocable) engine;
            scriptCache.put(scriptCode, invocable);
        }
        return invocable;
    }
}