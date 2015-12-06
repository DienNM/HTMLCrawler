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
    public List<CrawlResultModel> findByItemId(long itemId) {
        StringBuilder queryStr = new StringBuilder("FROM " + getClassName());
        queryStr.append(" WHERE itemId = :itemId ");

        Query query = entityManager.createQuery(queryStr.toString(), getClazz());
        query.setParameter("itemId", itemId);

        return query.getResultList();
    }

}
