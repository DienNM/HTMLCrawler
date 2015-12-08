package com.myprj.crawler.domain.target;

import java.util.List;

import com.myprj.crawler.annotation.EntityTransfer;
import com.myprj.crawler.domain.AuditData;
import com.myprj.crawler.model.target.ProductPriceModel;
import com.myprj.crawler.util.converter.EntityConverter;

/**
 * @author DienNM (DEE)
 */

public class ProductPriceData extends AuditData {

    private static final long serialVersionUID = 1L;

    @EntityTransfer("id")
    private long id;

    @EntityTransfer("product_id")
    private long productId;

    @EntityTransfer("price")
    private double price;

    @EntityTransfer("past_price")
    private double pastPrice;

    @EntityTransfer("currency")
    private String currency;

    @EntityTransfer("discount")
    private String discount;

    @EntityTransfer("eol_date")
    private long eolDate = 0;

    public ProductPriceData() {
    }
    
    public static void toDatas(List<ProductPriceModel> sources, List<ProductPriceData> dests) {
        for (ProductPriceModel source : sources) {
            ProductPriceData dest = new ProductPriceData();
            toData(source, dest);
            dests.add(dest);
        }
    }

    public static void toModels(List<ProductPriceData> sources, List<ProductPriceModel> dests) {
        for (ProductPriceData source : sources) {
            ProductPriceModel dest = new ProductPriceModel();
            toModel(source, dest);
            dests.add(dest);
        }
    }

    public static void toData(ProductPriceModel source, ProductPriceData dest) {
        EntityConverter.convert2Data(source, dest);
    }

    public static void toModel(ProductPriceData source, ProductPriceModel dest) {
        EntityConverter.convert2Entity(source, dest);
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