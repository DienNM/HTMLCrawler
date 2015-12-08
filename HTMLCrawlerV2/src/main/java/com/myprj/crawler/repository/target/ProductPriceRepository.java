package com.myprj.crawler.repository.target;

import java.util.List;

import com.myprj.crawler.model.target.ProductPriceModel;
import com.myprj.crawler.repository.GenericDao;

/**
 * @author DienNM (DEE)
 */

public interface ProductPriceRepository extends GenericDao<ProductPriceModel, Long> {
    
    List<ProductPriceModel> findByProductId(long productId);
    
    ProductPriceModel findLatestByProductId(long productId);
    
}
