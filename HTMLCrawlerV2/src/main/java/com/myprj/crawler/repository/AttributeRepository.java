package com.myprj.crawler.repository;

import com.myprj.crawler.model.config.AttributeModel;

/**
 * @author DienNM (DEE)
 */

public interface AttributeRepository {
    
    AttributeModel find(Long id);
    
    AttributeModel save(AttributeModel attribute);
    
    
}
