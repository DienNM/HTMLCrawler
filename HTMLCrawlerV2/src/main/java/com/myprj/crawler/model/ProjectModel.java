package com.myprj.crawler.model;

import static com.myprj.crawler.enumeration.ProjectStatus.CREATED;

import java.io.Serializable;
import java.util.Date;

import com.myprj.crawler.enumeration.ProjectStatus;

/**
 * @author DienNM (DEE)
 **/

public class ProjectModel implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;

    private String type;

    private ProjectStatus status = CREATED;

    private int activePage = -1;

    private String activeLink;

    private Date createdAt;

    private String createdBy;

    private Date updatedAt;

    private String updatedBy;

    public ProjectModel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getActivePage() {
        return activePage;
    }

    public void setActivePage(int activePage) {
        this.activePage = activePage;
    }

    public String getActiveLink() {
        return activeLink;
    }

    public void setActiveLink(String activeLink) {
        this.activeLink = activeLink;
    }

    public ProjectStatus getStatus() {
        return status;
    }

    public void setStatus(ProjectStatus status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

}
