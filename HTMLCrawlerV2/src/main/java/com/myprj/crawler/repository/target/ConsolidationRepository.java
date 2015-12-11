package com.myprj.crawler.repository.target;

import java.util.List;

import com.myprj.crawler.model.target.ConsolidationModel;
import com.myprj.crawler.repository.GenericDao;

/**
 * @author DienNM (DEE)
 */

public interface ConsolidationRepository extends GenericDao<ConsolidationModel, Long> {
    
    ConsolidationModel findByKeyAndSite(String key, String site);
    
    List<ConsolidationModel> findByName(String name);
    
    List<ConsolidationModel> findByCategoryAndSite(String category, String site);
}
