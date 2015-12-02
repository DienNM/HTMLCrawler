package com.myprj.crawler.service;

import com.myprj.crawler.domain.config.ItemData;

/**
 * @author DienNM (DEE)
 */

public interface ItemService {
    
    ItemData buildItem(ItemData item, String jsonAttributes);
    
    ItemData save(ItemData item);
    
    ItemData update(ItemData item);
    
    ItemData get(long id);
    
}
