package com.myprj.crawler.service.target;

import java.util.List;

import com.myprj.crawler.domain.target.AttributeMappingData;

/**
 * @author DienNM (DEE)
 */

public interface AttributeMappingService {
    
    AttributeMappingData save(AttributeMappingData attributeMappingData);
    
    void update(AttributeMappingData attributeMappingData);
    
    AttributeMappingData get(String categoryKey, String itemKey, String siteKey, String name);
    
    List<AttributeMappingData> get(String categoryKey, String itemKey, String siteKey);
    
}
