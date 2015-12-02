package com.myprj.crawler.service.crawl.impl.site;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.myprj.crawler.service.crawl.WorkerService;
import com.myprj.crawler.service.crawl.impl.AbstractCrawlerService;

/**
 * @author DienNM (DEE)
 */

@Service("choTotCrawler")
public class ChoTotCrawler extends AbstractCrawlerService{
    
    @Autowired
    @Qualifier("chototWorker")
    private WorkerService workerService;
    
    @Override
    protected WorkerService getWorkerService() {
        return workerService;
    }

}
