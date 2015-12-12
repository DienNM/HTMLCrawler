package com.myprj.crawler.model.crawl;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * @author DienNM (DEE)
 */
@Embeddable
public class CrawlResultId implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "result_key", length = 32)
    private String resultKey;

    @Column(name = "item_key", length = 50)
    private String itemKey;

    @Column(name = "category_key", length = 50)
    private String categoryKey;

    @Column(name = "site_key", length = 50)
    private String siteKey;

    public CrawlResultId() {
    }

    public String getItemKey() {
        return itemKey;
    }

    public void setItemKey(String itemKey) {
        this.itemKey = itemKey;
    }

    public String getCategoryKey() {
        return categoryKey;
    }

    public void setCategoryKey(String categoryKey) {
        this.categoryKey = categoryKey;
    }

    public String getSiteKey() {
        return siteKey;
    }

    public void setSiteKey(String site) {
        this.siteKey = site;
    }

    public String getResultKey() {
        return resultKey;
    }

    public void setResultKey(String resultKey) {
        this.resultKey = resultKey;
    }
}
