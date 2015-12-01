package com.myprj.crawler.service.cache.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.myprj.crawler.model.config.AttributeModel;
import com.myprj.crawler.service.cache.AttributeCacheService;

/**
 * @author DienNM (DEE)
 */
@Service
public class InMemoryAttributeCacheService implements AttributeCacheService {

    public static Map<Long, AttributeModel> cache = new HashMap<Long, AttributeModel>();
    
    @Override
    public Map<Long, AttributeModel> findAll(Long itemId) {
        Map<Long, AttributeModel> result = new HashMap<Long, AttributeModel>();
        for(AttributeModel att : cache.values()) {
            if(att.getItemId() == itemId) {
                result.put(att.getId(), att);
            }
        }
        return result;
    }

    @Override
    public AttributeModel find(Long id) {
        AttributeModel attribute = cache.get(id);
        return attribute;
    }

    @Override
    public void updateCache(AttributeModel attribute) {
        cache.put(attribute.getId(), attribute);
    }

}
