package com.myprj.crawler.service.target;

import java.util.List;

import com.myprj.crawler.domain.target.ProductAttributeData;

/**
 * @author DienNM (DEE)
 */

public interface ProductAttributeService {
    
    List<ProductAttributeData> getByProductId(long productId);
    
    ProductAttributeData getById(long id);
    
    ProductAttributeData get(String name, long productId);
    
}
