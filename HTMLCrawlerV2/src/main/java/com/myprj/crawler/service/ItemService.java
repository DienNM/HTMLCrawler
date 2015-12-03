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
    
    PageResult<ItemData> getAll(Pageable pageable);
    
    ItemData save(ItemData item);
    
    ItemData update(ItemData item);
    
    ItemData buildItem(ItemData item, String jsonAttributes);
    
}
