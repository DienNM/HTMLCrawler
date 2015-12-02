package com.myprj.crawler.repository.impl;

import org.springframework.stereotype.Repository;

import com.myprj.crawler.model.config.ItemAttributeModel;
import com.myprj.crawler.repository.ItemAttributeRepository;

/**
 * @author DienNM (DEE)
 */
@Repository
public class DefaultItemAttributeRepository extends DefaultGenericDao<ItemAttributeModel, Long> implements ItemAttributeRepository{

    @Override
    protected Class<ItemAttributeModel> getTargetClass() {
        return ItemAttributeModel.class;
    }

}
