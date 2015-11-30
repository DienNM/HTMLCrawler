package com.myprj.crawler.repository.impl;

import java.util.HashMap;
import java.util.Map;

import com.myprj.crawler.model.config.AttributeModel;
import com.myprj.crawler.repository.AttributeRepository;

/**
 * @author DienNM (DEE)
 */

public class DefaultAttributeRepository implements AttributeRepository {
    
    public static Map<Long, AttributeModel> repo = new HashMap<Long, AttributeModel>();

    @Override
    public AttributeModel find(Long id) {
        return repo.get(id);
    }

    @Override
    public AttributeModel save(AttributeModel attribute) {
        repo.put(attribute.getId(), attribute);
        return attribute;
    }

}
