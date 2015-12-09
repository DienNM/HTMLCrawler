package com.myprj.crawler.service;

import java.util.List;
import java.util.Set;

import com.myprj.crawler.domain.SiteData;

/**
 * @author DienNM (DEE)
 */

public interface SiteService {
    
    SiteData get(String id);
    
    List<SiteData> getAll();
    
    Set<String> getAllIds();
    
    SiteData save(SiteData site);

    List<SiteData> saveOrUpdate(List<SiteData> siteDatas);
    
    void update(SiteData siteData);
    
    void delete(String id);
    
    void delele(List<String> ids);
    
}
