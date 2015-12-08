package com.myprj.crawler.domain.target;

import java.io.Serializable;

import com.myprj.crawler.annotation.DataTransfer;

/**
 * @author DienNM (DEE)
 */

public class ProductData implements Serializable {

    private static final long serialVersionUID = 1L;

    @DataTransfer("id")
    private long id;

    @DataTransfer("name")
    private String name;

    @DataTransfer("category_key")
    private String categoryKey;

    @DataTransfer("item_key")
    private String itemKey;

    @DataTransfer("site")
    private String site;

    @DataTransfer("price")
    private String price;

    @DataTransfer("price_vat")
    private String priceVAT;

    @DataTransfer("past_price")
    private String pastPrice;

    @DataTransfer("past_price_vat")
    private String pastPriceVAT;

    @DataTransfer("discount")
    private String discount;
    
    public ProductData() {
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