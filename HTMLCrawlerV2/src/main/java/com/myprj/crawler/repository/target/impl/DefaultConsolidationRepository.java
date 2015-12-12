package com.myprj.crawler.repository.target.impl;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.myprj.crawler.model.target.ConsolidationId;
import com.myprj.crawler.model.target.ConsolidationModel;
import com.myprj.crawler.repository.impl.DefaultGenericDao;
import com.myprj.crawler.repository.target.ConsolidationRepository;

/**
 * @author DienNM (DEE)
 */
@Repository
public class DefaultConsolidationRepository extends DefaultGenericDao<ConsolidationModel, ConsolidationId> implements
        ConsolidationRepository {

    @Override
    protected Class<ConsolidationModel> getTargetClass() {
        return ConsolidationModel.class;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<ConsolidationModel> findByCategoryAndSite(String category, String siteKey) {
        Query query = entityManager.createQuery("FROM " + getClassName()
                + " WHERE id.categoryKey = :categoryKey AND id.siteKey = :siteKey ");
        query.setParameter("categoryKey", category);
        query.setParameter("siteKey", siteKey);
        return query.getResultList();
    }

    @SuppressWarnings("unchecked")
    @Override
    public ConsolidationModel findByMd5Key(String md5Key) {
        Query query = entityManager.createQuery("FROM " + getClassName() + " WHERE md5Key = :md5Key ");
        query.setParameter("md5Key", md5Key);
        List<ConsolidationModel> consolidationModels = query.getResultList();
        if (consolidationModels.isEmpty()) {
            return null;
        }
        return consolidationModels.get(0);
    }

}
