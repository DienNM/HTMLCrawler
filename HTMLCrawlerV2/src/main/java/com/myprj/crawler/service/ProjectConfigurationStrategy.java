package com.myprj.crawler.service;

import com.myprj.crawler.domain.Site;
import com.myprj.crawler.exception.ProjectCrawlerException;
import com.myprj.crawler.model.ProjectModel;
import com.myprj.crawler.repository.ProxyRepository;

/**
 * @author DienNM (DEE)
 */

public interface ProjectConfigurationStrategy {
    
    void configure(ProjectModel projectModel) throws ProjectCrawlerException;
    
    Site loadProject2Site(ProjectModel project) throws ProjectCrawlerException;
    
    void setProxyRepository(ProxyRepository proxyRepository);
}
