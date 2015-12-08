package com.myprj.crawler.service;

import java.util.List;

import com.myprj.crawler.domain.config.WorkerItemAttributeData;

/**
 * @author DienNM (DEE)
 */

public interface WorkerItemAttributeService {

    WorkerItemAttributeData get(String id);
    
    WorkerItemAttributeData getAndPopulate(String id);

    void populate(WorkerItemAttributeData attribute);

    void populateChildren(WorkerItemAttributeData attribute);

    void populateParent(WorkerItemAttributeData attribute);
    
    void populateAttribute(WorkerItemAttributeData attribute);

    List<WorkerItemAttributeData> getByWorkerItemId(long workerItemId);
    
    WorkerItemAttributeData getRootWithPopulateTree(long workerItemId);

    WorkerItemAttributeData save(WorkerItemAttributeData attribute);

    List<WorkerItemAttributeData> save(List<WorkerItemAttributeData> attributes);
}
