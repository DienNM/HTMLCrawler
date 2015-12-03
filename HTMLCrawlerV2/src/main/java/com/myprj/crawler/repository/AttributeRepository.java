package com.myprj.crawler.repository;

import java.util.List;

import com.myprj.crawler.model.config.AttributeModel;

/**
 * @author DienNM (DEE)
 */

public interface AttributeRepository extends GenericDao<AttributeModel, String>{
    
    List<AttributeModel> findChildren(String id);
    
    List<AttributeModel> findByItemId(long itemId);
    
    AttributeModel findRootByItemId(long itemId);
    
    void deleteByItemId(long itemId);
    
}
