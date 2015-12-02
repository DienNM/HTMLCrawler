package com.myprj.crawler.service.crawl;

import com.myprj.crawler.domain.crawl.WorkerContext;
import com.myprj.crawler.domain.crawl.WorkerData;
import com.myprj.crawler.domain.crawl.WorkerItemData;
import com.myprj.crawler.exception.WorkerException;
import com.myprj.crawler.model.crawl.WorkerModel;

/**
 * @author DienNM (DEE)
 */

public interface WorkerService {

    WorkerModel save(WorkerData worker);
    
    void update(WorkerData worker);
    
    void invoke(WorkerContext workerCtx, WorkerItemData workerItem) throws WorkerException;
    
}
