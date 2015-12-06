package com.myprj.crawler.service;

import java.util.List;

import com.myprj.crawler.domain.config.ItemAttributeData;

/**
 * @author DienNM (DEE)
 */

public interface ItemAttributeService {

    ItemAttributeData get(String id);
    
    ItemAttributeData getAndPopulate(String id);

    void populate(ItemAttributeData attribute);

    void populateChildren(ItemAttributeData itemAttribute);

    void populateParent(ItemAttributeData itemAttribute);
    
    void populateAttribute(ItemAttributeData itemAttribute);

    List<ItemAttributeData> getByWorkerItemId(long workerItemId);
    
    ItemAttributeData getRootWithPopulateTree(long workerItemId);

    ItemAttributeData save(ItemAttributeData itemAttribute);

    List<ItemAttributeData> save(List<ItemAttributeData> itemAttributes);
}
