package com.myprj.crawler.service.crawl.impl;

import static com.myprj.crawler.util.Serialization.serialize;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myprj.crawler.domain.crawl.WorkerContext;
import com.myprj.crawler.domain.crawl.WorkerData;
import com.myprj.crawler.enumeration.CrawlStatus;
import com.myprj.crawler.enumeration.WorkerStatus;
import com.myprj.crawler.exception.CrawlerException;
import com.myprj.crawler.exception.WorkerException;
import com.myprj.crawler.model.crawl.CrawlHistoryModel;
import com.myprj.crawler.repository.CrawlHistoryRepository;
import com.myprj.crawler.service.crawl.CrawlerService;
import com.myprj.crawler.service.crawl.WorkerService;

/**
 * @author DienNM (DEE)
 */
@Service("defaultCrawlerService")
public class DefaultCrawlerService implements CrawlerService {

    private Logger logger = LoggerFactory.getLogger(DefaultCrawlerService.class);

    private static Map<Long, WorkerContext> workerContextCache = new HashMap<Long, WorkerContext>();

    @Autowired
    private WorkerService workerService;

    @Autowired
    private CrawlHistoryRepository crawlHistoryRepository;

    @Override
    public void crawl(long workerId) throws CrawlerException {
        WorkerContext workerCtx = pickupWorkerContext(workerId);
        WorkerData worker = workerCtx.getWorker();
        worker.setStatus(WorkerStatus.Running);
        workerService.update(worker);

        logger.info("Worker {} [Id={}] starts crawling...", worker.getName(), worker.getId());

        CrawlHistoryModel crawlHistory = new CrawlHistoryModel();
        crawlHistory.setWorkerId(worker.getId());
        crawlHistory.setStatus(CrawlStatus.Running);
        crawlHistoryRepository.save(crawlHistory);

        long starttime = Calendar.getInstance().getTimeInMillis();
        try {
            workerService.invoke(workerCtx, workerCtx.getRootWorkerItem());
            crawlHistory.setStatus(CrawlStatus.Done);
            worker.setStatus(WorkerStatus.Done);
        } catch (WorkerException e) {
            crawlHistory.setStatus(CrawlStatus.Error);
            worker.setStatus(WorkerStatus.Error);
            crawlHistory.setMessage(e.getMessage());
        } finally {
            long took = (Calendar.getInstance().getTimeInMillis() - starttime) / 1000;
            crawlHistory.setTimeTaken(took);
            crawlHistory.setErrorLinks(serialize(workerCtx.getErrorLinks()));

            workerService.update(worker);
            crawlHistoryRepository.save(crawlHistory);

            destroy(worker);
            logger.info("Worker {} [Id={}] stops crawling. Took: {} second(s)", worker.getName(), worker.getId(), took);
        }
    }

    private synchronized WorkerContext pickupWorkerContext(long workerId) throws CrawlerException {
        WorkerContext workerCtx = workerContextCache.get(workerId);
        if (workerCtx == null) {
            throw new CrawlerException(String.format("Worker ID=%s has not registered yet", workerId));
        }
        return workerCtx;
    }

    @Override
    public synchronized void init(WorkerData worker) throws CrawlerException {
        WorkerContext workerCtx = workerContextCache.get(worker.getId());
        if (workerCtx == null || WorkerStatus.Created.equals(workerCtx.getWorker().getStatus())) {
            workerContextCache.put(worker.getId(), new WorkerContext(worker));
            return;
        }
        throw new CrawlerException("Worker " + worker.getId() + " is running");
    }

    @Override
    public synchronized void destroy(WorkerData worker) {
        WorkerContext workerContext = workerContextCache.get(worker.getId());
        if (workerContext != null) {
            workerContextCache.remove(worker.getId());
            workerContext.destroyWorker();
        }
    }

}
