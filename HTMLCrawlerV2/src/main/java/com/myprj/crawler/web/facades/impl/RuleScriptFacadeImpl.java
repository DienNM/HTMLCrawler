package com.myprj.crawler.web.facades.impl;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.myprj.crawler.domain.RuleScriptData;
import com.myprj.crawler.util.Config;
import com.myprj.crawler.util.FileUtil;
import com.myprj.crawler.util.StreamUtil;
import com.myprj.crawler.web.facades.RuleScriptFacade;

/**
 * @author DienNM (DEE)
 */
@Service
public class RuleScriptFacadeImpl implements RuleScriptFacade {

    private Logger logger = LoggerFactory.getLogger(RuleScriptFacadeImpl.class);

    @Override
    public List<RuleScriptData> loadScriptsFromSource(InputStream inputStream) {
        List<RuleScriptData> ruleScripts = new ArrayList<RuleScriptData>();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(inputStream));
            String line = null;
            while ((line = br.readLine()) != null) {
                if (line == null || line.trim().isEmpty() || line.startsWith("#")) {
                    continue;
                }
                RuleScriptData script = parseRuleScript(line);
                ruleScripts.add(script);
            }
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            logger.debug("{}", ex);
            return new ArrayList<RuleScriptData>();
        } finally {
            IOUtils.closeQuietly(br);
            IOUtils.closeQuietly(inputStream);
        }
        return ruleScripts;
    }

    private RuleScriptData parseRuleScript(String line) {
        String[] elements = line.split(Pattern.quote("|"));
        RuleScriptData ruleScript = new RuleScriptData();
        ruleScript.setCode(elements[0]);
        ruleScript.setEnabled(Boolean.valueOf(elements[1]));
        ruleScript.setFile(elements[2]);
        
        String root = FileUtil.getDirPath(Config.get("scripts.ruby.dir"));
        
        String content = StreamUtil.readFile2String(root + ruleScript.getFile());
        if (StringUtils.isEmpty(content)) {
            throw new InvalidParameterException("Script is empty. Line: " + line);
        }
        if(elements.length > 3) {
            ruleScript.setParameters(elements[3]);
        }
        ruleScript.setScript(content);
        return ruleScript;
    }

}
