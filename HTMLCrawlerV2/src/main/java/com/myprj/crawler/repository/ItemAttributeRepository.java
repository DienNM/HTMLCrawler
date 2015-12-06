package com.myprj.crawler.repository;

import java.util.List;

import com.myprj.crawler.model.config.ItemAttributeModel;

/**
 * @author DienNM (DEE)
 */

public interface ItemAttributeRepository extends GenericDao<ItemAttributeModel, String> {
    
    List<ItemAttributeModel> findChildren(String id);
    
    List<ItemAttributeModel> findByWorkerItemId(long workerItemId);
    
    void deleteByItemId(long itemId);
    
    void deleteByWorkerItemId(long workerItemId);
    
}
