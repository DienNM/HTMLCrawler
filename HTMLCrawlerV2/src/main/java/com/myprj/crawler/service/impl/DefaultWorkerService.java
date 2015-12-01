package com.myprj.crawler.service.impl;

import static com.myprj.crawler.enumeration.Level.Level0;
import static com.myprj.crawler.util.Serialization.deserialize;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.myprj.crawler.domain.HtmlDocument;
import com.myprj.crawler.domain.WorkerContext;
import com.myprj.crawler.domain.worker.CssSelector;
import com.myprj.crawler.domain.worker.ErrorLink;
import com.myprj.crawler.domain.worker.ListWorkerTargetParameter;
import com.myprj.crawler.domain.worker.WorkerItemConfig;
import com.myprj.crawler.enumeration.ResultStatus;
import com.myprj.crawler.enumeration.WorkerItemTargetType;
import com.myprj.crawler.exception.WorkerException;
import com.myprj.crawler.model.config.AttributeModel;
import com.myprj.crawler.model.crawl.CrawlResultModel;
import com.myprj.crawler.model.crawl.WorkerItemModel;
import com.myprj.crawler.model.crawl.WorkerModel;
import com.myprj.crawler.service.WorkerService;
import com.myprj.crawler.service.cache.AttributeCacheService;
import com.myprj.crawler.service.event.CrawlEventPublisher;
import com.myprj.crawler.service.event.impl.CrawlDetailCompletedEvent;
import com.myprj.crawler.service.handler.HandlerRegister;
import com.myprj.crawler.util.HtmlDownloader;

/**
 * @author DienNM (DEE)
 */

public abstract class DefaultWorkerService implements WorkerService {

    private final Logger logger = LoggerFactory.getLogger(DefaultWorkerService.class);

    private AttributeCacheService attributeCacheService;

    private CrawlEventPublisher crawlEventPublisher;
    
    @Override
    public void doCrawl(WorkerContext workerCtx, WorkerItemModel workerItem) throws WorkerException {
        switch (workerItem.getTargetType()) {
        case LIST:
            doWorkOnWorkerItemList(workerCtx, workerItem);
            break;
        case NAVIGATION:
            doWorkOnWorkerItemList(workerCtx, workerItem);
            break;
        case DETAIL:
            doWorkOnWorkerItemDetail(workerCtx, workerItem);
            break;
        default:
            throw new WorkerException("Worker Item target has not determined");
        }
    }
    
    protected abstract void doWorkOnWorkerItemList(WorkerContext workerCtx, WorkerItemModel workerItem) throws WorkerException;
    
    protected void processListItemsPerPage(WorkerContext workerCtx, WorkerItemModel workerItem,
            ListWorkerTargetParameter workerTarget, Elements elements, CssSelector cssSelector) throws WorkerException {
        List<Future<Object>> futureItems = new ArrayList<Future<Object>>();
        for (Element element : elements) {
            String nextLevelLink = null;
            if(StringUtils.isEmpty(cssSelector.getTargetAttribute())) {
                nextLevelLink = element.attr("href");
            } else {
                nextLevelLink = element.attr(cssSelector.getTargetAttribute());
            }
            
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
            
            if(Level0.equals(workerItem.getLevel())) {
                futureItems.add(workerCtx.getExecutorService().submit(new CrawlerJob(workerCtx, newWorkerItem)));
            } else {
                doCrawl(workerCtx, workerItem);
            }
        }
        waitForCompleteThreads(futureItems);
    }

    private void waitForCompleteThreads(List<Future<Object>> futureItems) {
        for (Future<Object> fitem : futureItems) {
            try {
                fitem.get();
            } catch (Exception e) {
                logger.error("Error: {}", e);
            }
        }
    }
    
