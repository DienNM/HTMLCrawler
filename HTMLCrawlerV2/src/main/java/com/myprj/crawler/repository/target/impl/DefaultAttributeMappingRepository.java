package com.myprj.crawler.repository.target.impl;

import org.springframework.stereotype.Repository;

import com.myprj.crawler.model.target.AttributeMappingId;
import com.myprj.crawler.model.target.AttributeMappingModel;
import com.myprj.crawler.repository.impl.DefaultGenericDao;
import com.myprj.crawler.repository.target.AttributeMappingRepository;

/**
 * @author DienNM (DEE)
 */
@Repository
public class DefaultAttributeMappingRepository extends DefaultGenericDao<AttributeMappingModel, AttributeMappingId>
        implements AttributeMappingRepository {

    @Override
    protected Class<AttributeMappingModel> getTargetClass() {
        return AttributeMappingModel.class;
    }

}
