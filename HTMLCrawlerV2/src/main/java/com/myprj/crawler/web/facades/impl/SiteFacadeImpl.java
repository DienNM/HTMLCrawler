package com.myprj.crawler.web.facades.impl;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.myprj.crawler.domain.SiteData;
import com.myprj.crawler.web.facades.SiteFacade;

/**
 * @author DienNM (DEE)
 */
@Service
public class SiteFacadeImpl implements SiteFacade {

    private Logger logger = LoggerFactory.getLogger(SiteFacadeImpl.class);

    @Override
    public List<SiteData> loadSitesFromSource(InputStream inputStream) {
        List<SiteData> sites = new ArrayList<SiteData>();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(inputStream));
            String line = null;
            while ((line = br.readLine()) != null) {
                if (line == null || line.trim().isEmpty() || line.startsWith("#")) {
                    continue;
                }
                SiteData site = parseSite(line);
                if (site == null) {
                    return new ArrayList<SiteData>();
                }
                sites.add(site);
            }
        } catch (Exception ex) {
            logger.error("Error loading Sites. Error: {}", ex);
            return new ArrayList<SiteData>();
        } finally {
            IOUtils.closeQuietly(br);
            IOUtils.closeQuietly(inputStream);
        }
        return sites;
    }

    private SiteData parseSite(String line) {
        try {
            String[] elements = line.split(Pattern.quote("|"));
            SiteData site = new SiteData();
            site.setKey(elements[0]);
            site.setName(elements[1]);
            site.setUrl(elements[2]);
            site.setDescription(elements[3]);
            return site;
        } catch (Exception e) {
            logger.error("Error parsing Line: {} to Site. Error: {}", line, e);
            return null;
        }
    }

}
