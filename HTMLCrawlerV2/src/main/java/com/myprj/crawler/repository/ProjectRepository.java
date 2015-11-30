package com.myprj.crawler.repository;

import java.util.List;

import com.myprj.crawler.model.ProjectModel;
import com.myprj.crawler.util.Page;
import com.myprj.crawler.util.Pageable;

/**
 * @author DienNM (DEE)
 */

public interface ProjectRepository {

    boolean exist(String name);

    List<ProjectModel> findAll();

    Page<ProjectModel> findAll(Pageable pageable);

    ProjectModel find(String name);

    ProjectModel save(ProjectModel project);

    boolean update(ProjectModel project);

    boolean delete(String name);

}