    protected void doWorkOnWorkerItemDetail(WorkerContext workerCtx, WorkerItemModel workerItem) throws WorkerException {
        WorkerModel worker = workerCtx.getWorker();
        String url = updateUrlBeforeCrawling(workerItem.getUrl());

        Document document = HtmlDownloader.download(url, worker.getDelayTime(), worker.getAttemptTimes());
        if (!isDownloadSuccess(document)) {
            String errorMessage = "Cannot download HTML from " + url;
            ErrorLink errorLink = new ErrorLink(url);
            errorLink.setMessage(errorMessage);
            errorLink.setLevel(workerItem.getLevel());
            errorLink.setTargetType(WorkerItemTargetType.DETAIL);
            workerCtx.getErrorLinks().add(errorLink);
            logger.warn(errorMessage);
        }
        HtmlDocument htmlDocument = new HtmlDocument(document);
        collectionDetail(url, htmlDocument, workerItem);
    }
    
    protected void collectionDetail(String url, HtmlDocument htmlDocument, WorkerItemModel workerItem) {
        WorkerItemConfig workerItemConfig = deserialize(workerItem.getcssSelectors(), WorkerItemConfig.class);

        CrawlResultModel crawlResult = new CrawlResultModel();
        crawlResult.setUrl(url);
        
        crawlResult.setCategoryId(workerItemConfig.getCategoryId());
        crawlResult.setItemId(workerItemConfig.getItemId());

        Map<Long, String> attributesCssSelectors = workerItemConfig.getAttributesCssSelectors();
        Map<Long, AttributeModel> allAttributes = attributeCacheService.findAll(workerItemConfig.getItemId());

        for (Long attId : attributesCssSelectors.keySet()) {
            AttributeModel attribute = allAttributes.get(attId);
            if (attribute == null) {
                continue;
            }
            String cssText = attributesCssSelectors.get(attId);
            CssSelector cssSelector = new CssSelector(cssText);
            Object data = HandlerRegister.getHandler(attribute.getType()).handle(htmlDocument, cssSelector);
            if (data == null) {
                logger.warn("No Data. Attribute: {}, CSS-Selector: {}, URL: {}", attribute.getName(), cssText, url);
            }
            crawlResult.getDetail().put(attribute.getName(), data);
        }
        if(crawlResult.getDetail().isEmpty()) {
            crawlResult.setStatus(ResultStatus.MISSING);
        } else {
            crawlResult.setStatus(ResultStatus.COMPLETED);
        }
        crawlEventPublisher.publish(new CrawlDetailCompletedEvent(crawlResult));
    }

    protected WorkerItemModel createNewWorkerItem(WorkerItemModel current) {
        WorkerItemModel newItem = new WorkerItemModel();
        newItem.setId(current.getId());
        newItem.setCssSelectors(current.getcssSelectors());
        newItem.setLevel(current.getLevel());
        newItem.setNextUrl(current.getNextUrl());
        newItem.setTargetType(current.getTargetType());
        newItem.setUrl(current.getUrl());
        newItem.setPagingConfig(current.getPagingConfig());
        return newItem;
    }
    
    protected String updateUrlFormat(String url) {
        return url;
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

    class CrawlerJob implements Callable<Object> {

        private WorkerContext workerCtx;
        private WorkerItemModel workerItem;

        public CrawlerJob(WorkerContext workerCtx, WorkerItemModel workerItem) {
            this.workerCtx = workerCtx;
            this.workerItem = workerItem;
        }

        @Override
        public Object call() throws Exception {
            try {
                doCrawl(workerCtx, workerItem);
            } catch (Exception e) {
                String errorMessage = String.format("Error: URL=%s, Message=%s", workerItem.getUrl(), e.getMessage());
                ErrorLink errorLink = new ErrorLink(workerItem.getUrl());
                errorLink.setMessage(errorMessage);
                errorLink.setLevel(workerItem.getLevel());
                errorLink.setTargetType(workerItem.getTargetType());
                workerCtx.getErrorLinks().add(errorLink);
                logger.warn(errorMessage);
            }
            return null;
        }
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
