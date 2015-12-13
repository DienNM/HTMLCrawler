package com.myprj.crawler.service;

import java.util.List;

import com.myprj.crawler.domain.RuleScriptData;

/**
 * @author DienNM (DEE)
 */

public interface RuleScriptService {
    
    RuleScriptData get(String code);
    
    List<RuleScriptData> get(List<String> codes);
    
    List<RuleScriptData> getAll();
    
    void saveOrUpdate(RuleScriptData ruleScriptData);
    
    void saveOrUpdate(List<RuleScriptData> ruleScriptDatas);
    
}
