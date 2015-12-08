package com.myprj.crawler.repository;

import java.util.List;

import com.myprj.crawler.model.config.ItemAttributeModel;

/**
 * @author DienNM (DEE)
 */

public interface ItemAttributeRepository extends GenericDao<ItemAttributeModel, String>{
    
    List<ItemAttributeModel> findChildren(String id);
    
    List<ItemAttributeModel> findByItemId(long itemId);
    
    ItemAttributeModel findRootByItemId(long itemId);
    
    void deleteByItemId(long itemId);
    
}
