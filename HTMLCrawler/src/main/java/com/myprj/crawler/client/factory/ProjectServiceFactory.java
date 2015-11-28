package com.myprj.crawler.client.factory;

import com.myprj.crawler.client.util.ProjectType;
import com.myprj.crawler.service.CrawlerService;
import com.myprj.crawler.service.ProjectService;
import com.myprj.crawler.service.impl.ChototCrawlerService;
import com.myprj.crawler.service.impl.ChototProject;
import com.myprj.crawler.service.impl.LazadaCrawlerService;
import com.myprj.crawler.service.impl.LazadaProject;

/**
 * @author DienNM (DEE)
 */

public final class ProjectServiceFactory {
    
    public static ProjectService createProjectService(ProjectType projectType) {
        ProjectService projectService = null;
        CrawlerService crawlerService = null;
        switch (projectType) {
        case chotot:
            crawlerService = new ChototCrawlerService();
            projectService = new ChototProject();
            break;
        case lazada:
            crawlerService = new LazadaCrawlerService();
            projectService = new LazadaProject();
            break;
        default:
            break;
        }
        if(crawlerService != null) {
            crawlerService.init();
        }
        if(projectService != null) {
            projectService.setCrawlerService(crawlerService);
        }
        return projectService;
    }
}
