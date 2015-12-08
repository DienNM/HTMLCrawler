package com.myprj.crawler.repository.target.impl;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.myprj.crawler.model.target.ProductModel;
import com.myprj.crawler.repository.impl.DefaultGenericDao;
import com.myprj.crawler.repository.target.ProductRepository;

/**
 * @author DienNM (DEE)
 */
@Repository
public class DefaultProductRepository extends DefaultGenericDao<ProductModel, Long> implements ProductRepository {

    @SuppressWarnings("unchecked")
    @Override
    public ProductModel findByKeyAndSite(String key, String site) {
        Query query = entityManager.createQuery("FROM " + getClassName() + " WHERE key = :key AND site = :site ");
        query.setParameter("key", key);
        query.setParameter("site", site);
        List<ProductModel> productModels = query.getResultList();
        if (productModels.isEmpty()) {
            return null;
        }
        return productModels.get(0);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<ProductModel> findByCategoryAndSite(String category, String site) {
        Query query = entityManager.createQuery("FROM " + getClassName()
                + " WHERE categoryKey = :categoryKey AND site = :site ");
        query.setParameter("categoryKey", category);
        query.setParameter("site", site);
        return query.getResultList();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<ProductModel> findByName(String name) {
        Query query = entityManager.createQuery("FROM " + getClassName() + " WHERE name LIKE :name ");
        query.setParameter("name", "%" + name + "%");
        return query.getResultList();
    }

    @Override
    protected Class<ProductModel> getTargetClass() {
        return ProductModel.class;
    }

}
