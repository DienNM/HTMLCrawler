package com.myprj.crawler.service.target;

import java.util.List;

import com.myprj.crawler.domain.target.ConsolidationAttributeData;
import com.myprj.crawler.model.target.ConsolidationAttributeId;

/**
 * @author DienNM (DEE)
 */

public interface ConsolidationAttributeService {
    
    ConsolidationAttributeData getById(ConsolidationAttributeId id);
    
    List<ConsolidationAttributeData> getByConsolidationId(String consolidationId);
}
