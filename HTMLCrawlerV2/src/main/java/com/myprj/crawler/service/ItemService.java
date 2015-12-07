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

    ItemData getByKey(String key);

    PageResult<ItemData> getAllWithPaging(Pageable pageable);

    void populateAttributes(ItemData item);

    ItemData save(ItemData item);

    List<ItemData> saveOrUpdate(List<ItemData> items);

    ItemData update(ItemData item);

    ItemData buildItem(String itemKey, String jsonAttributes, boolean forceBuild);

    void delete(long id);

    void delete(List<Long> ids);

}
