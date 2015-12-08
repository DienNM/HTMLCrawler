package com.myprj.crawler.repository.target.impl;

import java.util.Calendar;
import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.myprj.crawler.model.target.ProductPriceModel;
import com.myprj.crawler.repository.impl.DefaultGenericDao;
import com.myprj.crawler.repository.target.ProductPriceRepository;

/**
 * @author DienNM (DEE)
 */
@Repository
public class DefaultProductPriceRepository extends DefaultGenericDao<ProductPriceModel, Long> implements
        ProductPriceRepository {

    @SuppressWarnings("unchecked")
    @Override
    public List<ProductPriceModel> findByProductId(long productId) {
        Query query = entityManager.createQuery("FROM " + getClassName() + " WHERE productId = :productId ");
        query.setParameter("productId", productId);
        return query.getResultList();
    }
    
    @Override
    public void save(ProductPriceModel entity) {
        ProductPriceModel latestProductPrice = findLatestByProductId(entity.getProductId());
        if (latestProductPrice != null) {
            latestProductPrice.setEolDate(Calendar.getInstance().getTimeInMillis());
            update(latestProductPrice);
        }
        entity.setEolDate(0);
        super.save(entity);
    }

    @SuppressWarnings("unchecked")
    @Override
    public ProductPriceModel findLatestByProductId(long productId) {
        Query query = entityManager.createQuery("FROM " + getClassName()
                + " WHERE productId = :productId AND eolDate = 0 ");
        query.setParameter("productId", productId);
        List<ProductPriceModel> productPriceModels = query.getResultList();
        if (productPriceModels.isEmpty()) {
            return null;
        }
        return productPriceModels.get(0);
    }

    @Override
    protected Class<ProductPriceModel> getTargetClass() {
        return ProductPriceModel.class;
    }

}
