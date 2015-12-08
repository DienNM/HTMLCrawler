package com.myprj.crawler.repository.target.impl;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.myprj.crawler.model.target.ProductAttributeModel;
import com.myprj.crawler.repository.impl.DefaultGenericDao;
import com.myprj.crawler.repository.target.ProductAttributeRepository;

/**
 * @author DienNM (DEE)
 */
@Repository
public class DefaultProductAttributeRepository extends DefaultGenericDao<ProductAttributeModel, Long> implements
        ProductAttributeRepository {

    @SuppressWarnings("unchecked")
    @Override
    public List<ProductAttributeModel> findByProductId(long productId) {
        Query query = entityManager.createQuery("FROM " + getClassName() + " WHERE productId = :productId ");
        query.setParameter("productId", productId);
        return query.getResultList();
    }

    @SuppressWarnings("unchecked")
    @Override
    public ProductAttributeModel findByNameAndProductId(String name, long productId) {
        Query query = entityManager.createQuery("FROM " + getClassName()
                + " WHERE name = :name AND productId = :productId ");
        query.setParameter("name", name);
        query.setParameter("productId", productId);
        List<ProductAttributeModel> productAttributeModels = query.getResultList();
        if (productAttributeModels.isEmpty()) {
            return null;
        }
        return productAttributeModels.get(0);
    }

    @Override
    protected Class<ProductAttributeModel> getTargetClass() {
        return ProductAttributeModel.class;
    }

    @Override
    public void deleteByProductId(long productId) {
        Query query = entityManager.createQuery("DELETE FROM " + getClassName() + 
                " WHERE productId = :productId ");
        query.setParameter("productId", productId);
        query.executeUpdate();
    }

}
