package com.myprj.crawler.repository.impl;

import org.springframework.stereotype.Repository;

import com.myprj.crawler.model.SiteModel;
import com.myprj.crawler.repository.SiteRepository;

/**
 * @author DienNM (DEE)
 */

@Repository
public class DefaultSiteRepository extends DefaultGenericDao<SiteModel, String> implements SiteRepository{

    @Override
    protected Class<SiteModel> getTargetClass() {
        return SiteModel.class;
    }

}
