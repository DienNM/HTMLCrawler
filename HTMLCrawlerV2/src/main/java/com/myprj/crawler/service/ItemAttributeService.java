package com.myprj.crawler.service;

import java.util.List;

import com.myprj.crawler.domain.config.ItemAttributeData;

/**
 * @author DienNM (DEE)
 */

public interface ItemAttributeService {

    ItemAttributeData get(String id);
    
    ItemAttributeData getAndPopulate(String id);
    
    ItemAttributeData getRoot(String itemId);
    
    void populate(ItemAttributeData attribute);
    
    void populateChildren(ItemAttributeData attribute);

    void populateParent(ItemAttributeData attribute);
    
    List<ItemAttributeData> getByItemId(String itemId);
    
    ItemAttributeData save(ItemAttributeData attribute);
    
    List<ItemAttributeData> save(List<ItemAttributeData> attributes); 
    
}
