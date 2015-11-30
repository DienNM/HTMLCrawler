package com.myprj.crawler.repository;

import com.myprj.crawler.model.config.ItemModel;

/**
 * @author DienNM (DEE)
 */

public interface ItemRepository {
    
    ItemModel save(ItemModel item);
    
    ItemModel find(long id);
    
}
