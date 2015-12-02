package com.myprj.crawler.service;

import com.myprj.crawler.domain.config.AttributeData;
import com.myprj.crawler.domain.config.ItemData;

/**
 * @author DienNM (DEE)
 */

public interface ItemStructureBuilder {
    
    AttributeData build(ItemData item, String jsonText);
    
    void print(AttributeData root);
    
}
