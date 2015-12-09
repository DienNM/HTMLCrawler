package com.myprj.crawler.repository.target.impl;

import java.util.List;

import javax.persistence.Query;

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

    @SuppressWarnings("unchecked")
    @Override
    public List<AttributeMappingModel> findByGroup(String siteKey, String categoryKey, String itemKey) {
        Query query = entityManager.createQuery("FROM " + getClassName()
                + " WHERE siteKey = :siteKey AND categoryKey = :categoryKey AND itemKey = :itemKey ");
        query.setParameter("siteKey", siteKey);
        query.setParameter("categoryKey", categoryKey);
        query.setParameter("itemKey", itemKey);
        return query.getResultList();
    }

}
