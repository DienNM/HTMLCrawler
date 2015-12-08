package com.myprj.crawler.domain.target;

import java.util.List;

import com.myprj.crawler.annotation.EntityTransfer;
import com.myprj.crawler.model.AuditModel;
import com.myprj.crawler.model.target.ProductAttributeModel;
import com.myprj.crawler.util.converter.EntityConverter;

/**
 * @author DienNM (DEE)
 */

public class ProductAttributeData extends AuditModel {

    private static final long serialVersionUID = 1L;

    @EntityTransfer("id")
    private long id;

    @EntityTransfer("name")
    private String name;

    @EntityTransfer("value")
    private String value;

    @EntityTransfer("product_id")
    private long productId;

    public ProductAttributeData() {
    }

    public static void toDatas(List<ProductAttributeModel> sources, List<ProductAttributeData> dests) {
        for (ProductAttributeModel source : sources) {
            ProductAttributeData dest = new ProductAttributeData();
            toData(source, dest);
            dests.add(dest);
        }
    }

    public static void toModels(List<ProductAttributeData> sources, List<ProductAttributeModel> dests) {
        for (ProductAttributeData source : sources) {
            ProductAttributeModel dest = new ProductAttributeModel();
            toModel(source, dest);
            dests.add(dest);
        }
    }

    public static void toData(ProductAttributeModel source, ProductAttributeData dest) {
        EntityConverter.convert2Data(source, dest);
    }

    public static void toModel(ProductAttributeData source, ProductAttributeModel dest) {
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}