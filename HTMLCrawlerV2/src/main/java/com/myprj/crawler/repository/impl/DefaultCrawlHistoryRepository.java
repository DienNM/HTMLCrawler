package com.myprj.crawler.repository.impl;

import java.util.Calendar;
import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.myprj.crawler.model.crawl.CrawlHistoryModel;
import com.myprj.crawler.repository.CrawlHistoryRepository;

/**
 * @author DienNM (DEE)
 */
@Repository
public class DefaultCrawlHistoryRepository extends DefaultGenericDao<CrawlHistoryModel, Long> implements
        CrawlHistoryRepository {

    @Override
    protected Class<CrawlHistoryModel> getTargetClass() {
        return CrawlHistoryModel.class;
    }

    @Override
    @Transactional
    public void save(CrawlHistoryModel crawlHistory) {
        CrawlHistoryModel latestCrawlHistory = findLatest(crawlHistory.getWorkerId());
        if (latestCrawlHistory != null) {
            latestCrawlHistory.setEolDate(Calendar.getInstance().getTimeInMillis());
            update(latestCrawlHistory);
        }
        super.save(crawlHistory);
    }

    @SuppressWarnings("unchecked")
    @Override
    public CrawlHistoryModel findLatest(long workerId) {
        StringBuilder queryStr = new StringBuilder("FROM  " + getClassName());
        queryStr.append(" WHERE workerId = :workerId AND eolDate = 0 ");

        Query query = entityManager.createQuery(queryStr.toString(), getClazz());
        query.setParameter("workerId", workerId);

        List<CrawlHistoryModel> list = query.getResultList();
        if (list.isEmpty()) {
            return null;
        }

        return list.get(0);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<CrawlHistoryModel> findByWorkerId(long workerId) {
        StringBuilder queryStr = new StringBuilder("FROM CrawlHistoryModel ");
        queryStr.append(" WHERE workerId = :workerId ");

        Query query = entityManager.createQuery(queryStr.toString(), getClazz());
        query.setParameter("workerId", workerId);

        return query.getResultList();
    }

}
