package com.myprj.crawler.domain;

import java.util.List;

import com.myprj.crawler.annotation.DataTransfer;
import com.myprj.crawler.annotation.EntityTransfer;
import com.myprj.crawler.model.SiteModel;
import com.myprj.crawler.util.converter.EntityConverter;

/**
 * @author DienNM (DEE)
 */

public class SiteData extends AuditData {

    private static final long serialVersionUID = 1L;
    @EntityTransfer("site_key")
    @DataTransfer("key")
    private String key;

    @EntityTransfer("name")
    @DataTransfer("name")
    private String name;

    @EntityTransfer("url")
    @DataTransfer("url")
    private String url;

    @EntityTransfer("description")
    @DataTransfer("description")
    private String description;

    public SiteData() {
    }

    public static void toDatas(List<SiteModel> sources, List<SiteData> dests) {
        for (SiteModel source : sources) {
            SiteData dest = new SiteData();
            toData(source, dest);
            dests.add(dest);
        }
    }

    public static void toData(SiteModel source, SiteData dest) {
        EntityConverter.convert2Data(source, dest);
    }

    public static void toModels(List<SiteData> sources, List<SiteModel> dests) {
        for (SiteData source : sources) {
            SiteModel dest = new SiteModel();
            toModel(source, dest);
            dests.add(dest);
        }
    }

    public static void toModel(SiteData source, SiteModel dest) {
        EntityConverter.convert2Data(source, dest);
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
