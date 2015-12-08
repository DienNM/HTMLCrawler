package com.myprj.crawler.repository.target;

import java.util.List;

import com.myprj.crawler.model.target.ProductModel;
import com.myprj.crawler.repository.GenericDao;

/**
 * @author DienNM (DEE)
 */

public interface ProductRepository extends GenericDao<ProductModel, Long> {
    
    ProductModel findByKeyAndSite(String key, String site);
    
    List<ProductModel> findByName(String name);
    
    List<ProductModel> findByCategoryAndSite(String category, String site);
}
