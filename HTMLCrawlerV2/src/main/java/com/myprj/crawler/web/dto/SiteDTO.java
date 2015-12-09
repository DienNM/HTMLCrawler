package com.myprj.crawler.web.dto;

import java.util.List;

import com.myprj.crawler.annotation.DataTransfer;
import com.myprj.crawler.domain.SiteData;
import com.myprj.crawler.util.converter.DomainConverter;

/**
 * @author DienNM (DEE)
 */

public class SiteDTO extends AuditTDO {

    private static final long serialVersionUID = 1L;
    
    @DataTransfer("key")
    private String key;

    @DataTransfer("name")
    private String name;

    @DataTransfer("url")
    private String url;

    @DataTransfer("description")
    private String description;

    public SiteDTO() {
    }

    public static void toDTOs(List<SiteData> sources, List<SiteDTO> dests) {
        for (SiteData source : sources) {
            SiteDTO dest = new SiteDTO();
            toDTO(source, dest);
            dests.add(dest);
        }
    }

    public static void toDTO(SiteData source, SiteDTO dest) {
        DomainConverter.convert(source, dest);
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}