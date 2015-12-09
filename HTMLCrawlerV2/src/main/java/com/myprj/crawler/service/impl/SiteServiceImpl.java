package com.myprj.crawler.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
    
    @Autowired
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
    public Set<String> getAllIds() {
        List<SiteModel> siteModels = siteRepository.findAll();
        Set<String> ids = new HashSet<String>();
        for(SiteModel siteModel : siteModels) {
            ids.add(siteModel.getKey());
        }
        return ids;
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
    public List<SiteData> saveOrUpdate(List<SiteData> siteDatas) {
        List<SiteData> persistedSites = new ArrayList<SiteData>();
        for (SiteData site : siteDatas) {
            SiteModel siteModel = siteRepository.find(site.getKey());
            if (siteModel == null) {
                siteModel = new SiteModel();
                SiteData.toModel(site, siteModel);
                siteRepository.save(siteModel);
            } else {
                siteModel.setName(site.getName());
                siteModel.setDescription(site.getDescription());
                siteModel.setUrl(site.getUrl());
                siteRepository.update(siteModel);
            }
            SiteData persisted = new SiteData();
            SiteData.toData(siteModel, persisted);
            persistedSites.add(persisted);
        }
        return persistedSites;
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
