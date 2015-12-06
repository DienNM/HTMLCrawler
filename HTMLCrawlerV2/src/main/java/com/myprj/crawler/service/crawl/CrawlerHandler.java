package com.myprj.crawler.service.crawl;

import static java.util.Calendar.DAY_OF_MONTH;
import static java.util.Calendar.HOUR_OF_DAY;
import static java.util.Calendar.MILLISECOND;
import static java.util.Calendar.MINUTE;
import static java.util.Calendar.MONTH;
import static java.util.Calendar.SECOND;
import static java.util.Calendar.YEAR;

import java.security.InvalidParameterException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myprj.crawler.domain.RequestCrawl;
import com.myprj.crawler.domain.crawl.WorkerData;
import com.myprj.crawler.enumeration.WorkerStatus;
import com.myprj.crawler.exception.CrawlerException;
import com.myprj.crawler.service.WorkerService;

/**
 * @author DienNM (DEE)
 */
@Service
public class CrawlerHandler {

    private final static Logger logger = LoggerFactory.getLogger(CrawlerHandler.class);

    private static Map<String, CrawlerService> handlers = new HashMap<String, CrawlerService>();

    private static AtomicInteger tailNum = new AtomicInteger(0);

    private ExecutorService executorService;

    @Autowired
    private WorkerService workerService;

    @PostConstruct
    public void init() {
        executorService = Executors.newFixedThreadPool(3);
    }

    @PreDestroy
    public void destroy() {
        if (executorService != null && !executorService.isShutdown()) {
            executorService.shutdown();
        }
    }

    public void register(String type, CrawlerService crawlerService) {
        handlers.put(type, crawlerService);
    }

    public String handle(String type, final RequestCrawl request) {
        final CrawlerService crawlerService = handlers.get(type);
        if (crawlerService == null) {
            throw new UnsupportedOperationException("Crawler type " + type + " not support yet");
        }

        final WorkerData workerData = workerService.get(request.getWorkerId());
        try {
            crawlerService.init(workerData);
            String requestId = generateId();
            request.setRequestId(requestId);
            workerData.setRequestId(requestId);
            workerData.setStatus(WorkerStatus.Pending);
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        crawlerService.crawl(request);
                    } catch (CrawlerException e) {
                        logger.error("{}", e);
                    } finally {
                        crawlerService.destroy(workerData);
                    }
                }
            });
            workerService.update(workerData);
            return requestId;
        } catch (CrawlerException e1) {
            logger.error("{}", e1);
            throw new InvalidParameterException(e1.getMessage());
        }

    }

    public static String generateId() {
        Calendar calendar = Calendar.getInstance();
        String idTemplate = "%04d%02d%02d-%02d%02d%02d%03d-%03d";
        synchronized (tailNum) {
            if (tailNum.intValue() >= 999) {
                tailNum.set(0);
            }
            tailNum.addAndGet(1);
        }

        return String.format(idTemplate, calendar.get(YEAR), calendar.get(MONTH), calendar.get(DAY_OF_MONTH),
                calendar.get(HOUR_OF_DAY), calendar.get(MINUTE), calendar.get(SECOND), calendar.get(MILLISECOND),
                tailNum.intValue());
    }

}
