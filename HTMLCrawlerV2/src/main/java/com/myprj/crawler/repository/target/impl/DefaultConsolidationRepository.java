package com.myprj.crawler.repository.target.impl;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.myprj.crawler.model.target.ConsolidationModel;
import com.myprj.crawler.repository.impl.DefaultGenericDao;
import com.myprj.crawler.repository.target.ConsolidationRepository;

/**
 * @author DienNM (DEE)
 */
@Repository
public class DefaultConsolidationRepository extends DefaultGenericDao<ConsolidationModel, Long> implements ConsolidationRepository {

    @SuppressWarnings("unchecked")
    @Override
    public ConsolidationModel findByKeyAndSite(String key, String site) {
        Query query = entityManager.createQuery("FROM " + getClassName() + " WHERE key = :key AND site = :site ");
        query.setParameter("key", key);
        query.setParameter("site", site);
        List<ConsolidationModel> consolidationModels = query.getResultList();
        if (consolidationModels.isEmpty()) {
            return null;
        }
        return consolidationModels.get(0);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<ConsolidationModel> findByCategoryAndSite(String category, String site) {
        Query query = entityManager.createQuery("FROM " + getClassName()
                + " WHERE categoryKey = :categoryKey AND site = :site ");
        query.setParameter("categoryKey", category);
        query.setParameter("site", site);
        return query.getResultList();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<ConsolidationModel> findByName(String name) {
        Query query = entityManager.createQuery("FROM " + getClassName() + " WHERE name LIKE :name ");
        query.setParameter("name", "%" + name + "%");
        return query.getResultList();
    }

    @Override
    protected Class<ConsolidationModel> getTargetClass() {
        return ConsolidationModel.class;
    }

}
