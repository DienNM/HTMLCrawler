package com.myprj.crawler.repository.impl;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.myprj.crawler.model.crawl.WorkerItemAttributeModel;
import com.myprj.crawler.repository.WorkerItemAttributeRepository;

/**
 * @author DienNM (DEE)
 */
@Repository
public class DefaultWorkerItemAttributeRepository extends DefaultGenericDao<WorkerItemAttributeModel,String> implements WorkerItemAttributeRepository{

    @Override
    protected Class<WorkerItemAttributeModel> getTargetClass() {
        return WorkerItemAttributeModel.class;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<WorkerItemAttributeModel> findChildren(String id) {
        Query query = entityManager.createQuery("FROM " + getClassName() + " WHERE parentId = :parentId");
        query.setParameter("parentId", id);
        return query.getResultList();
    }

    @Override
    public void deleteByItemId(String itemKey) {
        Query query = entityManager.createQuery("DELETE FROM " + getClassName() + " WHERE itemKey = :itemKey");
        query.setParameter("itemKey", itemKey);
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
    public List<WorkerItemAttributeModel> findByWorkerItemId(long workerItemId) {
        Query query = entityManager.createQuery("FROM " + getClassName() + " WHERE workerItemId = :workerItemId");
        query.setParameter("workerItemId", workerItemId);
        return query.getResultList();
    }

}
