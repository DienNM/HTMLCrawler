package com.myprj.crawler.repository.target.impl;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.myprj.crawler.model.target.ConsolidationAttributeId;
import com.myprj.crawler.model.target.ConsolidationAttributeModel;
import com.myprj.crawler.repository.impl.DefaultGenericDao;
import com.myprj.crawler.repository.target.ConsolidationAttributeRepository;

/**
 * @author DienNM (DEE)
 */
@Repository
public class DefaultConsolidationAttributeRepository extends
        DefaultGenericDao<ConsolidationAttributeModel, ConsolidationAttributeId> implements
        ConsolidationAttributeRepository {

    @Override
    protected Class<ConsolidationAttributeModel> getTargetClass() {
        return ConsolidationAttributeModel.class;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<ConsolidationAttributeModel> findByConsolidationId(String consolidationId) {
        Query query = entityManager
                .createQuery("FROM " + getClassName() + " WHERE id.consolidationId = :consolidationId ");
        query.setParameter("consolidationId", consolidationId);
        return query.getResultList();
    }

    @Override
    public void deleteByConsolidationId(String consolidationId) {
        Query query = entityManager.createQuery("DELETE FROM " + getClassName()
                + " WHERE id.consolidationId = :consolidationId ");
        query.setParameter("consolidationId", consolidationId);
        query.executeUpdate();
    }

}
