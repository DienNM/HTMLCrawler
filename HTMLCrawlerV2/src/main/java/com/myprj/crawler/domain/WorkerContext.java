package com.myprj.crawler.domain;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.myprj.crawler.enumeration.Level;
import com.myprj.crawler.model.crawl.WorkerItemModel;
import com.myprj.crawler.model.crawl.WorkerModel;

/**
 * @author DienNM (DEE)
 */

public class WorkerContext implements Serializable{
    
    private static final long serialVersionUID = 1L;
    
    private ExecutorService executorService;
    
    private WorkerModel worker;
    
    private Map<Level, WorkerItemModel> workerItems = new HashMap<Level, WorkerItemModel>();
    
    private ProxyTracer proxyTracer;
    
    public WorkerContext(WorkerModel worker) {
        this.worker = worker;
        for(WorkerItemModel workerItem : worker.getWorkerItems()) {
            workerItems.put(workerItem.getLevel(), workerItem);
        }
        executorService = Executors.newFixedThreadPool(worker.getThreads());
    }
    
    public void destroyWorker() {
        if(executorService == null || executorService.isShutdown()) {
            return;
        }
        executorService.shutdown();
    }
    
    public WorkerItemModel nextWorkerItem(WorkerItemModel workerItemModel) {
        Level nextLevel = Level.goNextLevel(workerItemModel.getLevel());
        if(nextLevel == null) {
            return null;
        }
        
        return workerItems.get(nextLevel);
    }

    public ExecutorService getExecutorService() {
        return executorService;
    }

    public void setExecutorService(ExecutorService executorService) {
        this.executorService = executorService;
    }

    public WorkerModel getWorker() {
        return worker;
    }

    public void setWorker(WorkerModel worker) {
        this.worker = worker;
    }

    public ProxyTracer getProxyTracer() {
        return proxyTracer;
    }

    public void setProxyTracer(ProxyTracer proxyTracer) {
        this.proxyTracer = proxyTracer;
    }
    
    public Map<Level, WorkerItemModel> getWorkerItems() {
        return workerItems;
    }
    
    public void setWorkerItems(Map<Level, WorkerItemModel> workerItems) {
        this.workerItems = workerItems;
    }
    
}
