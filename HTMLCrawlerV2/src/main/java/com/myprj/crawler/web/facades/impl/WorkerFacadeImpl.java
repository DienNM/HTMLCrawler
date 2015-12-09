package com.myprj.crawler.web.facades.impl;

import java.io.InputStream;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myprj.crawler.domain.SiteData;
import com.myprj.crawler.domain.config.AttributeSelector;
import com.myprj.crawler.domain.crawl.PagingConfig;
import com.myprj.crawler.domain.crawl.WorkerData;
import com.myprj.crawler.domain.crawl.WorkerItemData;
import com.myprj.crawler.enumeration.CrawlType;
import com.myprj.crawler.enumeration.Level;
import com.myprj.crawler.service.SiteService;
import com.myprj.crawler.service.WorkerService;
import com.myprj.crawler.web.facades.WorkerFacade;
import com.myprj.crawler.web.util.ImportFileParser;
import com.myprj.crawler.web.util.ImportFileStruture;

/**
 * @author DienNM (DEE)
 */
@Service
public class WorkerFacadeImpl implements WorkerFacade {

    @Autowired
    private WorkerService workerService;
    
    @Autowired
    private SiteService siteService;

    @Override
    public List<String> loadImportWorkersFromSource(InputStream inputStream) {
        List<String> errorStructures = new ArrayList<String>();
        List<ImportFileStruture> importFileStrutures = ImportFileParser.loadItemFromSource(inputStream);
        if (importFileStrutures.isEmpty()) {
            errorStructures.add("No Workers loaded");
            return errorStructures;
        }
        for (ImportFileStruture item : importFileStrutures) {
            for (List<String> lines : item.getMainLines()) {
                String lineOfWorker = lines.get(0);
                try {
                    WorkerData workerData = parseWorker(lineOfWorker);
                    workerData = workerService.saveOrUpdate(workerData);
                    List<WorkerItemData> workerItems = new ArrayList<WorkerItemData>();
                    for (int i = 1; i < lines.size(); i++) {
                        WorkerItemData workerItem = parseWorkerItem(lines.get(i));
                        workerItem.setWorkerId(workerData.getId());
                        workerItems.add(workerItem);
                    }
                    if (!workerItems.isEmpty()) {
                        workerItems = workerService.buildWorkerItems(workerData, workerItems);
                        buildWorkerItemSelectors(workerItems, item.getJson());
                    }
                } catch(Exception e) {
                    errorStructures.add("Error: " + e.getMessage());
                }
            }
        }
        return errorStructures;
    }

    private void buildWorkerItemSelectors(List<WorkerItemData> workerItems, String json) {
        WorkerItemData detailWorkerItem = null;
        for (WorkerItemData workerItem : workerItems) {
            if (workerItem.getCrawlType().equals(CrawlType.DETAIL)) {
                detailWorkerItem = workerItem;
                break;
            }
        }
        if (detailWorkerItem == null) {
            return;
        }
        workerService.buildSelector4Item(detailWorkerItem, json);
    }

    private WorkerData parseWorker(String line) {
        String[] elements = line.split(Pattern.quote("|"));
        
        SiteData siteData = siteService.get(elements[1]);
        if(siteData == null) {
            throw new InvalidParameterException("Site Key " + elements[1] + " not found");
        }
        
        WorkerData workerData = new WorkerData();
        workerData.setKey(elements[0]);
        workerData.setSite(elements[1]);
        workerData.setThreads(Integer.valueOf(elements[2]));
        workerData.setAttemptTimes(Integer.valueOf(elements[3]));
        workerData.setDelayTime(Integer.valueOf(elements[4]));
        workerData.setName(elements[5]);
        if(elements.length > 6) {
            workerData.setDescription(elements[6]);
        }
        return workerData;
    }

    private WorkerItemData parseWorkerItem(String line) {
        String[] elements = line.split(Pattern.quote("|"));
        WorkerItemData workerItem = new WorkerItemData();
        workerItem.setItemKey(elements[0]);
        workerItem.setLevel(Level.valueOf(elements[1]));
        workerItem.setCrawlType(CrawlType.valueOf(elements[2]));
        
        if(workerItem.getCrawlType().equals(CrawlType.LIST)) {
            PagingConfig pagingConfig = new PagingConfig();
            pagingConfig.setStart(elements[3]);
            pagingConfig.setEnd(elements[4]);
            workerItem.setPagingConfig(pagingConfig);
            
            if (!StringUtils.isEmpty(elements[5])) {
                AttributeSelector selector = new AttributeSelector(elements[5]);
                workerItem.setLevel0Selector(selector);
            }
            workerItem.setUrl(elements[6]);
        } else {
            workerItem.setPagingConfig(null);
        }
        
        return workerItem;
    }

}
