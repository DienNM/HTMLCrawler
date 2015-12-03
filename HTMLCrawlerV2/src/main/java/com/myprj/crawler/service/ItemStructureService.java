package com.myprj.crawler.service;

import com.myprj.crawler.domain.config.ItemData;

/**
 * @author DienNM (DEE)
 */

public interface ItemStructureService<T> {
    
    T buildAttributes(ItemData item, String jsonText);
    
}
