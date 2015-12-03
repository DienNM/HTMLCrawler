package com.myprj.crawler.util;

import static com.myprj.crawler.enumeration.CrawlType.DETAIL;
import static com.myprj.crawler.enumeration.CrawlType.LIST;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.myprj.crawler.domain.config.AttributeSelector;
import com.myprj.crawler.domain.crawl.WorkerItemData;
import com.myprj.crawler.enumeration.CrawlType;
import com.myprj.crawler.enumeration.Level;

/**
 * @author DienNM (DEE)
 */

public final class WorkerItemValidator {
    
    public static void validateCreationPhase(WorkerItemData workerItem) {
        List<String> errors = new ArrayList<String>();
        validateCommon(workerItem, errors);
        if(!errors.isEmpty()) {
            throw new InvalidParameterException(StringUtils.join(errors, ", "));
        }
    }
    
    public static void validateCrawlPhase(WorkerItemData workerItem) {
        List<String> errors = new ArrayList<String>();
        validateCommon(workerItem, errors);
        
        CrawlType crawlType = workerItem.getCrawlType();
        if(LIST.equals(crawlType)) {
            validateDetailType(workerItem, errors);
        } else if(DETAIL.equals(crawlType)) {
            validateListType(workerItem, errors);
        }
        
        if(!errors.isEmpty()) {
            throw new InvalidParameterException(StringUtils.join(errors, ", "));
        }
    }
    
    public static void validateCommon(WorkerItemData workerItem, List<String> errors) {
        CrawlType crawlType = workerItem.getCrawlType();
        
        if(workerItem.getLevel() == null) {
            errors.add("Level is missing");
        }
        
        if(crawlType == null) {
            errors.add(String.format("%s is missing CrawlType", workerItem.getLevel()));
        }
        
        if(Level.Level0.equals(workerItem.getLevel())) {
            if(StringUtils.isEmpty(workerItem.getUrl())) {
                errors.add("Level0 is missing URL");
            }
        }
        
        if(LIST.equals(crawlType)) {
            validateDetailType(workerItem, errors);
        } else if(DETAIL.equals(crawlType)) {
            validateListType(workerItem, errors);
        }
    }
    
    public static void validateDetailType(WorkerItemData workerItem, List<String> errors) {
        if(workerItem.getItemId() == -1 || workerItem.getItem() == null) {
            errors.add(String.format("%s [CrawlType = %s] is missing Item", workerItem.getLevel(), workerItem.getClass()));
        }
        
        if(workerItem.getRootItemAttribute() == null) {
            errors.add(String.format("%s [CrawlType = %s] is missing Item Attributes", workerItem.getLevel(), workerItem.getClass()));
        }
    }
    
    public static void validateListType(WorkerItemData workerItem, List<String> errors) {
        if(workerItem.getPagingConfig() == null) {
            errors.add(String.format("%s [CrawlType = %s] is missing Paging Information", workerItem.getLevel(), workerItem.getClass()));
        }
        
        AttributeSelector selector = workerItem.getLinkSelector();
        if(selector == null || StringUtils.isEmpty(selector.getSelector())) {
            errors.add(String.format("%s [CrawlType = %s] is missing Link Selector", workerItem.getLevel(), workerItem.getClass()));
        }
    }
}
