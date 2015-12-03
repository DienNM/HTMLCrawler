package com.myprj.crawler.service;

import java.util.List;

import com.myprj.crawler.domain.PageResult;
import com.myprj.crawler.domain.Pageable;
import com.myprj.crawler.domain.crawl.WorkerData;

/**
 * @author DienNM (DEE)
 */

public interface WorkerService {
    
    WorkerData get(long id);
    
    List<WorkerData> getAll();
    
    PageResult<WorkerData> getPaging(Pageable pageable);
    
    WorkerData save(WorkerData worker);
    
    WorkerData update(WorkerData worker);
    
}
