package com.myprj.crawler.repository.impl;

import org.springframework.stereotype.Repository;

import com.myprj.crawler.model.config.AttributeModel;
import com.myprj.crawler.repository.AttributeRepository;

/**
 * @author DienNM (DEE)
 */

@Repository
public class DefaultAttributeRepository extends DefaultGenericDao<AttributeModel, String> implements AttributeRepository {

    @Override
    protected Class<AttributeModel> getTargetClass() {
        return AttributeModel.class;
    }

}
