package com.myprj.crawler.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.myprj.crawler.domain.config.AttributeData;
import com.myprj.crawler.service.AttributeService;

/**
 * @author DienNM (DEE)
 */
@Service("inMemoryAttributeService")
public class InMemoryAttributeServiceImpl implements AttributeService {
    
    private static Map<String, AttributeData> repo = new HashMap<String, AttributeData>();

    @Override
    public AttributeData get(String id) {
        return repo.get(id);
    }

    @Override
    public AttributeData getAndPopulate(String id) {
        return get(id);
    }

    @Override
    public AttributeData getRoot(long itemId) {
        return repo.get(itemId + "|content");
    }

    @Override
    public void populate(AttributeData attribute) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void populateChildren(AttributeData attribute) {
        // TODO Auto-generated method stub
    }

    @Override
    public void populateParent(AttributeData attribute) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public List<AttributeData> getByItemId(long itemId) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public AttributeData save(AttributeData attribute) {
        return repo.put(attribute.getId(), attribute);
    }

    @Override
    public List<AttributeData> save(List<AttributeData> attributes) {
        for(AttributeData att : attributes) {
            repo.put(att.getId(), att);
        }
        return attributes;
    }

}
