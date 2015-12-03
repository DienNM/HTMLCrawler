package com.myprj.crawler.repository;

import java.util.List;

import com.myprj.crawler.enumeration.Level;
import com.myprj.crawler.model.crawl.WorkerItemModel;

/**
 * @author DienNM (DEE)
 */

public interface WorkerItemRepository extends GenericDao<WorkerItemModel, Long> {

    List<WorkerItemModel> findByWorkerId(long workerId);
    
    WorkerItemModel findByWorkerIdAndLevel(long workerId, Level level);
    
}
