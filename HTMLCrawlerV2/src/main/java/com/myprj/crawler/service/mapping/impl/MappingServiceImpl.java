package com.myprj.crawler.service.mapping.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.myprj.crawler.service.mapping.MappingService;
import com.myprj.crawler.service.mapping.Pair;

/**
 * @author DienNM (DEE)
 */
@Service
public class MappingServiceImpl implements MappingService {

    private Logger logger = LoggerFactory.getLogger(MappingServiceImpl.class);
    
    private final static String AT = "@".intern();
    private final static String A_AT = "A@".intern();
    private final static String C_AT = "C@".intern();
    private final static String PILE = Pattern.quote("|").intern();
    

    @Override
    public Pair<Map<String, Object>, Map<String, Object>> doMappingIndex(Map<String, Object> indexMappings,
            Map<String, Object> targetValues) {
        Map<String, Object> mapFields = new HashMap<String, Object>();
        Map<String, Object> mapAttributes = new HashMap<String, Object>();
        mapValues2Index(indexMappings, targetValues, mapFields, mapAttributes);
        Pair<Map<String, Object>, Map<String, Object>> result = new Pair<Map<String, Object>, Map<String, Object>>(
                mapFields, mapAttributes);
        return result;
    }

    @SuppressWarnings("unchecked")
    private void mapValues2Index(Map<String, Object> indexMapping, Map<String, Object> values,
            Map<String, Object> mapFields, Map<String, Object> mapAttributes) {
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
                mapValue2Index(indexValue.toString(), targetValue, mapFields, mapAttributes);
            } else if (targetValue instanceof Map) {
                mapValues2Index((Map<String, Object>) indexValue, (Map<String, Object>) targetValue, mapFields,
                        mapAttributes);
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
                            mapFields, mapAttributes);
                } else {
                    mapValue2Index(sublistIndexValue.toString(), sublistTargetValue, mapFields, mapAttributes);
                }
            }
        }
    }

    private String parseFieldName(String text) {
        if (StringUtils.isEmpty(text) || !text.contains(AT)) {
            return null;
        }
        return text.substring(text.indexOf(AT) + 1);
    }

    private void mapValue2Index(String fieldNameKey, Object value, Map<String, Object> mapFields,
            Map<String, Object> mapAttributes) {
        String[] rightKeys = fieldNameKey.toString().split(PILE);
        for (String rightKey : rightKeys) {
            if (StringUtils.isEmpty(rightKey) || !rightKey.contains(AT)) {
                logger.warn("Field Name: " + rightKey + " is invalid. Must contain " + AT);
            }
            if (rightKey.startsWith(A_AT)) {
                mapAttributes.put(parseFieldName(rightKey), value);
            } else if (rightKey.startsWith(C_AT)) {
                mapFields.put(parseFieldName(rightKey), value);
            }
        }
    }

}
