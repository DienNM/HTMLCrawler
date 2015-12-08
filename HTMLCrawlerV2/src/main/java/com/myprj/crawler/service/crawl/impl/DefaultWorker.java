package com.myprj.crawler.service.crawl.impl;

import static com.myprj.crawler.enumeration.AttributeType.LIST_OBJECT;
import static com.myprj.crawler.enumeration.Level.Level0;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myprj.crawler.domain.ErrorLink;
import com.myprj.crawler.domain.HtmlDocument;
import com.myprj.crawler.domain.config.AttributeSelector;
import com.myprj.crawler.domain.config.WorkerItemAttributeData;
import com.myprj.crawler.domain.config.ItemContent;
import com.myprj.crawler.domain.config.ItemData;
import com.myprj.crawler.domain.crawl.CrawlResultData;
import com.myprj.crawler.domain.crawl.PagingConfig;
import com.myprj.crawler.domain.crawl.WorkerContext;
import com.myprj.crawler.domain.crawl.WorkerData;
import com.myprj.crawler.domain.crawl.WorkerItemData;
import com.myprj.crawler.enumeration.ResultStatus;
import com.myprj.crawler.exception.WorkerException;
import com.myprj.crawler.service.crawl.Worker;
import com.myprj.crawler.service.event.CrawlEventPublisher;
import com.myprj.crawler.service.event.impl.CrawlDetailCompletedEvent;
import com.myprj.crawler.service.handler.HandlerRegister;
import com.myprj.crawler.util.HtmlDownloader;
import com.myprj.crawler.util.AttributeStructureUtil;
import com.myprj.crawler.util.converter.DomainCopy;

/**
 * @author DienNM (DEE)
 */
@Service("defaultWorker")
public class DefaultWorker implements Worker {

    private final Logger logger = LoggerFactory.getLogger(DefaultWorker.class);

    @Autowired
    protected CrawlEventPublisher crawlEventPublisher;

    @Override
    public void invoke(WorkerContext workerCtx, WorkerItemData workerItem) throws WorkerException {
        switch (workerItem.getCrawlType()) {
        case LIST:
            invokeWorkerList(workerCtx, workerItem);
            break;
        case DETAIL:
            invokeWorkerDetail(workerCtx, workerItem);
            break;
        default:
            throw new WorkerException("Worker Item target has not determined");
        }
    }

    protected void invokeWorkerDetail(WorkerContext workerCtx, WorkerItemData workerItem) throws WorkerException {
        WorkerData worker = workerCtx.getWorker();
        String url = getURL(workerItem.getUrl());
        Document document = HtmlDownloader.download(url, worker.getDelayTime(), worker.getAttemptTimes());
        if (!isDownloadSuccess(document)) {
            String errorMessage = "Cannot download HTML from " + url;
            ErrorLink errorLink = new ErrorLink(url);
            errorLink.setMessage(errorMessage);
            errorLink.setLevel(workerItem.getLevel());
            errorLink.setCrawlType(workerItem.getCrawlType());
            workerCtx.getErrorLinks().add(errorLink);
            logger.warn(errorMessage);
        }
        HtmlDocument htmlDocument = new HtmlDocument(document);
        processWorkerDetail(url, htmlDocument, workerItem, worker);
    }

    protected void invokeWorkerList(WorkerContext workerCtx, WorkerItemData workerItem) throws WorkerException {
        List<String> urls = getURLs(workerItem.getPagingConfig(), workerItem.getUrl());
        for (String url : urls) {
            logger.info("Start crawling: {}", url);
            WorkerData worker = workerCtx.getWorker();
            Document document = HtmlDownloader.download(url, worker.getDelayTime(), worker.getAttemptTimes());
            if (!isDownloadSuccess(document)) {
                String errorMessage = "Cannot download HTML from " + url;
                ErrorLink error = new ErrorLink(url);
                error.setMessage(errorMessage);
                error.setLevel(workerItem.getLevel());
                error.setCrawlType(workerItem.getCrawlType());
                workerCtx.getErrorLinks().add(error);
                logger.warn(errorMessage);
                continue;
            }
            AttributeSelector linkSelector = workerItem.getLevel0Selector();
            Elements elements = document.body().select(linkSelector.getSelector());
            if (elements == null) {
                logger.warn("No Data. CSS-Selector={}, URL={}", linkSelector, url);
                continue;
            }
            processListItemsPerPage(workerCtx, workerItem, elements);
            logger.info("End crawling: {}", url);
        }
    }

    protected List<String> getURLs(PagingConfig workerTarget, String url) {
        int fromPage = Integer.valueOf(workerTarget.getStart());
        int toPage = Integer.valueOf(workerTarget.getEnd());
        List<String> urls = new ArrayList<String>();
        String updatedURLPagingFormat = updateURLPagingFormat(url);
        for (int page = fromPage; page <= toPage; page++) {
            String formatedURLPaging = formatURLPaging(updatedURLPagingFormat, String.valueOf(page));
            urls.add(formatedURLPaging);
        }
        return urls;
    }

