package com.myprj.crawler.model.target;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.myprj.crawler.model.AuditModel;

/**
 * @author DienNM (DEE)
 */

@Entity
@Table(name = "consolidation")
public class ConsolidationModel extends AuditModel {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    @Column(name = "result_key", length = 200)
    private String resultKey;

    @Column(name = "name", length = 200)
    private String name;

    @Column(name = "category_key", length = 50)
    private String categoryKey;

    @Column(name = "item_key", length = 50)
    private String itemKey;

    @Column(name = "site", length = 50)
    private String site;

    public ConsolidationModel() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getResultKey() {
        return resultKey;
    }

    public void setResultKey(String key) {
        this.resultKey = key;
    }
}
