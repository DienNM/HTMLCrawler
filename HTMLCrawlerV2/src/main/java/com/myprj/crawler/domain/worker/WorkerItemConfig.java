package com.myprj.crawler.domain.worker;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author DienNM (DEE)
 */

public class WorkerItemConfig implements Serializable {

    private static final long serialVersionUID = 1L;

    private long categoryId;

    private long itemId;

    private Map<Long, String> attributesCssSelectors = new HashMap<Long, String>();

    public WorkerItemConfig() {
    }

    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }

    public long getItemId() {
        return itemId;
    }

    public void setItemId(long itemId) {
        this.itemId = itemId;
    }

    public Map<Long, String> getAttributesCssSelectors() {
        return attributesCssSelectors;
    }

    public void setAttributesCssSelectors(Map<Long, String> attributesCssSelectors) {
        this.attributesCssSelectors = attributesCssSelectors;
    }

}
