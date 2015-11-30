package com.myprj.crawler.repository.impl;

import java.util.HashMap;
import java.util.Map;

import com.myprj.crawler.model.crawl.WorkerItemModel;
import com.myprj.crawler.repository.WorkerItemRepository;

/**
 * @author DienNM (DEE)
 */

public class DefaultWorkerItemRepository implements WorkerItemRepository {

    private static Map<Long, WorkerItemModel> repo = new HashMap<Long, WorkerItemModel>();
    
    @Override
    public WorkerItemModel save(WorkerItemModel workerItem) {
        repo.put(workerItem.getId(), workerItem);
        return workerItem;
    }

    @Override
    public WorkerItemModel find(long id) {
        return repo.get(id);
    }
}
