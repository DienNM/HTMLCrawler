package com.myprj.crawler.service;

import com.myprj.crawler.domain.config.AttributeData;

/**
 * @author DienNM (DEE)
 */

public interface ItemStructureBuilder {
    
    AttributeData build(long itemId, String jsonText);
    
    void print(AttributeData root);
    
}
