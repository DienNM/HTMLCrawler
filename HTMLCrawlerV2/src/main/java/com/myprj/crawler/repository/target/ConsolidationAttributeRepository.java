package com.myprj.crawler.repository.target;

import java.util.List;

import com.myprj.crawler.model.target.ConsolidationAttributeModel;
import com.myprj.crawler.repository.GenericDao;

/**
 * @author DienNM (DEE)
 */

public interface ConsolidationAttributeRepository extends GenericDao<ConsolidationAttributeModel, Long> {
    
    List<ConsolidationAttributeModel> findByConsolidationId(long consolidationId);

    ConsolidationAttributeModel findByNameAndConsolidationId(String name, long consolidationId);
    
    void deleteByConsolidationId(long consolidationId);
}
