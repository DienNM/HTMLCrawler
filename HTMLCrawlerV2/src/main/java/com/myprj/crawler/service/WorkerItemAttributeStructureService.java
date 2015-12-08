package com.myprj.crawler.service;

import com.myprj.crawler.domain.config.WorkerItemAttributeData;
import com.myprj.crawler.domain.crawl.WorkerItemData;

/**
 * @author DienNM (DEE)
 */

public interface WorkerItemAttributeStructureService {
    
    WorkerItemAttributeData build(WorkerItemData workerItem, String jsonText);
    
}
