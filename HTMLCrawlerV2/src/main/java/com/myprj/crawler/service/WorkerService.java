package com.myprj.crawler.service;

import java.util.List;

import com.myprj.crawler.domain.PageResult;
import com.myprj.crawler.domain.Pageable;
import com.myprj.crawler.domain.config.ItemAttributeData;
import com.myprj.crawler.domain.crawl.WorkerData;
import com.myprj.crawler.domain.crawl.WorkerItemData;
import com.myprj.crawler.enumeration.Level;

/**
 * @author DienNM (DEE)
 */

public interface WorkerService {
    
    WorkerData get(long id);
    
    List<WorkerData> getAll();
    
    PageResult<WorkerData> getPaging(Pageable pageable);
    
    void populateWorkerItems(WorkerData worker);
    
    WorkerData save(WorkerData worker);
    
    WorkerData update(WorkerData worker);
    
    void addWorkerItems(WorkerData worker, List<WorkerItemData> workerItems);
    
    ItemAttributeData buildSelector4Item(WorkerData worker, Level level, String jsonSelector);
    
}
