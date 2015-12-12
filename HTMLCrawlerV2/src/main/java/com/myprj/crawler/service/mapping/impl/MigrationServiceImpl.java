package com.myprj.crawler.service.mapping.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.myprj.crawler.domain.MigrationParam;
import com.myprj.crawler.domain.crawl.CrawlResultData;
import com.myprj.crawler.domain.target.ConsolidationData;
import com.myprj.crawler.service.mapping.MigrationService;
import com.myprj.crawler.util.Serialization;
import com.myprj.crawler.util.StreamUtil;

/**
 * @author DienNM (DEE)
 */
@Service
public class MigrationServiceImpl implements MigrationService {

    private Logger logger = LoggerFactory.getLogger(MigrationServiceImpl.class);

    @SuppressWarnings("unchecked")
    @Override
    public void migrate(MigrationParam migrationParam, List<CrawlResultData> crawlResults) {
        String valueJsons = StreamUtil.readFile2String(MigrationService.class.getResourceAsStream("/value.json"));
        Map<String, Object> values = Serialization.deserialize(valueJsons, Map.class);
        mapAttributeName(migrationParam.getIndexMapping(), (Map<String, Object>) values.get("content"));
    }

    private ConsolidationData mapAttributeName(Map<String, Object> indexMapping, Map<String, Object> values) {
        ConsolidationData consolidationData = new ConsolidationData();

        Map<String, Object> mapFields = new HashMap<String, Object>();
        Map<String, Object> mapAttributes = new HashMap<String, Object>();

        mapValues2Index(indexMapping, values, mapFields, mapAttributes);
        return consolidationData;
    }

    @SuppressWarnings("unchecked")
    private void mapValues2Index(Map<String, Object> indexMapping, Map<String, Object> values, Map<String, Object> mapFields,
            Map<String, Object> mapAttributes) {
        for (String key : indexMapping.keySet()) {
            if (!values.containsKey(key)) {
                logger.warn("Key: " + key + " not in mapping value");
            }
            Object indexValue = indexMapping.get(key);
            Object targetValue = values.get(key);

            if (indexValue == null || targetValue == null) {
                continue;
            }
            if (!indexValue.getClass().getName().equals(targetValue.getClass().getName())) {
                logger.warn("Key: " + key + " not in mapping value");
            }
            if (indexValue instanceof String) {
                mapValue2Index(indexValue.toString(), targetValue, mapFields, mapAttributes);
            } else if (targetValue instanceof Map) {
                mapValues2Index((Map<String, Object>) indexValue, (Map<String, Object>) targetValue, mapFields, mapAttributes);
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
                    mapValues2Index((Map<String, Object>) sublistIndexValue, (Map<String, Object>) sublistTargetValue, mapFields,
                            mapAttributes);
                } else {
                    mapValue2Index(sublistIndexValue.toString(), sublistTargetValue, mapFields, mapAttributes);
                }
            }
        }
    }

    private String parseFieldName(String text) {
        if (StringUtils.isEmpty(text) || !text.contains("@")) {
            return null;
        }
        return text.substring(text.indexOf("@") + 1);
    }

    private void mapValue2Index(String fieldNameKey, Object value, Map<String, Object> mapFields,
            Map<String, Object> mapAttributes) {
        String[] rightKeys = fieldNameKey.toString().split(Pattern.quote("|"));
        for (String rightKey : rightKeys) {
            if (StringUtils.isEmpty(rightKey) || !rightKey.contains("@")) {
                logger.warn("Field Name: " + rightKey + " is invalid. Must contain @");
            }
            if (rightKey.startsWith("A@")) {
                mapAttributes.put(parseFieldName(rightKey), value);
            } else if (rightKey.startsWith("C@")) {
                mapFields.put(parseFieldName(rightKey), value);
            }
        }
    }

    public static void main(String[] args) {
        MigrationService migrationService = new MigrationServiceImpl();
        MigrationParam migrationParam = new MigrationParam("recruitment", "job-recruitment", "vieclamnhanh");
        migrationService.migrate(migrationParam, null);
    }
}
