package com.myprj.crawler.repository.impl;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.myprj.crawler.model.config.ItemAttributeModel;
import com.myprj.crawler.repository.ItemAttributeRepository;

/**
 * @author DienNM (DEE)
 */
@Repository
public class DefaultItemAttributeRepository extends DefaultGenericDao<ItemAttributeModel, Long> implements ItemAttributeRepository{

    @Override
    protected Class<ItemAttributeModel> getTargetClass() {
        return ItemAttributeModel.class;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<ItemAttributeModel> findChildren(long id) {
        Query query = entityManager.createQuery("FROM " + getClassName() + " WHERE parentId = :parentId");
        query.setParameter("parentId", id);
        return query.getResultList();
    }

}
