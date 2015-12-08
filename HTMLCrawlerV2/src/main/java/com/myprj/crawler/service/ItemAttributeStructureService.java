package com.myprj.crawler.service;

import com.myprj.crawler.domain.config.ItemAttributeData;
import com.myprj.crawler.domain.config.ItemData;

/**
 * @author DienNM (DEE)
 */

public interface ItemAttributeStructureService {
    
    ItemAttributeData build(ItemData item, String jsonText);
    
}
