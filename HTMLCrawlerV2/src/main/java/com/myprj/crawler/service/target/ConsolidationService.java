package com.myprj.crawler.service.target;

import java.util.List;

import com.myprj.crawler.domain.target.ConsolidationData;

/**
 * @author DienNM (DEE)
 */

public interface ConsolidationService {

    ConsolidationData getById(long id);

    ConsolidationData getByKey(String key, String site);
    
    List<ConsolidationData> getByName(String name);

    List<ConsolidationData> getByCategory(String category, String site);

    ConsolidationData save(ConsolidationData consolidationData);

    void update(ConsolidationData consolidationData);
    
    void populateAttributes(ConsolidationData consolidationData);

}
