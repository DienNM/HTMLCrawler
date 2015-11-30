package com.myprj.crawler.service.impl;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.myprj.crawler.domain.WorkerContext;
import com.myprj.crawler.exception.CrawlerException;
import com.myprj.crawler.exception.WorkerException;
import com.myprj.crawler.model.crawl.WorkerItemModel;
import com.myprj.crawler.model.crawl.WorkerModel;
import com.myprj.crawler.service.CrawlerService;
import com.myprj.crawler.service.WorkerService;

/**
 * @author DienNM (DEE)
 */

public class DefaultCrawlerService implements CrawlerService {
    
    private static Map<Long, WorkerContext> workerContextCache = new HashMap<Long, WorkerContext>();
    
    private WorkerService workerService;
    
    @Override
    public void crawl(WorkerModel worker) throws CrawlerException {
        WorkerContext workerCtx = workerContextCache.get(worker.getId());
        if(workerCtx == null) {
            throw new CrawlerException(String.format("Worker %s [name = %s] has not registered yet", 
                    worker.getId(), worker.getName()));
        }
        List<WorkerItemModel> workerItems = worker.getWorkerItems();
        Collections.sort(workerItems);
        
        for(WorkerItemModel workerItem : workerItems) {
            try {
                workerService.doWork(workerCtx, workerItem);
            } catch (WorkerException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void init(WorkerModel worker) {
        workerContextCache.put(worker.getId(), new WorkerContext(worker));
    }

    @Override
    public void destroy(WorkerModel worker) {
        WorkerContext workerContext = workerContextCache.get(worker.getId());
        if(workerContext != null) {
            workerContext.destroyWorker();
            workerContextCache.remove(worker.getId());
        }
    }

    public WorkerService getWorkerService() {
        return workerService;
    }

    public void setWorkerService(WorkerService workerService) {
        this.workerService = workerService;
    }

}
