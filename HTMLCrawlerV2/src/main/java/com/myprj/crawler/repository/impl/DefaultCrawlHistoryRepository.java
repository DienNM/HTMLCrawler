package com.myprj.crawler.repository.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.myprj.crawler.model.crawl.CrawlHistoryModel;
import com.myprj.crawler.repository.CrawlHistoryRepository;

/**
 * @author DienNM (DEE)
 */

public class DefaultCrawlHistoryRepository implements CrawlHistoryRepository {
    
    private static Map<Long, CrawlHistoryModel> repo = new HashMap<Long, CrawlHistoryModel>();
    
    @Override
    public CrawlHistoryModel save(CrawlHistoryModel crawlHistory) {
        if(repo.containsKey(crawlHistory.getId())) {
            repo.put(crawlHistory.getId(), crawlHistory);
            return crawlHistory;
        }
        for(CrawlHistoryModel history : repo.values()) {
            if(history.getWorkerId() == crawlHistory.getId() && history.getEolDate() == 0) {
                history.setEolDate(Calendar.getInstance().getTimeInMillis());
            }
        }
        return crawlHistory;
    }

    @Override
    public CrawlHistoryModel findLatest(long workerId) {
        for(CrawlHistoryModel crawlHistory : repo.values()) {
            if(crawlHistory.getWorkerId() == workerId && crawlHistory.getEolDate() == 0) {
                return crawlHistory;
            }
        }
        return null;
    }

    @Override
    public List<CrawlHistoryModel> findByWorkerId(long workerId) {
        List<CrawlHistoryModel> crawlHistories = new ArrayList<CrawlHistoryModel>();
        for(CrawlHistoryModel crawlHistory : repo.values()) {
            if(crawlHistory.getWorkerId() == workerId) {
                crawlHistories.add(crawlHistory);
            }
        }
        return crawlHistories;
    }

}
