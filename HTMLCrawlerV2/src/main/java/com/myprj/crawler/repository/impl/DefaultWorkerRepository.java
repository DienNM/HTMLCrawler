package com.myprj.crawler.repository.impl;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.myprj.crawler.enumeration.WorkerStatus;
import com.myprj.crawler.model.crawl.WorkerModel;
import com.myprj.crawler.repository.WorkerRepository;

/**
 * @author DienNM (DEE)
 */
@Repository
public class DefaultWorkerRepository extends DefaultGenericDao<WorkerModel, Long> implements WorkerRepository {

    @Override
    protected Class<WorkerModel> getTargetClass() {
        return WorkerModel.class;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<WorkerModel> findByStatus(WorkerStatus status) {
        Query query = entityManager.createQuery("FROM " + getClassName() + " WHERE status = :status");
        query.setParameter("status", status);
        return query.getResultList();
    }

}
