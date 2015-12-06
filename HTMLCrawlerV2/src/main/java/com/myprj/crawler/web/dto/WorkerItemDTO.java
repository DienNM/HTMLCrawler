package com.myprj.crawler.web.dto;

import java.util.List;
import java.util.Map;

import com.myprj.crawler.annotation.DataTransfer;
import com.myprj.crawler.domain.config.AttributeSelector;
import com.myprj.crawler.domain.crawl.PagingConfig;
import com.myprj.crawler.domain.crawl.WorkerItemData;
import com.myprj.crawler.enumeration.CrawlType;
import com.myprj.crawler.enumeration.Level;
import com.myprj.crawler.util.converter.DomainConverter;
import com.myprj.crawler.util.converter.ObjectConverter;

/**
 * @author DienNM (DEE)
 */

public class WorkerItemDTO extends AuditTDO {

    private static final long serialVersionUID = 1L;
    
    @DataTransfer("level")
    private Level level;

    @DataTransfer("crawlType")
    private CrawlType crawlType;

    @DataTransfer("level0Selector")
    private String level0Selector;
    
    @DataTransfer("url")
    private String url;

    @DataTransfer("pagingConfig")
    private PagingConfig pagingConfig = new PagingConfig();
    
    private Map<String, Object> detailSelectors;
    
    public WorkerItemDTO() {
    }
    
    public static void toDatas(List<WorkerItemDTO> soures, List<WorkerItemData> dests) {
        for(WorkerItemDTO source : soures) {
            WorkerItemData dest = new WorkerItemData();
            toData(source, dest);
            dests.add(dest);
        }
    }
    
    public static void toData(WorkerItemDTO source, WorkerItemData dest) {
        DomainConverter.convert(source, dest, new ObjectConverter<WorkerItemDTO, WorkerItemData>() {
            @Override
            public void convert(WorkerItemDTO src, WorkerItemData dest) {
                if(src.getLevel0Selector() != null) {
                    AttributeSelector attributeSelector = new AttributeSelector(src.getLevel0Selector());
                    dest.setLevel0Selector(attributeSelector);
                }
            }
        });
    }
    
    public static void toDTOs(List<WorkerItemData> soures, List<WorkerItemDTO> dests) {
        for(WorkerItemData source : soures) {
            WorkerItemDTO dest = new WorkerItemDTO();
            toDTO(source, dest);
            dests.add(dest);
        }
    }
    
    public static void toDTO(WorkerItemData source, WorkerItemDTO dest) {
        DomainConverter.convert(source, dest, new ObjectConverter<WorkerItemData, WorkerItemDTO>() {
            @Override
            public void convert(WorkerItemData src, WorkerItemDTO dest) {
                if(src.getLevel0Selector() != null) {
                    dest.setLevel0Selector(src.getLevel0Selector().getText());
                }
            }
        });
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

    public Map<String, Object> getDetailSelectors() {
        return detailSelectors;
    }

    public void setDetailSelectors(Map<String, Object> detailSelectors) {
        this.detailSelectors = detailSelectors;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
