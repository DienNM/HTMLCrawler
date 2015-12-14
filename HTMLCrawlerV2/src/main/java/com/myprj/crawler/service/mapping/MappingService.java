package com.myprj.crawler.service.mapping;

import java.util.List;
import java.util.Map;

import com.myprj.crawler.domain.DataMapping;

/**
 * @author DienNM (DEE)
 */

public interface MappingService {

    Mapping doMappingIndex(Mapping index, Mapping value);

    void applyRuleMapping(List<DataMapping> ruleMappings, Map<String, Object> values);

}