    protected void processListItemsPerPage(WorkerContext workerCtx, WorkerItemData workerItem, Elements elements)
            throws WorkerException {
        AttributeSelector linkSelector = workerItem.getLevel0Selector();
        List<Future<Object>> futureItems = new ArrayList<Future<Object>>();
        for (Element element : elements) {
            String nextLevelLink = null;
            String attribute = "href";
            if (!StringUtils.isEmpty(linkSelector.getTargetAttribute())) {
                attribute = linkSelector.getTargetAttribute();
            }
            nextLevelLink = element.attr(attribute);
            if (!isValidLink(nextLevelLink)) {
                logger.warn("Invalid Link: {}. Level={}, CSS-Selector={}", nextLevelLink, workerItem.getLevel(),
                        linkSelector.getText());
                continue;
            }
            WorkerItemData nextWorkerItem = workerCtx.nextWorkerItem(workerItem);
            if (nextWorkerItem == null) {
                logger.warn("Worker Item: {} [Level={}] cannot go next level. Link={}", workerItem.getId(),
                        workerItem.getLevel(), nextLevelLink);
                continue;
            }

            WorkerItemData newItem = new WorkerItemData();
            DomainCopy.copy(nextWorkerItem, newItem);
            
            newItem.setUrl(nextLevelLink);

            if (Level0.equals(workerItem.getLevel())) {
                futureItems.add(workerCtx.getExecutorService().submit(new CrawlerJob(workerCtx, newItem)));
            } else {
                invoke(workerCtx, workerItem);
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

    protected void processWorkerDetail(String url, HtmlDocument htmlDocument, WorkerItemData workerItem, WorkerData worker) {
        ItemData item = workerItem.getItem();
        WorkerItemAttributeData rootItemAttribute = workerItem.getRootWorkerItemAttribute();

        CrawlResultData result = new CrawlResultData();
        result.setUrl(url);
        result.setSite(worker.getSite());
        result.setItemKey(item.getKey());
        result.setCategoryKey(item.getCategoryData().getKey());
        result.setRequestId(workerItem.getRequestId());
        result.getDetail().putAll(ItemContent.clone(item.getSampleContent()));
        
        collectResult4Attribute(htmlDocument, rootItemAttribute, result);

        if (result.getDetail().isEmpty()) {
            result.setStatus(ResultStatus.MISSING);
        } else {
            result.setStatus(ResultStatus.COMPLETED);
        }
        crawlEventPublisher.publish(new CrawlDetailCompletedEvent(result));
    }

    protected void collectResult4Attribute(HtmlDocument htmlDocument, WorkerItemAttributeData current, CrawlResultData result) {
        AttributeSelector selector = current.getSelector();
        Object data = null;
        if(LIST_OBJECT.equals(current.getType())) {
            data = HandlerRegister.getHandler(current.getType()).handle(htmlDocument, current);
            AttributeStructureUtil.populateValue2Attribute(result.getDetail(), current.getAttributeId(), data);
            return;
        } else if(selector != null) {
            data = HandlerRegister.getHandler(current.getType()).handle(htmlDocument, current);
        }
        
        if (data == null && selector != null) {
            logger.warn("No Data: Attribute={}, CSS-Selector={}, URL={}", current.getName(),
                    selector.getText(), htmlDocument.getDocument().baseUri());
        }
        AttributeStructureUtil.populateValue2Attribute(result.getDetail(), current.getAttributeId(), data);
        List<WorkerItemAttributeData> children = current.getChildren();
        if (!children.isEmpty()) {
            for (WorkerItemAttributeData child : children) {
                collectResult4Attribute(htmlDocument, child, result);
            }
        }
    }

    protected String getURL(String url) {
        return url;
    }

    protected String updateURLPagingFormat(String url) {
        return url;
    }

    protected String formatURLPaging(String url, String page) {
        return String.format(url, page);
    }

    protected boolean isValidLink(String url) {
        return url != null && url.startsWith("http://");
    }

    protected boolean isDownloadSuccess(Document document) {
        return document != null;
    }

    class CrawlerJob implements Callable<Object> {

        private WorkerContext workerCtx;
        private WorkerItemData workerItem;

        public CrawlerJob(WorkerContext workerCtx, WorkerItemData workerItem) {
            this.workerCtx = workerCtx;
            this.workerItem = workerItem;
        }

        @Override
        public Object call() throws Exception {
            try {
                invoke(workerCtx, workerItem);
            } catch (Exception e) {
                String errorMessage = String.format("Error: URL=%s, Message=%s", workerItem.getUrl(), e.getMessage());
                ErrorLink errorLink = new ErrorLink(workerItem.getUrl());
                errorLink.setMessage(errorMessage);
                errorLink.setLevel(workerItem.getLevel());
                errorLink.setCrawlType(workerItem.getCrawlType());
                workerCtx.getErrorLinks().add(errorLink);
                logger.warn(errorMessage);
            }
            return null;
        }
    }
}
