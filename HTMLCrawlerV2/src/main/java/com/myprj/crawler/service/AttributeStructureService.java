package com.myprj.crawler.service;

import com.myprj.crawler.domain.config.AttributeData;
import com.myprj.crawler.domain.config.ItemData;

/**
 * @author DienNM (DEE)
 */

public interface AttributeStructureService {
    
    AttributeData build(ItemData item, String jsonText);
    
}
