package com.myprj.crawler.service;

import com.myprj.crawler.domain.config.ItemAttributeData;
import com.myprj.crawler.domain.crawl.WorkerItemData;

/**
 * @author DienNM (DEE)
 */

public interface ItemAttributeStructureService {
    
    ItemAttributeData build(WorkerItemData workerItem, String jsonText);
    
}
