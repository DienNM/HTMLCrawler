package com.myprj.crawler.repository.impl;

import java.util.HashMap;
import java.util.Map;

import com.myprj.crawler.model.config.ItemModel;
import com.myprj.crawler.repository.ItemRepository;

/**
 * @author DienNM (DEE)
 */

public class DefaultItemRepository implements ItemRepository {
    
    private static Map<Long, ItemModel> repo = new HashMap<Long, ItemModel>();

    @Override
    public ItemModel save(ItemModel item) {
        repo.put(item.getId(), item);
        return item;
    }

    @Override
    public ItemModel find(long id) {
        return repo.get(id);
    }

}
