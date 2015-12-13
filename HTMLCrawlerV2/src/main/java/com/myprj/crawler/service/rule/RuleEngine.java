package com.myprj.crawler.service.rule;

import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.jruby.embed.EmbedEvalUnit;
import org.jruby.embed.LocalContextScope;
import org.jruby.embed.ScriptingContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myprj.crawler.domain.RuleScriptData;
import com.myprj.crawler.repository.RuleScriptRepository;

/**
 * @author DienNM (DEE)
 */

@Service
public class RuleEngine {

    private Logger logger = LoggerFactory.getLogger(RuleEngine.class);

    @Autowired
    private RuleScriptRepository ruleScriptRepository;

    private Map<String, EmbedEvalUnit> scriptCache;

    private ScriptingContainer rubyContainer;

    @PostConstruct
    public void init() {
        this.rubyContainer = new ScriptingContainer(LocalContextScope.THREADSAFE);
        this.scriptCache = new HashMap<String, EmbedEvalUnit>();
    }
    
    public void clearCache() {
        if(scriptCache != null) {
            scriptCache.clear();
        }
    }

    public RuleResponse performRule(RuleRequest input, RuleScriptData ruleScript) {
        if (input.getEvaluateObject() == null) {
            throw new InvalidParameterException("Evaluate object is missing");
        }
        try {
            RuleResponse response = perform(input, ruleScript);
            return response;
        } catch (Exception e) {
            logger.error("Script [{}] is failed: Error: {}", ruleScript.getCode(), e);
            return null;
        }
    }

    public RuleResponse perform(RuleRequest input, RuleScriptData ruleScript) {
        logger.debug("Running ruby script: " + ruleScript.getCode());
        EmbedEvalUnit script = receiveScript(ruleScript.getCode(), ruleScript.getScript());
        RuleResponse response = null;
        if (script == null) {
            logger.warn("Cannot parse rule script: " + ruleScript.getCode());
            return null;
        }
        response = (RuleResponse) this.rubyContainer.callMethod(script.run(), "perform", input);
        if (response == null || response.getRuleScripts().isEmpty()) {
            logger.warn("Rule script " + ruleScript.getCode() + " cannot return response");
            return null;
        }
        return response;
    }

    private synchronized EmbedEvalUnit receiveScript(String scriptCode, String script) {
        EmbedEvalUnit embedEvalUnit = this.scriptCache.get(scriptCode);
        if (embedEvalUnit == null) {
            embedEvalUnit = this.rubyContainer.parse(script);
            this.scriptCache.put(scriptCode, embedEvalUnit);
        }
        return embedEvalUnit;
    }
}