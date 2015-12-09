package com.myprj.crawler.repository.impl;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.myprj.crawler.model.config.ItemAttributeModel;
import com.myprj.crawler.repository.ItemAttributeRepository;

/**
 * @author DienNM (DEE)
 */

@SuppressWarnings("unchecked")
@Repository
public class DefaultItemAttributeRepository extends DefaultGenericDao<ItemAttributeModel, String> implements ItemAttributeRepository {

    @Override
    protected Class<ItemAttributeModel> getTargetClass() {
        return ItemAttributeModel.class;
    }

    @Override
    public List<ItemAttributeModel> findChildren(String id) {
        Query query = entityManager.createQuery("FROM " + getClassName() + " WHERE parentId = :parentId");
        query.setParameter("parentId", id);
        return query.getResultList();
    }

    @Override
    public List<ItemAttributeModel> findByItemId(String itemId) {
        Query query = entityManager.createQuery("FROM " + getClassName() + " WHERE itemId = :itemId");
        query.setParameter("itemId", itemId);
        return query.getResultList();
    }

    @Override
    public ItemAttributeModel findRootByItemId(String itemId) {
        Query query = entityManager.createQuery("FROM " + getClassName() + 
                " WHERE itemId = :itemId AND root = :root ");
        query.setParameter("itemId", itemId);
        query.setParameter("root", true);
        List<ItemAttributeModel> attributeModels = query.getResultList();
        if(attributeModels.isEmpty()) {
            return null;
        }
        return attributeModels.get(0);
    }

    @Override
    public void deleteByItemId(String itemId) {
        Query query = entityManager.createQuery("DELETE FROM " + getClassName() + " WHERE itemId = :itemId");
        query.setParameter("itemId", itemId);
        query.executeUpdate();
    }

}
