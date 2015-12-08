package com.myprj.crawler.model.target;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * @author DienNM (DEE)
 */
@MappedSuperclass
public class ProductModel implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;
    
    @Column(name = "name")
    private String name;

    @Column(name = "category_key")
    private String categoryKey;

    @Column(name = "item_key")
    private String itemKey;

    @Column(name = "site")
    private String site;

    @Column(name = "price")
    private String price;

    @Column(name = "price_vat")
    private String priceVAT;

    @Column(name = "past_price")
    private String pastPrice;

    @Column(name = "past_price_vat")
    private String pastPriceVAT;

    @Column(name = "discount")
    private String discount;
    
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
    public String getPriceVAT() {
        return priceVAT;
    }
    public void setPriceVAT(String priceVAT) {
        this.priceVAT = priceVAT;
    }
    public String getPastPrice() {
        return pastPrice;
    }
    public void setPastPrice(String pastPrice) {
        this.pastPrice = pastPrice;
    }
    public String getPastPriceVAT() {
        return pastPriceVAT;
    }
    public void setPastPriceVAT(String pastPriceVAT) {
        this.pastPriceVAT = pastPriceVAT;
    }
    public String getDiscount() {
        return discount;
    }
    public void setDiscount(String discount) {
        this.discount = discount;
    }
    

}
