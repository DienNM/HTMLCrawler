package com.myprj.crawler.service;

import java.util.List;

import com.myprj.crawler.domain.PageResult;
import com.myprj.crawler.domain.Pageable;
import com.myprj.crawler.domain.crawl.WorkerData;
import com.myprj.crawler.domain.crawl.WorkerItemData;
import com.myprj.crawler.enumeration.Level;

/**
 * @author DienNM (DEE)
 */

public interface WorkerService {
    
    WorkerData get(long id);
    
    List<WorkerData> getAll();
    
    PageResult<WorkerData> getAllWithPaging(Pageable pageable);
    
    void populateWorkerItems(WorkerData worker);
    
    WorkerData save(WorkerData worker);
    
    WorkerData update(WorkerData worker);
    
    void buildWorkerItems(WorkerData worker, List<WorkerItemData> workerItems);
    
    WorkerItemData getWorkerItem(WorkerData worker, Level level);
    
    WorkerItemData buildSelector4Item(WorkerItemData workerItem, String jsonSelector);
    
}
