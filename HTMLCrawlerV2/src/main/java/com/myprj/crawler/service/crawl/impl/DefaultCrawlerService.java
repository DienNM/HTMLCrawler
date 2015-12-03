package com.myprj.crawler.service.crawl.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.myprj.crawler.service.crawl.WorkerService;

/**
 * @author DienNM (DEE)
 */
@Service("defaultCrawlerService")
public class DefaultCrawlerService extends AbstractCrawlerService {

    @Autowired
    @Qualifier("defaultWorkerService")
    private WorkerService workerService;

    @Override
    protected WorkerService getWorkerService() {
        return workerService;
    }

}