package com.myprj.crawler.service.target;

import java.util.List;

import com.myprj.crawler.domain.target.ProductPriceData;

/**
 * @author DienNM (DEE)
 */

public interface ProductPriceService {
    
    List<ProductPriceData> getByProductId(long productId);
    
    ProductPriceData getLatestByProductId(long productId);
    
}
