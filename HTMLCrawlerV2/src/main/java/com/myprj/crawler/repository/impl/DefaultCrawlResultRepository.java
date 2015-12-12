package com.myprj.crawler.repository.impl;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.myprj.crawler.model.crawl.CrawlResultId;
import com.myprj.crawler.model.crawl.CrawlResultModel;
import com.myprj.crawler.repository.CrawlResultRepository;

/**
 * @author DienNM (DEE)
 */
@Repository
public class DefaultCrawlResultRepository extends DefaultGenericDao<CrawlResultModel, CrawlResultId> implements
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

}
