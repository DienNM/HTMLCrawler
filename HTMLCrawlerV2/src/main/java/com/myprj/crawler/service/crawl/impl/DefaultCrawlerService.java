package com.myprj.crawler.service.crawl.impl;

import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.myprj.crawler.domain.WorkerContext;
import com.myprj.crawler.domain.worker.ErrorLink;
import com.myprj.crawler.enumeration.CrawlStatus;
import com.myprj.crawler.enumeration.WorkerStatus;
import com.myprj.crawler.exception.CrawlerException;
import com.myprj.crawler.exception.WorkerException;
import com.myprj.crawler.model.crawl.CrawlHistoryModel;
import com.myprj.crawler.model.crawl.WorkerItemModel;
import com.myprj.crawler.model.crawl.WorkerModel;
import com.myprj.crawler.repository.CrawlHistoryRepository;
import com.myprj.crawler.repository.WorkerRepository;
import com.myprj.crawler.service.crawl.CrawlerService;
import com.myprj.crawler.service.crawl.WorkerService;

/**
 * @author DienNM (DEE)
 */

public class DefaultCrawlerService implements CrawlerService {

    private Logger logger = LoggerFactory.getLogger(DefaultCrawlerService.class);

    private static Map<Long, WorkerContext> workerContextCache = new HashMap<Long, WorkerContext>();

    private WorkerService workerService;

    private CrawlHistoryRepository crawlHistoryRepository;
    
    private WorkerRepository workerRepository;

    @Override
    public void crawl(long workerId) throws CrawlerException {
        
        WorkerContext workerCtx = pickupWorkerContext(workerId);
        WorkerModel worker = workerCtx.getWorker();
        
        worker.setStatus(WorkerStatus.Running);
        workerRepository.update(worker);
        
        logger.info("Worker {} [Id={}] starts crawling...", worker.getName(), worker.getId());

        List<WorkerItemModel> workerItems = worker.getWorkerItems();
        Collections.sort(workerItems);

        CrawlHistoryModel crawlHistory = new CrawlHistoryModel();
        crawlHistory.setWorkerId(worker.getId());
        crawlHistory.setStatus(CrawlStatus.Running);
        crawlHistoryRepository.save(crawlHistory);

        long starttime = Calendar.getInstance().getTimeInMillis();
        try {
            workerService.doCrawl(workerCtx, workerItems.get(0));
            crawlHistory.setStatus(CrawlStatus.Done);
            worker.setStatus(WorkerStatus.Done);
        } catch (WorkerException e) {
            crawlHistory.setStatus(CrawlStatus.Error);
            worker.setStatus(WorkerStatus.Error);
            crawlHistory.setMessage(e.getMessage());
        } finally {
            destroy(worker);

            worker.setUpdatedAt(Calendar.getInstance().getTimeInMillis());
            workerRepository.update(worker);
            crawlHistoryRepository.save(crawlHistory);
            long endtime = Calendar.getInstance().getTimeInMillis();
            logger.info("Worker {} [Id={}] stops crawling. Took: {} second(s)", worker.getName(), worker.getId(),
                    (endtime - starttime) / 1000);
            
            logger.error("All Error Links: ");
            for(ErrorLink errorLink : workerCtx.getErrorLinks()) {
                logger.error(errorLink.toString());
            }
        }
    }
    
    private synchronized WorkerContext pickupWorkerContext(long workerId) throws CrawlerException{
        WorkerContext workerCtx = workerContextCache.get(workerId);
        if (workerCtx == null) {
            throw new CrawlerException(String.format("Worker ID=%s has not registered yet", workerId));
        }
        return workerCtx;
    }

    @Override
    public synchronized void init(WorkerModel worker) throws CrawlerException {
        WorkerContext workerCtx = workerContextCache.get(worker.getId());
        if (workerCtx == null || WorkerStatus.Created.equals(workerCtx.getWorker().getStatus())) {
            workerContextCache.put(worker.getId(), new WorkerContext(worker));
            return;
        }
        throw new CrawlerException("Worker " + worker.getId() + " is running");
    }

    @Override
    public synchronized void destroy(WorkerModel worker) {
        WorkerContext workerContext = workerContextCache.get(worker.getId());
        if (workerContext != null) {
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
    
    @Override
    public void setWorkerRepository(WorkerRepository workerRepository) {
        this.workerRepository = workerRepository;
    }

}
