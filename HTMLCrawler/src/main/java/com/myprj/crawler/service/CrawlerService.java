package com.myprj.crawler.service;

import com.myprj.crawler.domain.Site;
/**
 * @author DienNM (DEE)
 **/
public interface CrawlerService {
    
    void crawl(Site site);
    
    void init();
    
    void destroy();
    
}
