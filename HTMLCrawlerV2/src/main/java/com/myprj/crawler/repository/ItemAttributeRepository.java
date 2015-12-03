package com.myprj.crawler.repository;

import java.util.List;

import com.myprj.crawler.model.config.ItemAttributeModel;

/**
 * @author DienNM (DEE)
 */

public interface ItemAttributeRepository extends GenericDao<ItemAttributeModel, String> {
    
    List<ItemAttributeModel> findChildren(String id);
    
    void deleteByItemId(long itemId);
    
}
