package com.myprj.crawler.service;

import java.util.List;

import com.myprj.crawler.domain.config.AttributeData;

/**
 * @author DienNM (DEE)
 */

public interface AttributeService {

    AttributeData get(String id);
    
    AttributeData getAndPopulate(String id);
    
    AttributeData getRoot(long itemId);
    
    void populate(AttributeData attribute);
    
    void populateChildren(AttributeData attribute);

    void populateParent(AttributeData attribute);
    
    List<AttributeData> getByItemId(long itemId);
    
    AttributeData save(AttributeData attribute);
    
    List<AttributeData> save(List<AttributeData> attributes); 
    
}
