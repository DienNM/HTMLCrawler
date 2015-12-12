package com.myprj.crawler.repository.impl;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.myprj.crawler.domain.PageResult;
import com.myprj.crawler.domain.Pageable;
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
    public List<CrawlResultModel> find(String siteKey, String categoryKey, String itemKey, Pageable pageable) {
        StringBuilder queryStr = new StringBuilder("FROM " + getClassName());
        queryStr.append(" WHERE siteKey = :siteKey AND "
                + " categoryKey = :categoryKey AND "
                + " itemKey = :itemKey ");

        Query query = entityManager.createQuery(queryStr.toString(), getClazz());
        query.setParameter("siteKey", categoryKey);
        query.setParameter("categoryKey", categoryKey);
        query.setParameter("itemKey", categoryKey);
        
        query.setFirstResult(pageable.getCurrentPage() * pageable.getPageSize());
        query.setMaxResults(pageable.getPageSize());
        
        return query.getResultList();
    }

    @SuppressWarnings("unchecked")
    @Override
    public PageResult<CrawlResultModel> findPaging(String siteKey, String categoryKey, String itemKey, Pageable pageable) {
        StringBuilder selectQuery = new StringBuilder("FROM " + getClassName());
        StringBuilder countQuery = new StringBuilder("SELECT count(*) FROM " + getClassName());
        String whereClause =  " WHERE siteKey = :siteKey AND "
                + " categoryKey = :categoryKey AND "
                + " itemKey = :itemKey ";
        
        selectQuery.append(whereClause);
        countQuery.append(whereClause);
        
        Query query = entityManager.createQuery(selectQuery.toString(), getClazz());
        query.setParameter("siteKey", categoryKey);
        query.setParameter("categoryKey", categoryKey);
        query.setParameter("itemKey", categoryKey);
        
        query.setFirstResult(pageable.getCurrentPage() * pageable.getPageSize());
        query.setMaxResults(pageable.getPageSize());
        
        List<CrawlResultModel> results = query.getResultList();
        
        // Count
        Object obj = entityManager.createQuery(countQuery.toString()).getSingleResult();
        long totalRecords = Long.valueOf(obj.toString());
        long totalPages = totalRecords / pageable.getPageSize();
        if(totalRecords % pageable.getPageSize() != 0 ) {
            totalPages += 1;
        }
        
        Pageable resultPageable = new Pageable(pageable);
        resultPageable.setTotalPages(totalPages);
        resultPageable.setTotalRecords(totalRecords);
        
        PageResult<CrawlResultModel> pageResult = new PageResult<>(resultPageable);
        pageResult.setContent(results);
        
        return pageResult;
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
