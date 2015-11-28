package com.myprj.crawler.service.impl;

import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.myprj.crawler.domain.Site;
import com.myprj.crawler.exception.ProjectCrawlerException;
import com.myprj.crawler.model.ProjectModel;
import com.myprj.crawler.repository.ProxyRepository;
import com.myprj.crawler.service.ProjectConfigurationStrategy;
import com.myprj.crawler.util.Log;

/**
 * @author DienNM (DEE)
 */

public abstract class AbstractProjectConfigurationStrategy implements ProjectConfigurationStrategy {

    protected final Logger logger = LoggerFactory.getLogger(Log.PROJECT_LOG);
    
    protected ProxyRepository proxyRepository;

    @Override
    public Site loadProject2Site(ProjectModel project) throws ProjectCrawlerException {
        InputStream is = null;
        try {
            Site site = new Site();

            loadConfigurationFile(project, site);
            loadLinks(project, site);

            return site;
        } catch (Exception e) {
            logger.error("{}", e);
            throw new ProjectCrawlerException("Cannot load site from project name:" + project.getName());
        } finally {
            IOUtils.closeQuietly(is);
        }
    }

    protected abstract void loadConfigurationFile(ProjectModel project, Site site) throws Exception;

    protected abstract void loadLinks(ProjectModel project, Site site) throws Exception;
    
    protected abstract void loadProxies(ProjectModel project, Site site) throws Exception;
    
    @Override
    public void setProxyRepository(ProxyRepository proxyRepository) {
        this.proxyRepository = proxyRepository;
    }
    
    protected ProxyRepository getProxyRepository() {
        return this.proxyRepository;
    }

}
