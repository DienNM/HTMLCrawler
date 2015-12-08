package com.myprj.crawler.repository.target;

import java.util.List;

import com.myprj.crawler.model.target.ProductAttributeModel;
import com.myprj.crawler.repository.GenericDao;

/**
 * @author DienNM (DEE)
 */

public interface ProductAttributeRepository extends GenericDao<ProductAttributeModel, Long> {
    
    List<ProductAttributeModel> findByProductId(long productId);

    ProductAttributeModel findByNameAndProductId(String name, long productId);
    
    void deleteByProductId(long productId);
}
