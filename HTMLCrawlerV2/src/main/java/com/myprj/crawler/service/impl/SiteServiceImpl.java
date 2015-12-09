package com.myprj.crawler.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.myprj.crawler.domain.SiteData;
import com.myprj.crawler.model.SiteModel;
import com.myprj.crawler.repository.SiteRepository;
import com.myprj.crawler.service.SiteService;

/**
 * @author DienNM (DEE)
 */
@Service
public class SiteServiceImpl implements SiteService {
    
    private Logger logger = LoggerFactory.getLogger(SiteServiceImpl.class);
    
    private SiteRepository siteRepository;
    
    @Override
    public SiteData get(String id) {
        SiteModel siteModel = siteRepository.find(id);
        if(siteModel == null) {
            return null;
        }
        
        SiteData siteData = new SiteData();
        SiteData.toData(siteModel, siteData);
        
        return siteData;
    }

    @Override
    public List<SiteData> getAll() {
        List<SiteModel> siteModels = siteRepository.findAll();
        List<SiteData> siteDatas = new ArrayList<SiteData>();
        SiteData.toDatas(siteModels, siteDatas);
        return siteDatas;
    }

    @Override
    @Transactional
    public SiteData save(SiteData site) {
        SiteModel siteModel = siteRepository.find(site.getKey());
        if(siteModel != null) {
            logger.error("Site " + site.getKey() + " already exists");
            return null;
        }
        siteModel = new SiteModel();
        SiteData.toModel(site, siteModel);
        siteRepository.save(siteModel);
        
        SiteData siteData = new SiteData();
        SiteData.toData(siteModel, siteData);
        
        return siteData;
    }

    @Override
    @Transactional
    public void update(SiteData siteData) {
        SiteModel siteModel = siteRepository.find(siteData.getKey());
        if(siteModel == null) {
            logger.error("Site " + siteData.getKey() + " not found");
            return;
        }
        siteModel = new SiteModel();
        SiteData.toModel(siteData, siteModel);
        
        siteRepository.update(siteModel);
    }

    @Override
    @Transactional
    public void delete(String id) {
        SiteModel siteModel = siteRepository.find(id);
        if(siteModel == null) {
            logger.error("Site " + id + " not found");
            return;
        }
        siteRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void delele(List<String> ids) {
        for(String id : ids) {
            SiteModel siteModel = siteRepository.find(id);
            if(siteModel == null) {
                logger.error("Site " + id + " not found");
                return;
            }
        }
        siteRepository.deleteByIds(ids);
    }
}
