package com.myprj.crawler.repository.target.impl;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.myprj.crawler.model.target.ConsolidationAttributeModel;
import com.myprj.crawler.repository.impl.DefaultGenericDao;
import com.myprj.crawler.repository.target.ConsolidationAttributeRepository;

/**
 * @author DienNM (DEE)
 */
@Repository
public class DefaultConsolidationAttributeRepository extends DefaultGenericDao<ConsolidationAttributeModel, Long> implements
        ConsolidationAttributeRepository {

    @SuppressWarnings("unchecked")
    @Override
    public List<ConsolidationAttributeModel> findByConsolidationId(long consolidationId) {
        Query query = entityManager.createQuery("FROM " + getClassName() + " WHERE consolidationId = :consolidationId ");
        query.setParameter("consolidationId", consolidationId);
        return query.getResultList();
    }

    @SuppressWarnings("unchecked")
    @Override
    public ConsolidationAttributeModel findByNameAndConsolidationId(String name, long consolidationId) {
        Query query = entityManager.createQuery("FROM " + getClassName()
                + " WHERE name = :name AND consolidationId = :consolidationId ");
        query.setParameter("name", name);
        query.setParameter("consolidationId", consolidationId);
        List<ConsolidationAttributeModel> consolidationAttributeModels = query.getResultList();
        if (consolidationAttributeModels.isEmpty()) {
            return null;
        }
        return consolidationAttributeModels.get(0);
    }

    @Override
    protected Class<ConsolidationAttributeModel> getTargetClass() {
        return ConsolidationAttributeModel.class;
    }

    @Override
    public void deleteByConsolidationId(long consolidationId) {
        Query query = entityManager.createQuery("DELETE FROM " + getClassName() + 
                " WHERE consolidationId = :consolidationId ");
        query.setParameter("consolidationId", consolidationId);
        query.executeUpdate();
    }

}
