package com.myprj.crawler.domain.crawl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.myprj.crawler.domain.ErrorLink;
import com.myprj.crawler.domain.ProxyTracer;
import com.myprj.crawler.enumeration.Level;

/**
 * @author DienNM (DEE)
 */

public class WorkerContext implements Serializable {

    private static final long serialVersionUID = 1L;

    private ExecutorService executorService;

    private WorkerData worker;

    private Map<Level, WorkerItemData> workerItems = new HashMap<Level, WorkerItemData>();

    private List<ErrorLink> errorLinks = new ArrayList<ErrorLink>();

    private ProxyTracer proxyTracer;

    public WorkerContext(WorkerData worker) {
        this.worker = worker;
        for (WorkerItemData workerItem : worker.getWorkerItems()) {
            workerItems.put(workerItem.getLevel(), workerItem);
        }
        executorService = Executors.newFixedThreadPool(worker.getThreads());
    }

    public void destroyWorker() {
        if (executorService == null || executorService.isShutdown()) {
            return;
        }
        executorService.shutdown();
    }
    
    public WorkerItemData getRootWorkerItem() {
        return workerItems.get(Level.Level0);
    }

    public WorkerItemData nextWorkerItem(WorkerItemData workerItemData) {
        Level nextLevel = Level.goNextLevel(workerItemData.getLevel());
        if (nextLevel == null) {
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

    public WorkerData getWorker() {
        return worker;
    }

    public void setWorker(WorkerData worker) {
        this.worker = worker;
    }

    public ProxyTracer getProxyTracer() {
        return proxyTracer;
    }

    public void setProxyTracer(ProxyTracer proxyTracer) {
        this.proxyTracer = proxyTracer;
    }

    public Map<Level, WorkerItemData> getWorkerItems() {
        return workerItems;
    }

    public void setWorkerItems(Map<Level, WorkerItemData> workerItems) {
        this.workerItems = workerItems;
    }

    public List<ErrorLink> getErrorLinks() {
        return errorLinks;
    }

    public void setErrorLinks(List<ErrorLink> errorLinks) {
        this.errorLinks = errorLinks;
    }

}
