package com.myprj.crawler.repository.target;

import java.util.List;

import com.myprj.crawler.model.target.ConsolidationId;
import com.myprj.crawler.model.target.ConsolidationModel;
import com.myprj.crawler.repository.GenericDao;

/**
 * @author DienNM (DEE)
 */

public interface ConsolidationRepository extends GenericDao<ConsolidationModel, ConsolidationId> {
    
    List<ConsolidationModel> findByCategoryAndSite(String category, String site);
    
    ConsolidationModel findByMd5Key(String md5Key);
}
