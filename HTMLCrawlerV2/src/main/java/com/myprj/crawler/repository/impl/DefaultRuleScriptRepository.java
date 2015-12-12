package com.myprj.crawler.repository.impl;

import org.springframework.stereotype.Repository;

import com.myprj.crawler.model.RuleScriptModel;
import com.myprj.crawler.repository.RuleScriptRepository;

/**
 * @author DienNM (DEE)
 */
@Repository
public class DefaultRuleScriptRepository extends DefaultGenericDao<RuleScriptModel, String> implements
        RuleScriptRepository {

    @Override
    protected Class<RuleScriptModel> getTargetClass() {
        return RuleScriptModel.class;
    }

}
