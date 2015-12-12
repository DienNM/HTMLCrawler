package com.myprj.crawler.service.target.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.myprj.crawler.domain.target.ConsolidationAttributeData;
import com.myprj.crawler.domain.target.ConsolidationData;
import com.myprj.crawler.model.target.ConsolidationAttributeModel;
import com.myprj.crawler.model.target.ConsolidationId;
import com.myprj.crawler.model.target.ConsolidationModel;
import com.myprj.crawler.repository.target.ConsolidationAttributeRepository;
import com.myprj.crawler.repository.target.ConsolidationRepository;
import com.myprj.crawler.service.target.ConsolidationAttributeService;
import com.myprj.crawler.service.target.ConsolidationService;

/**
 * @author DienNM (DEE)
 */
@Service
public class ConsolidationServiceImpl implements ConsolidationService {

    private Logger logger = LoggerFactory.getLogger(ConsolidationServiceImpl.class);

    @Autowired
    private ConsolidationRepository consolidationRepository;

    @Autowired
    private ConsolidationAttributeRepository consolidationAttributeRepository;

    @Autowired
    private ConsolidationAttributeService consolidationAttributeService;

    @Override
    public ConsolidationData getById(ConsolidationId id) {
        ConsolidationModel productModel = consolidationRepository.find(id);
        if (productModel == null) {
            return null;
        }
        ConsolidationData productData = new ConsolidationData();
        ConsolidationData.toData(productModel, productData);

        populateAttributes(productData);

        return productData;
    }

    @Override
    public ConsolidationData getByMd5Key(String md5Key) {
        ConsolidationModel productModel = consolidationRepository.findByMd5Key(md5Key);
        if (productModel == null) {
            return null;
        }
        ConsolidationData productData = new ConsolidationData();
        ConsolidationData.toData(productModel, productData);

        populateAttributes(productData);

        return productData;
    }

    @Override
    public List<ConsolidationData> getByCategory(String category, String site) {
        List<ConsolidationModel> productModels = consolidationRepository.findByCategoryAndSite(category, site);
        List<ConsolidationData> productDatas = new ArrayList<ConsolidationData>();
        ConsolidationData.toDatas(productModels, productDatas);
        return productDatas;
    }

    @Override
    @Transactional
    public void saveOrUpdate(ConsolidationData consolidation) {
        ConsolidationModel newObject = new ConsolidationModel();
        ConsolidationData.toModel(consolidation, newObject);

        ConsolidationModel dbModel = consolidationRepository.find(newObject.getId());
        if (dbModel == null) {
            consolidationRepository.save(newObject);
        } else {
            if (dbModel.getMd5Attributes().equals(newObject.getMd5Attributes())) {
                logger.info(dbModel.getId().toString() + " not change. Not updated");
                return;
            }
            dbModel.setName(newObject.getName());
            dbModel.setUrl(newObject.getUrl());
            consolidationRepository.update(dbModel);
            consolidationAttributeRepository.deleteByConsolidationId(dbModel.getMd5Key());
        }
        List<ConsolidationAttributeData> attributes = consolidation.getAttributes();
        if (!attributes.isEmpty()) {
            List<ConsolidationAttributeModel> attributeModels = new ArrayList<ConsolidationAttributeModel>();
            ConsolidationAttributeData.toModels(attributes, attributeModels);
            consolidationAttributeRepository.save(attributeModels);
        }
    }

    @Override
    public void populateAttributes(ConsolidationData consolidationData) {
        List<ConsolidationAttributeData> productAttributeDatas = consolidationAttributeService
                .getByConsolidationId(consolidationData.getMd5Key());
        consolidationData.setAttributes(productAttributeDatas);
    }

}
