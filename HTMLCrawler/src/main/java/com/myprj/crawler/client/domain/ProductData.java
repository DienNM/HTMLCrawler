package com.myprj.crawler.client.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * @author DienNM(DEE)
 **/

public class ProductData {

    private String name;

    private String price;

    private String originalPrice;

    private String description;

    private List<String> descriptionDetails = new ArrayList<String>();

    @Override
    public String toString() {
        return String.format("%s|price=%s|Orginal Price=%s|%s", name, price, originalPrice,
                descriptionDetails.toString());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(String originalPrice) {
        this.originalPrice = originalPrice;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getDescriptionDetails() {
        return descriptionDetails;
    }

    public void setDescriptionDetails(List<String> descriptionDetails) {
        this.descriptionDetails = descriptionDetails;
    }
}
