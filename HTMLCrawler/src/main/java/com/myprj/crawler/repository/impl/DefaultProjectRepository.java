package com.myprj.crawler.repository.impl;

import static com.myprj.crawler.client.util.PathConstants.GLOBAL_PROJECTS;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.myprj.crawler.model.ProjectModel;
import com.myprj.crawler.repository.ProjectRepository;
import com.myprj.crawler.util.Page;
import com.myprj.crawler.util.Pageable;
import com.myprj.crawler.util.Serialization;
import com.myprj.crawler.util.StreamUtil;

/**
 * @author DienNM (DEE)
 */

public class DefaultProjectRepository implements ProjectRepository {
    
    private final Logger logger = LoggerFactory.getLogger(Logger.class);

    private static Map<String, ProjectModel> projectsCache = new HashMap<String, ProjectModel>();
    private static Map<String, String> projectNamesCache = new HashMap<String, String>();
    
    static {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(GLOBAL_PROJECTS))));
            String line = null;
            while ((line = br.readLine()) != null) {
                if(line == null || line.isEmpty()) {
                    continue;
                }
                ProjectModel project = Serialization.deserialize(line, ProjectModel.class);
                if(project == null) {
                    continue;
                }
                projectsCache.put(project.getName(), project);
                projectNamesCache.put(project.getName().toLowerCase(), project.getName());
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        } finally {
            IOUtils.closeQuietly(br);
        }
    }
    
    @Override
    public boolean exist(String name) {
        if(StringUtils.isEmpty(name)) {
            return false;
        }
        return projectNamesCache.containsKey(name.toLowerCase());
    }

    @Override
    public List<ProjectModel> findAll() {
        return new ArrayList<ProjectModel>(projectsCache.values());
    }

    @Override
    public Page<ProjectModel> findAll(Pageable pageable) {
        throw new UnsupportedOperationException("This operation has not supported yet");
    }

    @Override
    public ProjectModel find(String name) {
        if(StringUtils.isEmpty(name)) {
            return null;
        }
        String existingName = projectNamesCache.get(name.toLowerCase());
        if(existingName == null) {
            return null;
        }
        return projectsCache.get(existingName);
    }

    @Override
    public ProjectModel save(ProjectModel project) {
        
        projectsCache.put(project.getName(), project);
        projectNamesCache.put(project.getName().toLowerCase(), project.getName());
        
        StreamUtil.write(new File(GLOBAL_PROJECTS), Serialization.serialize(project), true);
        
        return project;
    }

    @Override
    public boolean update(ProjectModel project) {
        projectsCache.put(project.getName(), project);
        projectNamesCache.put(project.getName().toLowerCase(), project.getName());
        
        List<String> lines = new ArrayList<String>();
        for(ProjectModel proj : projectsCache.values()) {
            lines.add(Serialization.serialize(proj));
        }
        StreamUtil.write(new File(GLOBAL_PROJECTS), lines, false);
        return true;
    }

    @Override
    public boolean delete(String name) {
        if(StringUtils.isEmpty(name)) {
            throw new InvalidParameterException("Project Name is null");
        }
        if(!exist(name)) {
            logger.error("Project {} do not exist", name);
            return false;
        }
        String existingName = projectNamesCache.get(name.toLowerCase());
        projectNamesCache.remove(name.toLowerCase());
        projectsCache.remove(existingName);
        return true;
    }

}
