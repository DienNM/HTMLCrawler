package com.myprj.crawler.repository;

import com.myprj.crawler.model.crawl.WorkerItemModel;

/**
 * @author DienNM (DEE)
 */

public interface WorkerItemRepository {
    
    WorkerItemModel save(WorkerItemModel workerItem);
    
    WorkerItemModel find(long id);
    
}
