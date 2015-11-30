package com.myprj.crawler.service.impl;

import static com.myprj.crawler.util.Serialization.deserialize;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.myprj.crawler.domain.HtmlDocument;
import com.myprj.crawler.domain.WorkerContext;
import com.myprj.crawler.domain.worker.ListWorkerTargetParameter;
import com.myprj.crawler.domain.worker.WorkerItemConfig;
import com.myprj.crawler.enumeration.AttributeType;
import com.myprj.crawler.exception.WorkerException;
import com.myprj.crawler.model.config.AttributeModel;
import com.myprj.crawler.model.crawl.CrawlResultModel;
import com.myprj.crawler.model.crawl.WorkerItemModel;
import com.myprj.crawler.model.crawl.WorkerModel;
import com.myprj.crawler.service.WorkerService;
import com.myprj.crawler.service.cache.AttributeCacheService;
import com.myprj.crawler.service.event.CrawlEventPublisher;
import com.myprj.crawler.service.event.impl.CrawlCompletedEvent;
import com.myprj.crawler.service.handler.HandlerRegister;
import com.myprj.crawler.util.HtmlDownloader;
import com.myprj.crawler.util.Serialization;

/**
 * @author DienNM (DEE)
 */

public class DefaultWorkerService implements WorkerService {

    private final Logger logger = LoggerFactory.getLogger(DefaultWorkerService.class);

    private AttributeCacheService attributeCacheService;
    
    private CrawlEventPublisher crawlEventPublisher;

    @Override
    public void doWork(WorkerContext workerCtx, WorkerItemModel workerItem) throws WorkerException {
        // Pool here
        switch (workerItem.getTargetType()) {
        case LIST:
            doWorkOnWorkerItemList(workerCtx, workerItem);
            break;
        case DETAIL:
            doWorkOnWorkerItemDetail(workerCtx, workerItem);
            break;
        default:
            throw new WorkerException("Worker Item target has not determined");
        }
    }

    @SuppressWarnings("unchecked")
    protected void doWorkOnWorkerItemList(WorkerContext workerCtx, WorkerItemModel workerItem) throws WorkerException {
        ListWorkerTargetParameter workerTarget = Serialization.deserialize(workerItem.getWorkerItemPagingConfig(),
                ListWorkerTargetParameter.class);

        WorkerModel worker = workerCtx.getWorker();
        String url = workerItem.getUrl();

        for (int currentPage = workerTarget.getCurrentPage(); currentPage <= workerTarget.getToPage(); currentPage++) {
            workerTarget.setCurrentPage(currentPage);
            String finalLink = String.format(url, currentPage);
            logger.info("Start crawling: {}", finalLink);

            finalLink = updateUrlBeforeCrawling(finalLink);
            Document document = HtmlDownloader.download(url, worker.getDelayTime(), worker.getAttemptTimes());
            if (isDownloadSuccess(document)) {
                Map<String, String> cssSelectors = Serialization.deserialize(workerItem.getAttributeCssSelectors(),
                        Map.class);
                String cssSelectLink = cssSelectors.get(AttributeType.LINK.name());
                Elements div = document.body().select(cssSelectLink);
                if (div == null) {
                    logger.error("Empty list from: " + cssSelectLink);
                    return;
                }
                Iterator<Element> elements = div.iterator();
                List<Future<Object>> futureItems = new ArrayList<Future<Object>>();
                while (elements.hasNext()) {
                    Element element = elements.next();
                    String nextLevelLink = element.attr(workerTarget.getUrlAttribute());
                    if (!isValidLinkOfListTargetItem(nextLevelLink)) {
                        continue;
                    }
                    WorkerItemModel nextWorkItems = workerCtx.nextWorkerItem(workerItem);
                    if (nextWorkItems == null) {
                        logger.error("Cannot go to the next level of " + workerItem.getId());
                        continue;
                    }
                    
                    WorkerItemModel newWorkerItem = createNewWorkerItem(nextWorkItems);
                    newWorkerItem.setUrl(nextLevelLink);
                    
                    futureItems.add(workerCtx.getExecutorService().submit(new CrawlerJob(workerCtx, newWorkerItem)));
                }
                for (Future<Object> fitem : futureItems) {
                    try {
                        fitem.get();
                    } catch (Exception e) {
                        logger.error("Error: {}", e);
                    }
                }
            }

        }
    }
    
