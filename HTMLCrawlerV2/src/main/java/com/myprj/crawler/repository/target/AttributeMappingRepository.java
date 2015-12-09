package com.myprj.crawler.repository.target;

import org.springframework.stereotype.Repository;

import com.myprj.crawler.model.target.AttributeMappingId;
import com.myprj.crawler.model.target.AttributeMappingModel;
import com.myprj.crawler.repository.GenericDao;

/**
 * @author DienNM (DEE)
 */
@Repository
public interface AttributeMappingRepository extends GenericDao<AttributeMappingModel, AttributeMappingId> {
    
}
