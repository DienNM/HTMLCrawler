package com.myprj.crawler.service.mapping.impl;

import static com.myprj.crawler.util.CommonUtil.isCollectionEmpty;
import static com.myprj.crawler.util.CommonUtil.isObjectTextEmpty;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myprj.crawler.domain.DataMapping;
import com.myprj.crawler.service.RuleScriptService;
import com.myprj.crawler.service.mapping.Mapping;
import com.myprj.crawler.service.mapping.MappingService;
import com.myprj.crawler.service.rule.RuleEngine;
import com.myprj.crawler.service.rule.RuleRequest;

/**
 * @author DienNM (DEE)
 */
@Service
public class MappingServiceImpl implements MappingService {

    private Logger logger = LoggerFactory.getLogger(MappingServiceImpl.class);

    private final static String AT = "@".intern();

    @Autowired
    private RuleEngine ruleEngine;

    @Autowired
    private RuleScriptService ruleScriptService;

    @Override
    public Mapping doMappingIndex(Mapping indexes, Mapping values) {
        Mapping result = new Mapping();
        mapValues2Index(indexes, values, result);
        return result;
    }

    @Override
    public void applyRuleMapping(List<DataMapping> ruleMappings, Map<String, Object> values) {
        for (DataMapping dataMapping : ruleMappings) {
            Object evaluateObject = values.get(dataMapping.getName());
            Object response = executeRule(evaluateObject, dataMapping);
            if (response == null) {
                logger.debug("Rule: {} - Function: {} - Attribute: {}: Cannot return response",
                        dataMapping.getRuleCode(), dataMapping.getFunction(), dataMapping.getName());
                continue;
            }
            values.put(dataMapping.getName(), response);
        }
    }

    private Object executeRule(Object evaluateObject, DataMapping att) {
        RuleRequest ruleRequest = new RuleRequest();
        ruleRequest.setEvaluateObject(evaluateObject);
        ruleRequest.setFunctionName(att.getFunction());
        ruleRequest.setRuleCode(att.getRuleCode());
        ruleRequest.setAttributeName(att.getName());
        return ruleEngine.perform(ruleRequest);
    }

    @SuppressWarnings("unchecked")
    private void mapValues2Index(Mapping indexes, Mapping values, Mapping results) {
        for (String key : indexes.keySet()) {
            if (!values.containsKey(key)) {
                logger.warn("Key: " + key + " not in mapping value");
            }
            Object indexValue = indexes.get(key);
            Object targetValue = values.get(key);

            if (isObjectTextEmpty(indexValue) || isObjectTextEmpty(targetValue)) {
                continue;
            }
            
            if (indexValue instanceof String) {
                mapValue2Index(indexValue.toString(), targetValue, results);
            } else if (targetValue instanceof Map) {
                mapValues2Index(new Mapping(indexValue), new Mapping(targetValue), results);
            } else if (targetValue instanceof List) {
                List<Object> listIndexValue = (List<Object>) indexValue;
                List<Object> listTargetValue = (List<Object>) targetValue;
                if (isCollectionEmpty(listTargetValue) || isCollectionEmpty(listIndexValue)) {
                    continue;
                }
                Object sublistIndexValue = listIndexValue.get(0);
                Object sublistTargetValue = listTargetValue.get(0);
                if (!sublistIndexValue.getClass().getName().equals(sublistTargetValue.getClass().getName())) {
                    logger.warn("Key: " + key + " not in mapping value");
                }
                if (sublistIndexValue instanceof Map) {
                    mapValues2Index(new Mapping(sublistIndexValue), new Mapping(sublistTargetValue), results);
                } else {
                    mapValue2Index(sublistIndexValue.toString(), sublistTargetValue, results);
                }
            }
        }
    }

    private void mapValue2Index(String field, Object value, Map<String, Object> attributes) {
        if (StringUtils.isEmpty(field) || !field.contains(AT)) {
            logger.debug("Field Name: " + field + " is invalid. Must contain " + AT);
            return;
        }
        attributes.put(field.substring(field.indexOf(AT) + 1), value);
    }

}
