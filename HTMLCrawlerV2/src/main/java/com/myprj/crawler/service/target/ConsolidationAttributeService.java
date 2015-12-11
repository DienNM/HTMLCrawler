package com.myprj.crawler.service.target;

import java.util.List;

import com.myprj.crawler.domain.target.ConsolidationAttributeData;

/**
 * @author DienNM (DEE)
 */

public interface ConsolidationAttributeService {
    
    List<ConsolidationAttributeData> getByConsolidationId(long consolidationId);
    
    ConsolidationAttributeData getById(long id);
    
    ConsolidationAttributeData get(String name, long consolidationId);
    
}
