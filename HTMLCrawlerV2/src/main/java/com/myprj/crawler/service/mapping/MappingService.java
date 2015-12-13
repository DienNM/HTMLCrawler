package com.myprj.crawler.service.mapping;

import java.util.Map;

import com.myprj.crawler.domain.DataMapping;

/**
 * @author DienNM (DEE)
 */

public interface MappingService {

    Pair<Map<String, Object>, Map<String, Object>> doMappingIndex(Map<String, Object> indexMappings,
            Map<String, Object> targetValues);
    
    Pair<DataMapping, DataMapping> loadDataMappings(String site);
    
    Map<String, Object> applyRuleMapping(String siteKey, Map<String, Object> originalObject);

}
