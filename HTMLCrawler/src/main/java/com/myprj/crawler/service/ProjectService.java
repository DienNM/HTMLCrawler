package com.myprj.crawler.service;

import java.util.List;

import com.myprj.crawler.model.ProjectModel;
import com.myprj.crawler.repository.ProjectRepository;
import com.myprj.crawler.util.Page;
import com.myprj.crawler.util.Pageable;

/**
 * @author DienNM
 **/

public interface ProjectService {
    
    
    /**
     * Checking a projectName is existing
     * @param projectName Project Name that is already created
     * @return boolean
     */
    boolean exist(String projectName);
    
    /**
     * List all projects
     * @return List<Project>
     */
    List<ProjectModel> getAll();
    
    /**
     * List all projects by paging
     * @param pageable Pageable information
     * @return Page<ProjectModel>
     */
    Page<ProjectModel> getAll(Pageable pageable);
    
    /**
     * Load detail of Project
     * @param name Project Name
     * @return Project
     */
    ProjectModel getByName(String name);
    
    /**
     * Create a project with given name
     * @param name Project Name
     * @return Project
     * @throws Exception 
     */
    ProjectModel create(String name) throws Exception;
    
    /**
     * Update information of existing project
     * @param project Project
     */
    boolean update(ProjectModel project);
    
    void crawl(ProjectModel project);
    
    void setCrawlerService(CrawlerService crawlerService);
    
    CrawlerService getCrawlerService();
    
    void setProjectRepository(ProjectRepository projectRepository);
    
    void setProjectConfigurationStrategy(ProjectConfigurationStrategy projectConfigurationStrategy);
    
}
