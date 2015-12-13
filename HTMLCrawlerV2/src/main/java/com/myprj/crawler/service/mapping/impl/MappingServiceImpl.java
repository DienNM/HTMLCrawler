package com.myprj.crawler.service.mapping.impl;

import static com.myprj.crawler.service.rule.RuleResponse.RULE_RESULT;
import static java.io.File.separator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myprj.crawler.domain.DataMapping;
import com.myprj.crawler.service.RuleScriptService;
import com.myprj.crawler.service.mapping.MappingService;
import com.myprj.crawler.service.rule.RuleEngine;
import com.myprj.crawler.service.rule.RuleRequest;
import com.myprj.crawler.service.rule.RuleResponse;
import com.myprj.crawler.util.Config;
import com.myprj.crawler.util.FileUtil;
import com.myprj.crawler.util.StreamUtil;

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
    public Map<String, Object> doMappingIndex(Map<String, Object> indexMappings, Map<String, Object> targetValues) {
        Map<String, Object> resultMappingIndex = new HashMap<String, Object>();
        mapValues2Index(indexMappings, targetValues, resultMappingIndex);
        return resultMappingIndex;
    }

    @Override
    public List<DataMapping> loadDataMappings(String site, String category, String item) {
        List<DataMapping> dataMappings = new ArrayList<DataMapping>();

        String mappingDir = FileUtil.getDirPath(Config.get("mapping.dir"));
        String mappingFile = mappingDir + category + separator + item + separator + site + separator + "data.mapping";

        List<String> csvLines = StreamUtil.readCSVFile(mappingFile);
        if (csvLines.isEmpty()) {
            return dataMappings;
        }
        for (String line : csvLines) {
            DataMapping fieldMapping = DataMapping.parse(line);
            if (fieldMapping != null) {
                dataMappings.add(fieldMapping);
            }
        }
        return dataMappings;
    }

    @Override
    public void applyRuleMapping(List<DataMapping> dataMappings, Map<String, Object> values) {
        for (DataMapping dataMapping : dataMappings) {
            Object evaluateObject = values.get(dataMapping.getName());
            RuleResponse response = executeRule(evaluateObject, dataMapping);
            if (response == null) {
                logger.warn("Rule: {} - Function: {} - Attribute: {}: Cannot return response", dataMapping.getRuleCode(),
                        dataMapping.getFunction(), dataMapping.getName());
                continue;
            }
            values.put(dataMapping.getName(), response.get(RULE_RESULT));
        }
    }

    private RuleResponse executeRule(Object evaluateObject, DataMapping att) {
        RuleRequest ruleRequest = new RuleRequest();
        ruleRequest.setEvaluateObject(evaluateObject);
        ruleRequest.setFunctionName(att.getFunction());
        ruleRequest.setRuleCode(att.getRuleCode());
        return ruleEngine.perform(ruleRequest);
    }

    @SuppressWarnings("unchecked")
    private void mapValues2Index(Map<String, Object> indexMapping, Map<String, Object> values,
            Map<String, Object> mapAttributes) {
        for (String key : indexMapping.keySet()) {
            if (!values.containsKey(key)) {
                logger.warn("Key: " + key + " not in mapping value");
            }
            Object indexValue = indexMapping.get(key);
            Object targetValue = values.get(key);

            if (indexValue == null || targetValue == null || StringUtils.isEmpty(targetValue.toString())) {
                continue;
            }
            if (indexValue instanceof String) {
                mapValue2Index(indexValue.toString(), targetValue, mapAttributes);
            } else if (targetValue instanceof Map) {
                mapValues2Index((Map<String, Object>) indexValue, (Map<String, Object>) targetValue, mapAttributes);
            } else if (targetValue instanceof List) {
                List<Object> listIndexValue = (List<Object>) indexValue;
                List<Object> listTargetValue = (List<Object>) targetValue;
                if (listTargetValue.isEmpty() || listIndexValue.isEmpty()) {
                    continue;
                }
                Object sublistIndexValue = listIndexValue.get(0);
                Object sublistTargetValue = listTargetValue.get(0);
                if (!sublistIndexValue.getClass().getName().equals(sublistTargetValue.getClass().getName())) {
                    logger.warn("Key: " + key + " not in mapping value");
                }
                if (sublistIndexValue instanceof Map) {
                    mapValues2Index((Map<String, Object>) sublistIndexValue, (Map<String, Object>) sublistTargetValue,
                            mapAttributes);
                } else {
                    mapValue2Index(sublistIndexValue.toString(), sublistTargetValue, mapAttributes);
                }
            }
        }
    }

    private void mapValue2Index(String field, Object value, Map<String, Object> attributes) {
        if (StringUtils.isEmpty(field) || !field.contains(AT)) {
            logger.warn("Field Name: " + field + " is invalid. Must contain " + AT);
        }
        attributes.put(field.substring(field.indexOf(AT) + 1), value);
    }

}
