package com.myprj.crawler.repository.impl;

import org.springframework.stereotype.Repository;

import com.myprj.crawler.model.config.ItemModel;
import com.myprj.crawler.repository.ItemRepository;

/**
 * @author DienNM (DEE)
 */
@Repository
public class DefaultItemRepository extends DefaultGenericDao<ItemModel, Long> implements ItemRepository {

    @Override
    protected Class<ItemModel> getTargetClass() {
        return ItemModel.class;
    }

}
