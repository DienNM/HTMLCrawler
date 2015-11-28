package com.myprj.crawler.service;

import java.security.InvalidParameterException;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.myprj.crawler.domain.Site;
import com.myprj.crawler.exception.ProjectCrawlerException;
import com.myprj.crawler.model.ProjectModel;
import com.myprj.crawler.repository.ProjectRepository;
import com.myprj.crawler.util.Page;
import com.myprj.crawler.util.Pageable;

/**
 * @author DienNM (DEE)
 **/

public abstract class AbstractProjectSupport implements ProjectService {

    private final Logger logger = LoggerFactory.getLogger(ProjectService.class);

    private CrawlerService crawlerService;

    private ProjectRepository projectRepository;

    private ProjectConfigurationStrategy projectConfigurationStrategy;

    protected abstract String getProjectType();

    @Override
    public boolean exist(String name) {
        return projectRepository.exist(name);
    }

    @Override
    public Page<ProjectModel> getAll(Pageable pageable) {
        return projectRepository.findAll(pageable);
    }

    @Override
    public List<ProjectModel> getAll() {
        return projectRepository.findAll();
    }

    @Override
    public ProjectModel getByName(String name) {
        return projectRepository.find(name);
    }

    @Override
    public ProjectModel create(String name) throws Exception {
        if (StringUtils.isEmpty(name)) {
            throw new InvalidParameterException("Project name is empty");
        }
        if (exist(name)) {
            logger.error("Project {} already exists", name);
            return null;
        }

        ProjectModel project = new ProjectModel();
        project.setName(name);
        project.setCreatedAt(Calendar.getInstance().getTime());
        project.setType(getProjectType());

        try {
            projectConfigurationStrategy.configure(project);
            ProjectModel persistedProject = projectRepository.save(project);
            return persistedProject;
        } catch (ProjectCrawlerException e) {
            return null;
        }
    }

    @Override
    public synchronized boolean update(ProjectModel project) {
        if (project == null || StringUtils.isEmpty(project.getName())) {
            throw new InvalidParameterException("Project or Project Name is null");
        }
        if (!exist(project.getName())) {
            logger.error("Project {} do not exist", project.getName());
        }
        return projectRepository.update(project);
    }

    @Override
    public void crawl(ProjectModel project) {
        if (project.getActiveLink() != null) {
            logger.info("RESUME Crawling project: {}. Page = {}, Link = {}", project.getName(),
                    project.getActiveLink(), project.getActivePage());
        } else {
            logger.info("START Crawling project: {}", project.getName());
        }
        Site site;
        try {
            site = projectConfigurationStrategy.loadProject2Site(project);
            crawlerService.crawl(site);
        } catch (ProjectCrawlerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setCrawlerService(CrawlerService crawlerService) {
        this.crawlerService = crawlerService;
    }

    @Override
    public CrawlerService getCrawlerService() {
        return this.crawlerService;
    }

    @Override
    public void setProjectRepository(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Override
    public void setProjectConfigurationStrategy(ProjectConfigurationStrategy projectConfigurationStrategy) {
        this.projectConfigurationStrategy = projectConfigurationStrategy;
    }
}
