package com.myprj.crawler.service.impl;

import static com.myprj.crawler.enumeration.WorkerItemTargetType.LIST;
import static com.myprj.crawler.util.DateUtil.addDays;
import static com.myprj.crawler.util.Serialization.deserialize;

import java.util.Date;
import java.util.Map;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.myprj.crawler.domain.WorkerContext;
import com.myprj.crawler.domain.worker.CssSelector;
import com.myprj.crawler.domain.worker.ErrorLink;
import com.myprj.crawler.domain.worker.ListWorkerTargetParameter;
import com.myprj.crawler.enumeration.AttributeType;
import com.myprj.crawler.enumeration.WorkerItemTargetType;
import com.myprj.crawler.exception.WorkerException;
import com.myprj.crawler.model.crawl.WorkerItemModel;
import com.myprj.crawler.model.crawl.WorkerModel;
import com.myprj.crawler.util.DateUtil;
import com.myprj.crawler.util.HtmlDownloader;

/**
 * @author DienNM (DEE)
 */

public class DefaultDateWorkerService extends DefaultWorkerService {

    private final Logger logger = LoggerFactory.getLogger(DefaultDateWorkerService.class);
    
    public static final String DATE_FORMAT = "MM-dd-yyyy";

    @SuppressWarnings("unchecked")
    protected void doWorkOnWorkerItemList(WorkerContext workerCtx, WorkerItemModel workerItem) throws WorkerException {
        ListWorkerTargetParameter workerTarget = deserialize(workerItem.getPagingConfig(),
                ListWorkerTargetParameter.class);

        WorkerModel worker = workerCtx.getWorker();
        String url = workerItem.getUrl();
        
        Date startDate = DateUtil.convertString2Date(workerTarget.getStart(), getDateFormat());
        Date endDate = DateUtil.convertString2Date(workerTarget.getEnd(), getDateFormat());
        
        for (; DateUtil.lte(startDate, endDate); startDate = addDays(startDate, 1)) {
            String urlWithPaging = formateURLWithDate(updateUrlFormat(url), startDate);
            logger.info("Start crawling: {}", urlWithPaging);
            urlWithPaging = updateUrlBeforeCrawling(urlWithPaging);

            Document document = HtmlDownloader.download(urlWithPaging, worker.getDelayTime(), worker.getAttemptTimes());
            if (!isDownloadSuccess(document)) {
                String errorMessage = "Cannot download HTML from " + urlWithPaging;
                ErrorLink errorLink = new ErrorLink(url);
                errorLink.setMessage(errorMessage);
                errorLink.setLevel(workerItem.getLevel());
                errorLink.setTargetType(WorkerItemTargetType.DETAIL);
                workerCtx.getErrorLinks().add(errorLink);
                logger.warn(errorMessage);
            }

            Map<String, String> cssSelectors = deserialize(workerItem.getcssSelectors(), Map.class);
            
            String text = cssSelectors.get(AttributeType.LINK.name());
            CssSelector cssSelector = new CssSelector(text);
            Elements elements = document.body().select(cssSelector.getSelector());
            if (elements == null) {
                String errorMessage = String.format("No Data. CSS-Selector=%s, URL=%s", cssSelector, urlWithPaging);
                ErrorLink errorLink = new ErrorLink(url);
                errorLink.setMessage(errorMessage);
                errorLink.setLevel(workerItem.getLevel());
                errorLink.setTargetType(LIST);
                workerCtx.getErrorLinks().add(errorLink);
                logger.error(errorMessage);
                continue;
            }
            processListItemsPerPage(workerCtx, workerItem, workerTarget, elements, cssSelector);
            logger.info("End crawling: {}", urlWithPaging);
        }
    }
    
    protected String getDateFormat() {
        return DATE_FORMAT;
    }
    
    protected String formateURLWithDate(String url, Date date) {
        String text = DateUtil.convertDate2String(date, getDateFormat());
        return String.format(url, text);
    }
    
}