    class CrawlerJob implements Callable<Object> {

        private WorkerContext workerCtx;
        private WorkerItemModel workerItem;
        
        public CrawlerJob(WorkerContext workerCtx, WorkerItemModel workerItem) {
            this.workerCtx = workerCtx;
            this.workerItem = workerItem;
        }

        @Override
        public Object call() throws Exception {
            doWork(workerCtx, workerItem);
            return null;
        }
    }

    protected void doWorkOnWorkerItemDetail(WorkerContext workerCtx, WorkerItemModel workerItem) throws WorkerException {
        WorkerModel worker = workerCtx.getWorker();
        String url = workerItem.getUrl();
        
        if (url == null) {
            logger.error("Cannot crawl a worker without URL: " + workerItem.getId());
            return;
        }
        url = updateUrlBeforeCrawling(url);
        CrawlResultModel crawlResult = new CrawlResultModel();
        crawlResult.setUrl(url);

        Document document = HtmlDownloader.download(url, worker.getDelayTime(), worker.getAttemptTimes());
        if (isDownloadSuccess(document)) {

            HtmlDocument htmlDocument = new HtmlDocument(document);
            WorkerItemConfig workerItemConfig = deserialize(workerItem.getAttributeCssSelectors(), WorkerItemConfig.class);

            crawlResult.setCategoryId(workerItemConfig.getCategoryId());
            crawlResult.setItemId(workerItemConfig.getItemId());

            Map<Long, String> attributesCssSelectors = workerItemConfig.getAttributesCssSelectors();
            Map<Long, AttributeModel> allAttributes = attributeCacheService.findAll(workerItemConfig.getItemId());

            for (Long attId : attributesCssSelectors.keySet()) {
                AttributeModel attribute = allAttributes.get(attId);
                if (attribute == null) {
                    continue;
                }
                String cssSelector = attributesCssSelectors.get(attId);
                Object data = HandlerRegister.getHandler(attribute.getType()).handle(htmlDocument, cssSelector);

                crawlResult.getDetail().put(attribute.getId(), data);
            }
        }
        
        CrawlCompletedEvent crawlCompletedEvent = new CrawlCompletedEvent(crawlResult);
        crawlEventPublisher.publish(crawlCompletedEvent);
    }
    
    private WorkerItemModel createNewWorkerItem(WorkerItemModel current) {
        WorkerItemModel newItem = new WorkerItemModel();
        newItem.setId(current.getId());
        newItem.setAttributeCssSelectors(current.getAttributeCssSelectors());
        newItem.setLevel(current.getLevel());
        newItem.setNextUrl(current.getNextUrl());
        newItem.setTargetType(current.getTargetType());
        newItem.setUrl(current.getUrl());
        newItem.setWorkerItemPagingConfig(current.getWorkerItemPagingConfig());
        return newItem;
    }

    protected boolean isValidLinkOfListTargetItem(String url) {
        return url != null && url.startsWith("http://");
    }

    protected String updateUrlBeforeCrawling(String url) {
        return url;
    }

    protected boolean isDownloadSuccess(Document document) {
        return document != null;
    }

    @Override
    public void setAttributeCacheService(AttributeCacheService attributeCacheService) {
        this.attributeCacheService = attributeCacheService;
    }

    @Override
    public void setCrawlEventPublisher(CrawlEventPublisher crawlEventPublisher) {
        this.crawlEventPublisher = crawlEventPublisher;
    }
    
    public AttributeCacheService getAttributeCacheService() {
        return attributeCacheService;
    }

}
