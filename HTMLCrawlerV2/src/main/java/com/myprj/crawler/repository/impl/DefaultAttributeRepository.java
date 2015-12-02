package com.myprj.crawler.repository.impl;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.myprj.crawler.model.config.AttributeModel;
import com.myprj.crawler.repository.AttributeRepository;

/**
 * @author DienNM (DEE)
 */

@SuppressWarnings("unchecked")
@Repository
public class DefaultAttributeRepository extends DefaultGenericDao<AttributeModel, String> implements AttributeRepository {

    @Override
    protected Class<AttributeModel> getTargetClass() {
        return AttributeModel.class;
    }

    @Override
    public List<AttributeModel> findChildren(String id) {
        Query query = entityManager.createQuery("FROM " + getClassName() + " WHERE parentId = :parentId");
        query.setParameter("parentId", id);
        return query.getResultList();
    }

    @Override
    public List<AttributeModel> findByItemId(long itemId) {
        Query query = entityManager.createQuery("FROM " + getClassName() + " WHERE itemId = :itemId");
        query.setParameter("itemId", itemId);
        return query.getResultList();
    }

    @Override
    public AttributeModel findRootByItemId(long itemId) {
        Query query = entityManager.createQuery("FROM " + getClassName() + 
                " WHERE itemId = :itemId AND root = :root ");
        query.setParameter("itemId", itemId);
        query.setParameter("root", true);
        List<AttributeModel> attributeModels = query.getResultList();
        if(attributeModels.isEmpty()) {
            return null;
        }
        return attributeModels.get(0);
    }

}
