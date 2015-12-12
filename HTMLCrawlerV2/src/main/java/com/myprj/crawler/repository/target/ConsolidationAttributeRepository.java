package com.myprj.crawler.repository.target;

import java.util.List;

import com.myprj.crawler.model.target.ConsolidationAttributeId;
import com.myprj.crawler.model.target.ConsolidationAttributeModel;
import com.myprj.crawler.repository.GenericDao;

/**
 * @author DienNM (DEE)
 */

public interface ConsolidationAttributeRepository extends GenericDao<ConsolidationAttributeModel, ConsolidationAttributeId> {
    
    List<ConsolidationAttributeModel> findByConsolidationId(String consolidationId);

    void deleteByConsolidationId(String consolidationId);
}
