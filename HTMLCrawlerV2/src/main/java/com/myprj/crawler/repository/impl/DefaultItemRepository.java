package com.myprj.crawler.repository.impl;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.myprj.crawler.model.config.ItemModel;
import com.myprj.crawler.repository.ItemRepository;

/**
 * @author DienNM (DEE)
 */
@Repository
public class DefaultItemRepository extends DefaultGenericDao<ItemModel, Long> implements ItemRepository {

    @Override
    protected Class<ItemModel> getTargetClass() {
        return ItemModel.class;
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public List<ItemModel> findByCategory(long category) {
        Query query = entityManager.createQuery("FROM " + getClassName() + " WHERE categoryId = :categoryId");
        query.setParameter("categoryId", category);
        return query.getResultList();
    }

}
