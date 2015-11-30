package com.myprj.crawler.repository;

import com.myprj.crawler.model.crawl.WorkerModel;

/**
 * @author DienNM (DEE)
 */

public interface WorkerRepository {
    
    WorkerModel save(WorkerModel worker);
    
    WorkerModel find(long id);
    
}
