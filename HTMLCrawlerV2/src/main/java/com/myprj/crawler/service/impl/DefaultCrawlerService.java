package com.myprj.crawler.service.impl;

import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.myprj.crawler.domain.WorkerContext;
import com.myprj.crawler.enumeration.CrawlStatus;
import com.myprj.crawler.exception.CrawlerException;
import com.myprj.crawler.exception.WorkerException;
import com.myprj.crawler.model.crawl.CrawlHistoryModel;
import com.myprj.crawler.model.crawl.WorkerItemModel;
import com.myprj.crawler.model.crawl.WorkerModel;
import com.myprj.crawler.repository.CrawlHistoryRepository;
import com.myprj.crawler.service.CrawlerService;
import com.myprj.crawler.service.WorkerService;

/**
 * @author DienNM (DEE)
 */

public class DefaultCrawlerService implements CrawlerService {
    
    private Logger logger = LoggerFactory.getLogger(DefaultCrawlerService.class);
    
    private static Map<Long, WorkerContext> workerContextCache = new HashMap<Long, WorkerContext>();
    
    private WorkerService workerService;
    
    private CrawlHistoryRepository crawlHistoryRepository;
    
    @Override
    public void crawl(WorkerModel worker) throws CrawlerException {
        WorkerContext workerCtx = workerContextCache.get(worker.getId());
        if(workerCtx == null) {
            throw new CrawlerException(String.format("Worker %s [name = %s] has not registered yet", 
                    worker.getId(), worker.getName()));
        }
        
        logger.info("Worker {} [Id={}] starts crawling...", worker.getName(), worker.getId());
        
        List<WorkerItemModel> workerItems = worker.getWorkerItems();
        Collections.sort(workerItems);
        
        CrawlHistoryModel crawlHistory = new CrawlHistoryModel();
        crawlHistory.setWorkerId(worker.getId());
        crawlHistory.setStatus(CrawlStatus.Running);
        crawlHistoryRepository.save(crawlHistory);
        
        long starttime = Calendar.getInstance().getTimeInMillis();
        
        WorkerItemModel rootWorkerItem = workerItems.get(0);
        try {
            workerService.doWork(workerCtx, rootWorkerItem);
        } catch (WorkerException e) {
            crawlHistory = crawlHistoryRepository.findLatest(worker.getId());
            crawlHistory.setStatus(CrawlStatus.Error);
            crawlHistory.setMessage(e.getMessage());
            crawlHistoryRepository.save(crawlHistory);
        } finally {
            destroy(worker);
            long endtime = Calendar.getInstance().getTimeInMillis();
            logger.info("Worker {} [Id={}] stops crawling. Took: {} second(s)", worker.getName(), worker.getId(),
                    (endtime - starttime)/1000);
        }
    }

    @Override
    public void init(WorkerModel worker) {
        workerContextCache.put(worker.getId(), new WorkerContext(worker));
    }

    @Override
    public void destroy(WorkerModel worker) {
        WorkerContext workerContext = workerContextCache.get(worker.getId());
        if(workerContext != null) {
            workerContextCache.remove(worker.getId());
            workerContext.destroyWorker();
        }
    }

    public WorkerService getWorkerService() {
        return workerService;
    }

    public void setWorkerService(WorkerService workerService) {
        this.workerService = workerService;
    }

    @Override
    public void setCrawlHistoryRepository(CrawlHistoryRepository crawlHistoryRepository) {
        this.crawlHistoryRepository = crawlHistoryRepository;
    }

}
