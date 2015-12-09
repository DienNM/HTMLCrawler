package com.myprj.crawler.web.facades;

import java.io.InputStream;
import java.util.List;

import com.myprj.crawler.domain.SiteData;

/**
 * @author DienNM (DEE)
 */

public interface SiteFacade {

    List<SiteData> loadSitesFromSource(InputStream inputStream);
    
}
