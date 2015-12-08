package com.myprj.crawler.repository;

import java.util.List;

import com.myprj.crawler.model.config.WorkerItemAttributeModel;

/**
 * @author DienNM (DEE)
 */

public interface WorkerItemAttributeRepository extends GenericDao<WorkerItemAttributeModel, String> {
    
    List<WorkerItemAttributeModel> findChildren(String id);
    
    List<WorkerItemAttributeModel> findByWorkerItemId(long workerItemId);
    
    void deleteByItemKey(String itemKey);
    
    void deleteByWorkerItemId(long workerItemId);
    
}
