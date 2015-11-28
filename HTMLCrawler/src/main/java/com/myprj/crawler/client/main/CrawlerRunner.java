package com.myprj.crawler.client.main;

import java.util.Calendar;
import java.util.List;
import java.util.Scanner;

import com.myprj.crawler.client.factory.ProjectServiceFactory;
import com.myprj.crawler.client.repository.impl.DefaultProjectRepository;
import com.myprj.crawler.client.repository.impl.DefaultProxyRepository;
import com.myprj.crawler.client.util.ProjectType;
import com.myprj.crawler.enumeration.ProjectStatus;
import com.myprj.crawler.model.ProjectModel;
import com.myprj.crawler.repository.ProjectRepository;
import com.myprj.crawler.repository.ProxyRepository;
import com.myprj.crawler.service.ProjectConfigurationStrategy;
import com.myprj.crawler.service.ProjectService;
import com.myprj.crawler.service.impl.DefaultProjectConfigurationStrategy;

/**
 * @author DienNM (DEE)
 **/

public class CrawlerRunner {

    ProjectModel project = null;
    String action = null;
    ProjectService projectService = null;
    
    private void loadDependencies(ProjectType projectType) {
        
        ProjectRepository projectRepository = new DefaultProjectRepository();
        ProxyRepository proxyRepository = new DefaultProxyRepository();
        
        ProjectConfigurationStrategy projectConfigurationStrategy = new DefaultProjectConfigurationStrategy();
        projectConfigurationStrategy.setProxyRepository(proxyRepository);
        
        projectService = ProjectServiceFactory.createProjectService(projectType);
        projectService.setProjectRepository(projectRepository);
        projectService.setProjectConfigurationStrategy(projectConfigurationStrategy);
    }

    public static void main(final String[] args) throws Exception {
        CrawlerRunner crawlerRunner = new CrawlerRunner();
        
        ShutdownInterceptor shutdownInterceptor = new ShutdownInterceptor(crawlerRunner);
        Runtime.getRuntime().addShutdownHook(shutdownInterceptor);

        crawlerRunner.action = args[0];
        ProjectType projectType = ProjectType.valueOf(args[1]);
        
        crawlerRunner.loadDependencies(projectType);

        if ("list".equalsIgnoreCase(crawlerRunner.action)) {
            crawlerRunner.handleListProjects(projectType);
        } else if ("create".equalsIgnoreCase(crawlerRunner.action)) {
            crawlerRunner.handleCreateProject(projectType);
        } else if ("crawl".equalsIgnoreCase(crawlerRunner.action)) {
            crawlerRunner.handleCrawlProject(projectType);
        }
    }
    
    

    // Handle Get Project Name for creating
    public void handleCreateProject(ProjectType type) throws Exception {
        System.out.println("\tCREATE PROJECT - " + type);
        System.out.println("\t--------------------");
        String projectName;

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("\t\tEnter Project Name: ");
            projectName = scanner.next();
            if (projectName == null || projectName.isEmpty()) {
                System.out.println("Error!");
                continue;
            }
            if (projectService.exist(projectName)) {
                System.out.println();
                System.out.println("\tError! Project Name is already existing");
                System.out.println();
                continue;
            }
            scanner.close();
            break;
        }

        projectService.create(projectName);
        System.out.println("Create project: " + projectName);
        System.out.println("Go to project detail to update your links and configurations");
        exit();
    }

    /**
     * List all projects by type
     */
    public void handleListProjects(ProjectType type) throws Exception {
        List<ProjectModel> projects = projectService.getAll();
        System.out.println();
        System.out.println("List of Projects: " + type);
        System.out.println("===========================================================");

        for (ProjectModel project : projects) {
            System.out.println();

            System.out.println("\tProject: " + project.getName());
            System.out.println("\t\t: Type: " + project.getType());
            System.out.println("\t\t: Created: " + project.getCreatedAt());
            System.out.println("\t\t: Status: " + project.getStatus());
        }

        System.out.println();
        System.out.println("===========================================================");
        exit();
    }

    public void handleCrawlProject(ProjectType type) throws Exception {
        System.out.println();
        System.out.println("\tCRAWLING PROJECT: " + type);
        System.out.println("\t--------------------");
        System.out.println();
        String projectName;

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("\t\tEnter Project Name: ");
            projectName = scanner.next();
            if (!projectService.exist(projectName)) {
                System.out.println();
                System.out.println("Error! NOT Exist");
                System.out.println();
                continue;
            }
            scanner.close();
            break;
        }

        project = projectService.getByName(projectName);

        System.out.println();
        System.out.println("Project Info: ");
        System.out.println("-----------------------------------------------------");
        System.out.println("\tProject: " + project.getName());
        System.out.println("\t\t: Type: " + project.getType());
        System.out.println("\t\t: Created: " + project.getCreatedAt());
        System.out.println("\t\t: Status: " + project.getStatus());
        System.out.println("-----------------------------------------------------");
        System.out.println();

        projectService.crawl(project);
        projectService.getCrawlerService().destroy();
        
        exit();
    }

    public synchronized void shutDown() {
        if ("crawl".equalsIgnoreCase(action) && project != null) {
            if (project.getStatus() == ProjectStatus.DONE) {
                project.setActiveLink("-1");
                project.setActiveLink("");
            } else {
                project.setStatus(ProjectStatus.RESUME);
            }
            project.setUpdatedAt(Calendar.getInstance().getTime());
            projectService.update(project);
        }
    }

    private void exit() throws Exception {
        System.out.println();
        System.out.print("\tExit after 10 seconds");
        Thread.sleep(5000);
        System.exit(0);
    }
}
