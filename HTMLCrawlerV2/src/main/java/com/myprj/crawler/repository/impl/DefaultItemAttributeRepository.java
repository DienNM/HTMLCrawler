package com.myprj.crawler.repository.impl;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.myprj.crawler.model.config.ItemAttributeModel;
import com.myprj.crawler.repository.ItemAttributeRepository;

/**
 * @author DienNM (DEE)
 */
@Repository
public class DefaultItemAttributeRepository extends DefaultGenericDao<ItemAttributeModel,String> implements ItemAttributeRepository{

    @Override
    protected Class<ItemAttributeModel> getTargetClass() {
        return ItemAttributeModel.class;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<ItemAttributeModel> findChildren(String id) {
        Query query = entityManager.createQuery("FROM " + getClassName() + " WHERE parentId = :parentId");
        query.setParameter("parentId", id);
        return query.getResultList();
    }

    @Override
    public void deleteByItemId(long itemId) {
        Query query = entityManager.createQuery("DELETE FROM " + getClassName() + " WHERE itemId = :itemId");
        query.setParameter("itemId", itemId);
        query.executeUpdate();
    }

    @Override
    public void deleteByWorkerItemId(long workerItemId) {
        Query query = entityManager.createQuery("DELETE FROM " + getClassName() + " WHERE workerItemId = :workerItemId");
        query.setParameter("workerItemId", workerItemId);
        query.executeUpdate();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<ItemAttributeModel> findByWorkerItemId(long workerItemId) {
        Query query = entityManager.createQuery("FROM " + getClassName() + " WHERE workerItemId = :workerItemId");
        query.setParameter("workerItemId", workerItemId);
        return query.getResultList();
    }

}
