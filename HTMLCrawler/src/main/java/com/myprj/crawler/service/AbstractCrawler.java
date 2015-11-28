package com.myprj.crawler.service;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.myprj.crawler.domain.Item;
import com.myprj.crawler.domain.Link;
import com.myprj.crawler.domain.ProxyTracer;
import com.myprj.crawler.domain.Site;
import com.myprj.crawler.enumeration.ProjectStatus;
import com.myprj.crawler.model.ProxyModel;
import com.myprj.crawler.util.HtmlLoader;
import com.myprj.crawler.util.Log;

/**
 * @author DienNM (DEE)
 **/

public abstract class AbstractCrawler<T> implements CrawlerService {

    private final Logger logger = LoggerFactory.getLogger(Log.CRAWLER_LOG);

    protected ExecutorService executorService;

    @Override
    public void destroy() {
        if (executorService != null) {
            executorService.shutdown();
        }
    }

    protected abstract String updateUrlFormat(String url);
    protected abstract String updateUrlBeforeCrawling(String url);
    
    protected abstract boolean isDownloadSuccess(Document document);

    protected abstract Set<String> getLinkForDetailInfo(Element body);
    protected abstract Item<T> getCrawledResult(String url, Document document);
    protected abstract void persistResult(Site site, List<Item<T>> items);
    
    @Override
    public void crawl(Site site) {
        
        ProxyTracer proxyTrack = new ProxyTracer();
        site.setProxyTrack(proxyTrack);

        for (Link outssiteLink : site.getLinks()) {
            if(!outssiteLink.isActive()) {
                continue;
            }
            
            site.getProject().setStatus(ProjectStatus.RUNNING);
            site.getProject().setActiveLink(outssiteLink.getLink());
            String linkTemplate = updateUrlFormat(outssiteLink.getLink());

            for (int currentPage = outssiteLink.getFromPage(); currentPage <= outssiteLink.getToPage(); currentPage++) {
                site.getProject().setActivePage(currentPage);
                String finalLink = String.format(linkTemplate, currentPage);
                logger.info("Start crawling: {}", finalLink);

                finalLink = updateUrlBeforeCrawling(finalLink);
                Document document = dowload(site, finalLink);
                
                Set<String> urlDetails = getLinkForDetailInfo(document.body());
                List<Future<Item<T>>> futureItems = new ArrayList<Future<Item<T>>>();

                for (String url : urlDetails) {
                    futureItems.add(executorService.submit(new CrawlDetail(site, url)));
                }

                List<Item<T>> items = new ArrayList<Item<T>>();
                for (Future<Item<T>> fitem : futureItems) {
                    Item<T> item;
                    try {
                        item = fitem.get();
                        if (item == null) {
                            continue;
                        }
                        items.add(item);
                    } catch (Exception e) {
                        logger.error("Error: {}", e);
                    }
                }

                if (!items.isEmpty()) {
                    System.out.println("\tCrawled: " + items.size());
                    persistResult(site, items);
                }
            }
        }
        site.getProject().setStatus(ProjectStatus.DONE);
    }
    
    protected Document dowload(Site site, String link) {
        if (site.usingProxy()) {
            switchProxy(site);
        }
        Document document = HtmlLoader.download(link);
        if(link == null || !isDownloadSuccess(document)) {
            logger.warn("Loaded url {} failed", link);
            return null;
        }
        return document;
    }

    protected Item<T> crawlDetail(Site site, String url) {
        Document document = dowload(site, url);
        if (document != null) {
            return getCrawledResult(url, document);
        }
        return null;
    }

    class CrawlDetail implements Callable<Item<T>> {

        private Site site;
        private String links;

        public CrawlDetail(Site site, String links) {
            this.site = site;
            this.links = links;
        }

        @Override
        public Item<T> call() throws Exception {
            return crawlDetail(site, links);
        }
    }

    private void switchProxy(Site site) {
        if (!site.usingProxy()) {
            return;
        }
        synchronized (site.getProxyTrack()) {
            ProxyTracer proxyTracker = site.getProxyTrack();
            if (proxyTracker.getCountDownloaded() < proxyTracker.getMaxDownloadTimes() 
                    && proxyTracker.getStart() != -1) {
                return;
            }
            List<ProxyModel> proxies = site.getProxies();
            while (!proxies.isEmpty()) {
                if (proxyTracker.getStart() >= proxies.size()) {
                    proxyTracker.setStart(-1);
                }
                proxyTracker.setStart(proxyTracker.getStart() + 1);
                ProxyModel proxy = proxies.get(proxyTracker.getStart());
                if (isReachableProxy(proxy)) {
                    System.setProperty("http.proxyHost", proxy.getIp());
                    System.setProperty("http.proxyPort", String.valueOf(proxy.getPort()));
                    logger.warn("Switch to proxy: " + proxy.getIp() + ":" + proxy.getPort());
                    proxyTracker.setCountDownloaded(0);
                    return;
                } else {
                    proxy.setReachable(false);
                    proxies.remove(proxyTracker.getStart());
                }
            }
        }

    }
    
    private boolean isReachableProxy(ProxyModel proxy) {
        if (!proxy.isReachable()) {
            return false;
        }
        try {
            InetAddress addr = InetAddress.getByName(proxy.getIp());
            return addr.isReachable(3000);
        } catch (Exception e) {
            logger.warn("Proxy Error: " + proxy.getIp() + " - " + e.getMessage());
            return false;
        }
    }

}
