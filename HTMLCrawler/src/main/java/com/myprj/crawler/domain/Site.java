package com.myprj.crawler.domain;

import java.util.ArrayList;
import java.util.List;

import com.myprj.crawler.model.ProjectModel;
import com.myprj.crawler.model.ProxyModel;

/**
 * @author DienNM (DEE)
 **/

public class Site {
    
    private String name;
    
    private int toPage;

    private int fromPage;

    private ProjectModel project;

    private List<Link> links;
    
    private List<ProxyModel> proxies;
    
    private ProxyTracer proxyTrack;
    
    public Site() {
        links = new ArrayList<Link>();
    }
    
    public boolean usingProxy() {
        return proxies != null && !proxies.isEmpty();
    }

    public List<Link> getLinks() {
        return links;
    }

    public void setLinks(List<Link> links) {
        this.links = links;
    }

    public int getFromPage() {
        return fromPage;
    }

    public void setFromPage(int fromPage) {
        this.fromPage = fromPage;
    }

    public int getToPage() {
        return toPage;
    }

    public void setToPage(int toPage) {
        this.toPage = toPage;
    }

    public ProjectModel getProject() {
        return project;
    }

    public void setProject(ProjectModel project) {
        this.project = project;
    }

    public List<ProxyModel> getProxies() {
        return proxies;
    }

    public void setProxies(List<ProxyModel> proxies) {
        this.proxies = proxies;
    }

    public ProxyTracer getProxyTrack() {
        return proxyTrack;
    }

    public void setProxyTrack(ProxyTracer proxyTrack) {
        this.proxyTrack = proxyTrack;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
