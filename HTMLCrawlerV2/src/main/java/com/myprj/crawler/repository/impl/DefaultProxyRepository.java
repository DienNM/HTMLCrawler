package com.myprj.crawler.repository.impl;

import org.springframework.stereotype.Repository;

import com.myprj.crawler.model.ProxyModel;
import com.myprj.crawler.repository.ProxyRepository;

/**
 * @author DienNM (DEE)
 */
@Repository
public class DefaultProxyRepository extends DefaultGenericDao<ProxyModel, Long> implements ProxyRepository {

    @Override
    protected Class<ProxyModel> getTargetClass() {
        return ProxyModel.class;
    }

}
