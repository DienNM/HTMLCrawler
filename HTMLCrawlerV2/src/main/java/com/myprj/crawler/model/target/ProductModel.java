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
@Table(name = "product")
public class ProductModel extends AuditModel {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    @Column(name = "key_sku", length = 200)
    private String key;

    @Column(name = "name", length = 200)
    private String name;

    @Column(name = "category_key", length = 50)
    private String categoryKey;

    @Column(name = "item_key", length = 50)
    private String itemKey;

    @Column(name = "site", length = 50)
    private String site;

    @Column(name = "price", length = 50)
    private String price;

    @Column(name = "included_vat")
    private boolean includedVat;

    @Column(name = "past_price", length = 50)
    private String pastPrice;
    
    public ProductModel() {
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPastPrice() {
        return pastPrice;
    }

    public void setPastPrice(String pastPrice) {
        this.pastPrice = pastPrice;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public boolean isIncludedVat() {
        return includedVat;
    }

    public void setIncludedVat(boolean includedVat) {
        this.includedVat = includedVat;
    }

}
