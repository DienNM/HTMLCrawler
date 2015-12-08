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
@Table(name = "product_price")
public class ProductPriceModel extends AuditModel {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    @Column(name = "product_id")
    private long productId;

    @Column(name = "price")
    private double price;

    @Column(name = "past_price")
    private double pastPrice;

    @Column(name = "currency")
    private String currency;

    @Column(name = "discount")
    private String discount;

    @Column(name = "eol_date")
    private long eolDate = 0;
    
    public ProductPriceModel() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getPastPrice() {
        return pastPrice;
    }

    public void setPastPrice(double pastPrice) {
        this.pastPrice = pastPrice;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public long getEolDate() {
        return eolDate;
    }

    public void setEolDate(long eolDate) {
        this.eolDate = eolDate;
    }

}
