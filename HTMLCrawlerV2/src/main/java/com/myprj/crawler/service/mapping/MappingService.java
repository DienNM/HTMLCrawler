package com.myprj.crawler.service.mapping;

import java.util.Map;

/**
 * @author DienNM (DEE)
 */

public interface MappingService {

    Pair<Map<String, Object>, Map<String, Object>> doMappingIndex(Map<String, Object> indexMappings,
            Map<String, Object> targetValues);

}
