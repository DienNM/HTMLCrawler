package com.myprj.crawler.service;

import java.util.List;

import com.myprj.crawler.domain.config.ItemAttributeData;

/**
 * @author DienNM (DEE)
 */

public interface ItemAttributeService {

    ItemAttributeData get(long id);
    
    ItemAttributeData getAndPopulate(long id);

    ItemAttributeData getRoot(long itemId);

    void populate(ItemAttributeData attribute);

    void populateChildren(ItemAttributeData itemAttribute);

    void populateParent(ItemAttributeData itemAttribute);
    
    void populateAttribute(ItemAttributeData itemAttribute);

    List<ItemAttributeData> getByItemId(long itemId);

    ItemAttributeData save(ItemAttributeData itemAttribute);

    List<ItemAttributeData> save(List<ItemAttributeData> itemAttributes);
}
