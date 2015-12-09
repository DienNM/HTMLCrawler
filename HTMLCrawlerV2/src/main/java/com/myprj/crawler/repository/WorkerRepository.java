package com.myprj.crawler.repository;

import java.util.List;

import com.myprj.crawler.enumeration.WorkerStatus;
import com.myprj.crawler.model.crawl.WorkerModel;

/**
 * @author DienNM (DEE)
 */

public interface WorkerRepository extends GenericDao<WorkerModel, Long> {
    
    List<WorkerModel> findByStatus(WorkerStatus status);
    
    WorkerModel findByKey(String key);
    
    List<WorkerModel> findByKeys(List<String> keys);
    
}
