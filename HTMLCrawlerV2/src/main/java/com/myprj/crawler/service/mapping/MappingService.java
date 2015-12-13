package com.myprj.crawler.service.mapping;

import java.util.List;
import java.util.Map;

import com.myprj.crawler.domain.DataMapping;

/**
 * @author DienNM (DEE)
 */

public interface MappingService {

    Map<String, Object> doMappingIndex(Map<String, Object> indexMappings, Map<String, Object> targetValues);

    List<DataMapping> loadDataMappings(String site, String category, String item);

    void applyRuleMapping(List<DataMapping> dataMappings, Map<String, Object> values);

}
