package com.myprj.crawler.service.crawl;

import static java.util.Calendar.DAY_OF_MONTH;
import static java.util.Calendar.HOUR_OF_DAY;
import static java.util.Calendar.MILLISECOND;
import static java.util.Calendar.MINUTE;
import static java.util.Calendar.MONTH;
import static java.util.Calendar.SECOND;
import static java.util.Calendar.YEAR;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.stereotype.Service;

import com.myprj.crawler.domain.RequestCrawl;
import com.myprj.crawler.exception.CrawlerException;

/**
 * @author DienNM (DEE)
 */
@Service
public class CrawlerHandler {

    private static Map<String, CrawlerService> handlers = new HashMap<String, CrawlerService>();

    private static AtomicInteger tailNum = new AtomicInteger(0);

    private ExecutorService executorService;
    
    @PostConstruct
    public void init() {
        executorService = Executors.newFixedThreadPool(3);
    }
    
    @PreDestroy
    public void destroy() {
        if(executorService != null && !executorService.isShutdown()) {
            executorService.shutdown();
        }
    }
    
    public void register(String type, CrawlerService crawlerService) {
        handlers.put(type, crawlerService);
    }
    
    public String handle(String type, RequestCrawl request) {
        CrawlerService crawlerService = handlers.get(type);
        if (crawlerService == null) {
            throw new UnsupportedOperationException("Crawler type " + type + " not support yet");
        }
        try {
            String requestId = generateId();
            request.setRequestId(requestId);
            crawlerService.crawl(request);
            return requestId;
        } catch (CrawlerException e) {
            return null;
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

        return String.format(idTemplate, calendar.get(YEAR), 
                calendar.get(MONTH), 
                calendar.get(DAY_OF_MONTH),
                calendar.get(HOUR_OF_DAY), 
                calendar.get(MINUTE), 
                calendar.get(SECOND), 
                calendar.get(MILLISECOND),
                tailNum.intValue());
    }

}
