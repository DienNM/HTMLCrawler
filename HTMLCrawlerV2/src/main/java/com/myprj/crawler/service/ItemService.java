package com.myprj.crawler.service;

import java.util.List;

import com.myprj.crawler.domain.PageResult;
import com.myprj.crawler.domain.Pageable;
import com.myprj.crawler.domain.config.ItemData;

/**
 * @author DienNM (DEE)
 */

public interface ItemService {

    ItemData get(long id);
    
    List<ItemData> getAll();
    
    PageResult<ItemData> getPaging(Pageable pageable);
    
    void populateAttributes(ItemData item);
    
    ItemData save(ItemData item);
    
    ItemData update(ItemData item);
    
    void delete(long id);
    
    ItemData buildItem(long itemId, String jsonAttributes);
    
    ItemData buildItem(long itemId, String jsonAttributes, boolean forceBuilt);
    
}
