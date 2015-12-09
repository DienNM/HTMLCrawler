package com.myprj.crawler.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author DienNM (DEE)
 */
@Entity
@Table(name = "site")
public class SiteModel extends AuditModel {

    private static final long serialVersionUID = 1L;
    
    @Id
    @Column(name = "site_key", length = 50)
    private String key;

    @Column(name = "name", length = 100)
    private String name;

    @Column(name = "url", length = 200)
    private String url;

    @Column(name = "description", length = 200)
    private String description;
    
    public SiteModel() {
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
