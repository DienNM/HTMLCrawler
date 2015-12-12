package com.myprj.crawler.service.target;

import java.util.List;

import com.myprj.crawler.domain.target.ConsolidationData;
import com.myprj.crawler.model.target.ConsolidationId;

/**
 * @author DienNM (DEE)
 */

public interface ConsolidationService {

    ConsolidationData getById(ConsolidationId id);

    ConsolidationData getByMd5Key(String md5Key);

    List<ConsolidationData> getByCategory(String category, String site);

    void saveOrUpdate(ConsolidationData consolidationData);

    void populateAttributes(ConsolidationData consolidationData);

}
