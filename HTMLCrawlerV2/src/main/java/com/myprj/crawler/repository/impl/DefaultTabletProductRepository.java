package com.myprj.crawler.repository.impl;

import org.springframework.stereotype.Repository;

import com.myprj.crawler.model.target.TabletProductModel;
import com.myprj.crawler.repository.TabletProductRepository;

/**
 * @author DienNM (DEE)
 */

@Repository
public class DefaultTabletProductRepository extends DefaultGenericDao<TabletProductModel, Long> implements
        TabletProductRepository {

    @Override
    protected Class<TabletProductModel> getTargetClass() {
        return TabletProductModel.class;
    }

}
