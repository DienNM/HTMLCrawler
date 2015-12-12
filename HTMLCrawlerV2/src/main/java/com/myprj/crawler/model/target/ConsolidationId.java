package com.myprj.crawler.model.target;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * @author DienNM (DEE)
 */
@Embeddable
public class ConsolidationId implements Serializable {
    private static final long serialVersionUID = 1L;

    @Column(name = "result_key", length = 32)
    private String resultKey;

    @Column(name = "category_key", length = 50)
    private String categoryKey;

    @Column(name = "item_key", length = 50)
    private String itemKey;

    @Column(name = "site_key", length = 50)
    private String siteKey;
    
    public ConsolidationId() {
    }
    
    @Override
    public String toString() {
        return String.format("Site=%s, Category=%s, Item=%s, Key=%s", siteKey, categoryKey, itemKey, resultKey);
    }
    
    public String getCategoryKey() {
        return categoryKey;
    }

    public void setCategoryKey(String categoryKey) {
        this.categoryKey = categoryKey;
    }

    public String getItemKey() {
        return itemKey;
    }

    public void setItemKey(String itemKey) {
        this.itemKey = itemKey;
    }

    public String getSiteKey() {
        return siteKey;
    }

    public void setSiteKey(String siteKey) {
        this.siteKey = siteKey;
    }

    public String getResultKey() {
        return resultKey;
    }

    public void setResultKey(String key) {
        this.resultKey = key;
    }
}
