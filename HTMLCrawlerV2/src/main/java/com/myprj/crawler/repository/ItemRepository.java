package com.myprj.crawler.repository;

import java.util.List;

import com.myprj.crawler.model.config.ItemModel;

/**
 * @author DienNM (DEE)
 */

public interface ItemRepository extends GenericDao<ItemModel, String> {
    
    List<ItemModel> findByCategory(String category);

}
