package com.myprj.crawler.repository.impl;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.myprj.crawler.model.crawl.CrawlResultModel;
import com.myprj.crawler.repository.CrawlResultRepository;

/**
 * @author DienNM (DEE)
 */
@Repository
public class DefaultCrawlResultRepository extends DefaultGenericDao<CrawlResultModel, Long> implements
        CrawlResultRepository {

    @Override
    protected Class<CrawlResultModel> getTargetClass() {
        return CrawlResultModel.class;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<CrawlResultModel> findByItemKey(String itemKey) {
        StringBuilder queryStr = new StringBuilder("FROM " + getClassName());
        queryStr.append(" WHERE itemKey = :itemKey ");

        Query query = entityManager.createQuery(queryStr.toString(), getClazz());
        query.setParameter("itemKey", itemKey);

        return query.getResultList();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<CrawlResultModel> findByCategoryKey(String categoryKey) {
        StringBuilder queryStr = new StringBuilder("FROM " + getClassName());
        queryStr.append(" WHERE categoryKey = :categoryKey ");

        Query query = entityManager.createQuery(queryStr.toString(), getClazz());
        query.setParameter("categoryKey", categoryKey);

        return query.getResultList();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<CrawlResultModel> findByRequestId(String requestId) {
        StringBuilder queryStr = new StringBuilder("FROM " + getClassName());
        queryStr.append(" WHERE requestId = :requestId ");

        Query query = entityManager.createQuery(queryStr.toString(), getClazz());
        query.setParameter("requestId", requestId);

        return query.getResultList();
    }

    @SuppressWarnings("unchecked")
    @Override
    public CrawlResultModel findByResultKey(String resultKey, String siteKey, String categoryKey, String itemKey) {
        StringBuilder queryStr = new StringBuilder("FROM " + getClassName());
        queryStr.append(" WHERE resultKey = :resultKey AND ");
        queryStr.append(" siteKey = :siteKey AND ");
        queryStr.append(" categoryKey = :categoryKey AND ");
        queryStr.append(" itemKey = :itemKey ");

        Query query = entityManager.createQuery(queryStr.toString(), getClazz());
        query.setParameter("resultKey", resultKey);
        query.setParameter("siteKey", siteKey);
        query.setParameter("categoryKey", categoryKey);
        query.setParameter("itemKey", itemKey);

        List<CrawlResultModel> results = query.getResultList();
        if(results.isEmpty()) {
            return null;
        }
        return results.get(0);
    }

}
