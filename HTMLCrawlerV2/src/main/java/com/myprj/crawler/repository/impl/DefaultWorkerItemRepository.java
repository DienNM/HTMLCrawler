package com.myprj.crawler.repository.impl;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.myprj.crawler.enumeration.Level;
import com.myprj.crawler.model.crawl.WorkerItemModel;
import com.myprj.crawler.repository.WorkerItemRepository;

/**
 * @author DienNM (DEE)
 */
@Repository
public class DefaultWorkerItemRepository extends DefaultGenericDao<WorkerItemModel, Long> implements WorkerItemRepository {

    @Override
    protected Class<WorkerItemModel> getTargetClass() {
        return WorkerItemModel.class;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<WorkerItemModel> findByWorkerId(long workerId) {
        Query query = entityManager.createQuery("FROM " + getClassName() + " WHERE workerId = :workerId");
        query.setParameter("workerId", workerId);
        List<WorkerItemModel> workerItemModels = query.getResultList();
        return workerItemModels;
    }

    @SuppressWarnings("unchecked")
    @Override
    public WorkerItemModel findByWorkerIdAndLevel(long workerId, Level level) {
        Query query = entityManager.createQuery("FROM " + getClassName() + 
                " WHERE workerId = :workerId AND level = :level ");
        query.setParameter("workerId", workerId);
        query.setParameter("level", level);
        List<WorkerItemModel> workerItemModels = query.getResultList();
        if(workerItemModels.isEmpty()) {
            return null;
        }
        return workerItemModels.get(0);
    }

}
