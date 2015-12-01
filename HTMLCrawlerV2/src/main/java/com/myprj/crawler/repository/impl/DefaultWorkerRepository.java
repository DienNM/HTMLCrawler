package com.myprj.crawler.repository.impl;

import org.springframework.stereotype.Repository;

import com.myprj.crawler.model.crawl.WorkerModel;
import com.myprj.crawler.repository.WorkerRepository;

/**
 * @author DienNM (DEE)
 */
@Repository
public class DefaultWorkerRepository extends DefaultGenericDao<WorkerModel, Long> implements WorkerRepository {

    @Override
    protected Class<WorkerModel> getTargetClass() {
        return WorkerModel.class;
    }

}
