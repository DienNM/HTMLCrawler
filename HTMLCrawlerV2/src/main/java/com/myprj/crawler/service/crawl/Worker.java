package com.myprj.crawler.service.crawl;

import com.myprj.crawler.domain.crawl.WorkerContext;
import com.myprj.crawler.domain.crawl.WorkerItemData;
import com.myprj.crawler.exception.WorkerException;

/**
 * @author DienNM (DEE)
 */

public interface Worker {
    
    void invoke(WorkerContext workerCtx, WorkerItemData workerItem) throws WorkerException;
    
}
