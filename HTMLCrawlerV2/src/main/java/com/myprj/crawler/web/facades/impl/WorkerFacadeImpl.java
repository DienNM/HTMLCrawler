package com.myprj.crawler.web.facades.impl;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myprj.crawler.domain.config.AttributeSelector;
import com.myprj.crawler.domain.crawl.PagingConfig;
import com.myprj.crawler.domain.crawl.WorkerData;
import com.myprj.crawler.domain.crawl.WorkerItemData;
import com.myprj.crawler.enumeration.CrawlType;
import com.myprj.crawler.enumeration.Level;
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

    // prod-mobile|tiki|3|5|1000|Product Mobile Worker|Product Mobile Worker
    // Level0|LIST|1|1|div.product-col-2 div.infomation p.title a{{href}}|http://tiki.vn/dien-thoai-di-dong/c1793?mode=list&page=%s
    // Level1|DETAIL|0|0||
    @Override
    public List<String> loadImportWorkersFromSource(InputStream inputStream, boolean forceBuild) {
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
                    workerData = workerService.save(workerData);
                    List<WorkerItemData> workerItems = new ArrayList<WorkerItemData>();
                    for (int i = 1; i < lines.size(); i++) {
                        WorkerItemData workerItem = parseWorkerItem(lines.get(i));
                        workerItem.setWorkerId(workerData.getId());
                        workerItems.add(workerItem);
                    }
                    if (!workerItems.isEmpty()) {
                        workerItems = workerService.buildWorkerItems(workerData, workerItems);
                        buildSelector(workerItems, item.getJson(), forceBuild);
                    }
                } catch(Exception e) {
                    errorStructures.add("Error: " + e.getMessage());
                }
            }
        }
        return errorStructures;
    }

    private void buildSelector(List<WorkerItemData> workerItems, String json, boolean forceBuild) {
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
        WorkerData workerData = new WorkerData();
        workerData.setSite(elements[0]);
        workerData.setThreads(Integer.valueOf(elements[1]));
        workerData.setAttemptTimes(Integer.valueOf(elements[2]));
        workerData.setDelayTime(Integer.valueOf(elements[3]));
        workerData.setName(elements[4]);
        workerData.setDescription(elements[5]);
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
        }
        
        return workerItem;
    }

}
