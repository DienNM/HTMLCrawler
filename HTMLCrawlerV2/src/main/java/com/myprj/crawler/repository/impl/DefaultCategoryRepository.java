package com.myprj.crawler.repository.impl;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.myprj.crawler.model.config.CategoryModel;
import com.myprj.crawler.repository.CategoryRepository;

/**
 * @author DienNM (DEE)
 */
@Repository
public class DefaultCategoryRepository extends DefaultGenericDao<CategoryModel, Long> implements CategoryRepository {

    @Override
    protected Class<CategoryModel> getTargetClass() {
        return CategoryModel.class;
    }

    @SuppressWarnings("unchecked")
    @Override
    public CategoryModel findByKey(String key) {
        StringBuilder queryStr = new StringBuilder("FROM " + getClassName());
        queryStr.append(" WHERE key = :key ");

        Query query = entityManager.createQuery(queryStr.toString(), getClazz());
        query.setParameter("key", key);

        List<CategoryModel> categoryModels = query.getResultList();
        if (categoryModels.isEmpty()) {
            return null;
        }

        return categoryModels.get(0);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<CategoryModel> findByParentKey(String parentKey) {
        StringBuilder queryStr = new StringBuilder("FROM " + getClassName());
        queryStr.append(" WHERE parentKey = :parentKey ");

        Query query = entityManager.createQuery(queryStr.toString(), getClazz());
        query.setParameter("parentKey", parentKey);

        return query.getResultList();
    }

    @Override
    public void updateParentKey(String oldParentKey, String newParentKey) {
        StringBuilder queryStr = new StringBuilder("UPDATE category ");
        queryStr.append(" set parent_key = :newParentKey ");
        queryStr.append(" WHERE parent_key = :oldParentKey ");

        Query query = entityManager.createNativeQuery(queryStr.toString(), getClazz());
        query.setParameter("newParentKey", newParentKey);
        query.setParameter("oldParentKey", oldParentKey);

        query.executeUpdate();
    }

}
