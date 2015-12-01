package com.myprj.crawler.repository.impl;

import org.springframework.stereotype.Repository;

import com.myprj.crawler.model.crawl.WorkerItemModel;
import com.myprj.crawler.repository.WorkerItemRepository;

/**
 * @author DienNM (DEE)
 */
@Repository
public class DefaultWorkerItemRepository extends DefaultGenericDao<WorkerItemModel, Long> implements WorkerItemRepository {

    @Override
    protected Class<WorkerItemModel> getTargetClass() {
        return WorkerItemModel.class;
    }

}
