package com.myprj.crawler.service.cache;

import java.util.Map;

import com.myprj.crawler.model.config.AttributeModel;

/**
 * @author DienNM (DEE)
 */

public interface AttributeCacheService {
    
    Map<Long, AttributeModel> findAll(Long itemId);
    
    AttributeModel find(Long id);
    
    void updateCache(AttributeModel attribute);
    
}
