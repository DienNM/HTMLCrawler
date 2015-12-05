package com.myprj.crawler.web.dto;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.myprj.crawler.domain.config.AttributeSelector;
import com.myprj.crawler.domain.crawl.PagingConfig;
import com.myprj.crawler.domain.crawl.WorkerItemData;
import com.myprj.crawler.enumeration.CrawlType;
import com.myprj.crawler.enumeration.Level;

/**
 * @author DienNM (DEE)
 */

public class WorkerItemDTO extends AuditTDO {

    private static final long serialVersionUID = 1L;
    
    private Level level;
    
    private CrawlType crawlType;
    
    private String level0Selector;
    
    private PagingConfig pagingConfig = new PagingConfig();
    
    public WorkerItemDTO() {
    }
    
    public static List<WorkerItemDTO> toDTOs(List<WorkerItemData> soures) {
        List<WorkerItemDTO> dests = new  ArrayList<WorkerItemDTO>();
        toDTOs(soures, dests);
        return dests;
    }
    
    public static void toDTOs(List<WorkerItemData> soures, List<WorkerItemDTO> dests) {
        for(WorkerItemData source : soures) {
            WorkerItemDTO dest = new WorkerItemDTO();
            toDTO(source, dest);
            dests.add(dest);
        }
    }
    
    public static void toDTO(WorkerItemData source, WorkerItemDTO dest) {
        dest.setCrawlType(source.getCrawlType());
        dest.setLevel(source.getLevel());
        if(source.getPagingConfig() != null) {
            dest.setPagingConfig(source.getPagingConfig());
        }
        if(source.getLevel0Selector() != null) {
            dest.setLevel0Selector(source.getLevel0Selector().getText());
        }
    }
    
    public static List<WorkerItemData> toDatas(List<WorkerItemDTO> soures) {
        List<WorkerItemData> dests = new  ArrayList<WorkerItemData>();
        toDatas(soures, dests);
        return dests;
    }
    
    public static void toDatas(List<WorkerItemDTO> soures, List<WorkerItemData> dests) {
        for(WorkerItemDTO source : soures) {
            WorkerItemData dest = new WorkerItemData();
            toData(source, dest);
            dests.add(dest);
        }
    }
    
    public static void toData(WorkerItemDTO source, WorkerItemData dest) {
        dest.setCrawlType(source.getCrawlType());
        dest.setLevel(source.getLevel());
        if(source.getPagingConfig() != null) {
            dest.setPagingConfig(source.getPagingConfig());
        }
        if(!StringUtils.isEmpty(source.getLevel0Selector())) {
            AttributeSelector selector = new AttributeSelector(source.getLevel0Selector());
            dest.setLevel0Selector(selector);
        }
    }
    
    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public CrawlType getCrawlType() {
        return crawlType;
    }

    public void setCrawlType(CrawlType crawlType) {
        this.crawlType = crawlType;
    }

    public PagingConfig getPagingConfig() {
        return pagingConfig;
    }

    public void setPagingConfig(PagingConfig pagingConfig) {
        this.pagingConfig = pagingConfig;
    }

    public String getLevel0Selector() {
        return level0Selector;
    }

    public void setLevel0Selector(String level0Selector) {
        this.level0Selector = level0Selector;
    }
}
