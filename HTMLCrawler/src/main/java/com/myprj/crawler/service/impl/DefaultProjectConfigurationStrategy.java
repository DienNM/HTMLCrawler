package com.myprj.crawler.service.impl;

import static com.myprj.crawler.client.util.PathConstants.CONFIG_FILE;
import static com.myprj.crawler.client.util.PathConstants.LINK_FILE;
import static com.myprj.crawler.client.util.PathConstants.PROJECTS;
import static com.myprj.crawler.client.util.PathConstants.PROJECT_CONFIG;
import static com.myprj.crawler.client.util.PathConstants.TEMPLATE_CONFIG;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.myprj.crawler.domain.Link;
import com.myprj.crawler.domain.Site;
import com.myprj.crawler.exception.ProjectCrawlerException;
import com.myprj.crawler.model.ProjectModel;
import com.myprj.crawler.util.Log;

/**
 * @author DienNM (DEE)
 */

public class DefaultProjectConfigurationStrategy extends AbstractProjectConfigurationStrategy {

    private final Logger logger = LoggerFactory.getLogger(Log.PROJECT_LOG);
    @Override
    public void configure(ProjectModel project) throws ProjectCrawlerException {
        // Create: data/projects/project_name
        File file = new File(PROJECTS + project.getName());
        if (!file.mkdirs()) {
            throw new ProjectCrawlerException("Cannot create folder: " + PROJECTS + project.getName());
        }
        makeProjectConfig(project);
        logger.info("Done for Project configuration: {}", project.getName());
    }

    private void makeProjectConfig(ProjectModel project) throws ProjectCrawlerException {
        String projectName = project.getName();
        String projectType = project.getType();
        try {
            // Copy from Template Configuration of each Project to target
            String templateConfig = String.format(TEMPLATE_CONFIG, projectType);
            String projectConfig = String.format(PROJECT_CONFIG, projectName);

            FileUtils.copyDirectory(new File(templateConfig), new File(projectConfig));
            logger.info("Copied {} to {}", templateConfig, projectConfig);

        } catch (Exception e) {
            throw new ProjectCrawlerException(e);
        }
    }

    @Override
    protected void loadConfigurationFile(ProjectModel project, Site site) throws Exception {
        InputStream is = null;
        try {
            String configFile = String.format(PROJECT_CONFIG + CONFIG_FILE, project.getName());
            is = new FileInputStream(configFile);
            Properties prop = new Properties();
            prop.load(is);

            site.setName(prop.getProperty("site"));
            site.setFromPage(Integer.valueOf(prop.getProperty("frompage")));
            site.setToPage(Integer.valueOf(prop.getProperty("topage")));
            site.setProject(project);

        } finally {
            IOUtils.closeQuietly(is);
        }
    }

    @Override
    protected void loadLinks(ProjectModel project, Site site) throws Exception {
        BufferedReader br = null;
        try {
            String linkFile = String.format(PROJECT_CONFIG + LINK_FILE, project.getName());
            br = new BufferedReader(new InputStreamReader(new FileInputStream(linkFile)));
            String line = null;
            while ((line = br.readLine()) != null) {
                Link link = new Link(line);
                link.setActive(true);
                
                link.setFromPage(site.getFromPage());
                link.setToPage(site.getToPage());
                
                site.getLinks().add(link);
            }
            
            loadActiveLinks(site);
            
        } finally {
            IOUtils.closeQuietly(br);
        }
    }

    @Override
    protected void loadProxies(ProjectModel project, Site site) throws Exception {
        site.setProxies(getProxyRepository().findByType(project.getType()));
    }

    private void loadActiveLinks(Site site) {
        ProjectModel project = site.getProject();
        String active = project.getActiveLink();
        List<Link> links = site.getLinks();
        
        // If no active links
        if (active == null || active.isEmpty()) {
            return;
        }

        // There is any active link
        for (int i = 0; i < links.size(); i++) {
            Link link = links.get(i);
            if (link.getLink().equals(active)) {
                link.setFromPage(project.getActivePage());
            } else {
                link.setActive(false);
            }
        }
    }
}
