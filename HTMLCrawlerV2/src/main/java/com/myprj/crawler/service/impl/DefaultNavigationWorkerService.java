package com.myprj.crawler.service.impl;

import static com.myprj.crawler.util.DateUtil.addDays;
import static com.myprj.crawler.util.Serialization.deserialize;

import java.util.Date;

import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.myprj.crawler.domain.HtmlDocument;
import com.myprj.crawler.domain.WorkerContext;
import com.myprj.crawler.domain.worker.ErrorLink;
import com.myprj.crawler.domain.worker.ListWorkerTargetParameter;
import com.myprj.crawler.enumeration.WorkerItemTargetType;
import com.myprj.crawler.exception.WorkerException;
import com.myprj.crawler.model.crawl.WorkerItemModel;
import com.myprj.crawler.model.crawl.WorkerModel;
import com.myprj.crawler.util.DateUtil;
import com.myprj.crawler.util.HtmlDownloader;

/**
 * @author DienNM (DEE)
 */

public class DefaultNavigationWorkerService extends DefaultWorkerService {

    private final Logger logger = LoggerFactory.getLogger(DefaultNavigationWorkerService.class);
    
    public static final String DATE_FORMAT = "MM-dd-yyyy";

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
            collectionDetail(urlWithPaging, new HtmlDocument(document), workerItem);
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
