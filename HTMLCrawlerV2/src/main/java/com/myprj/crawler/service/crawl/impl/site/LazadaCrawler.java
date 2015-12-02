package com.myprj.crawler.service.crawl.impl.site;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.myprj.crawler.service.crawl.WorkerService;
import com.myprj.crawler.service.crawl.impl.AbstractCrawlerService;

/**
 * @author DienNM (DEE)
 */
@Service("lazadaCrawler")
public class LazadaCrawler extends AbstractCrawlerService {
    
    @Autowired
    @Qualifier("lazadaWorker")
    private WorkerService workerService;

    @Override
    public WorkerService getWorkerService() {
        return workerService;
    }
    
}
