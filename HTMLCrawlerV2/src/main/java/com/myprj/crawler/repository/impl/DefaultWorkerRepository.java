package com.myprj.crawler.repository.impl;

import java.util.HashMap;
import java.util.Map;

import com.myprj.crawler.model.crawl.WorkerModel;
import com.myprj.crawler.repository.WorkerRepository;

/**
 * @author DienNM (DEE)
 */

public class DefaultWorkerRepository implements WorkerRepository {

    private static Map<Long, WorkerModel> repo = new HashMap<Long, WorkerModel>();

    @Override
    public WorkerModel save(WorkerModel worker) {
        repo.put(worker.getId(), worker);
        return worker;
    }

    @Override
    public WorkerModel find(long id) {
        return repo.get(id);
    }

}
