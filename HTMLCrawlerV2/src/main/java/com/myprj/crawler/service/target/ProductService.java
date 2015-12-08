package com.myprj.crawler.service.target;

import java.util.List;

import com.myprj.crawler.domain.target.ProductData;

/**
 * @author DienNM (DEE)
 */

public interface ProductService {

    ProductData getById(long id);

    ProductData getByKey(String key, String site);
    
    List<ProductData> getByName(String name);

    List<ProductData> getByCategory(String category, String site);

    ProductData save(ProductData productData);

    void update(ProductData productData);
    
    void populateAttributes(ProductData productData);

}
