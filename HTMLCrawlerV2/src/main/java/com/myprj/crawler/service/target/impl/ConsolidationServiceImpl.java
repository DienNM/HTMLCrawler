package com.myprj.crawler.service.target.impl;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.myprj.crawler.domain.target.ConsolidationAttributeData;
import com.myprj.crawler.domain.target.ConsolidationData;
import com.myprj.crawler.model.target.ConsolidationAttributeModel;
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
    public ConsolidationData getById(long id) {
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
    public ConsolidationData getByKey(String key, String site) {
        ConsolidationModel productModel = consolidationRepository.findByKeyAndSite(key, site);
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
    public List<ConsolidationData> getByName(String name) {
        List<ConsolidationModel> productModels = consolidationRepository.findByName(name);
        List<ConsolidationData> productDatas = new ArrayList<ConsolidationData>();
        ConsolidationData.toDatas(productModels, productDatas);
        return productDatas;
    }

    private List<String> validateAndNormalizeProduct(ConsolidationData product) {
        List<String> errors = new ArrayList<String>();

        if (StringUtils.isEmpty(product.getResultKey())) {
            errors.add("Product Key is required");
        } else {
            product.setResultKey(product.getResultKey().trim());
        }

        if (StringUtils.isEmpty(product.getCategoryKey())) {
            errors.add("Product Category is required");
        } else {
            product.setCategoryKey(product.getCategoryKey().trim());
        }

        if (StringUtils.isEmpty(product.getSite())) {
            errors.add("Product Site is required");
        } else {
            product.setSite(product.getSite().trim());
        }
        return errors;
    }

    @Override
    @Transactional
    public ConsolidationData save(ConsolidationData product) {
        List<String> errors = validateAndNormalizeProduct(product);
        if (!errors.isEmpty()) {
            logger.error("Product cannot be saved. Errors: {}", errors);
            return null;
        }

        ConsolidationModel productModel = new ConsolidationModel();
        ConsolidationData.toModel(product, productModel);
        consolidationRepository.save(productModel);

        ConsolidationData persisted = new ConsolidationData();
        ConsolidationData.toData(productModel, persisted);

        List<ConsolidationAttributeData> productAttributeDatas = product.getAttributes();
        if (!productAttributeDatas.isEmpty()) {
            List<ConsolidationAttributeModel> productAttributeModels = new ArrayList<ConsolidationAttributeModel>();
            ConsolidationAttributeData.toModels(productAttributeDatas, productAttributeModels);
            consolidationAttributeRepository.save(productAttributeModels);

            List<ConsolidationAttributeData> productAttributePersisteds = new ArrayList<ConsolidationAttributeData>();
            ConsolidationAttributeData.toDatas(productAttributeModels, productAttributePersisteds);
            persisted.setAttributes(productAttributePersisteds);
        }
        return persisted;
    }

    @Override
    @Transactional
    public void update(ConsolidationData product) {
        ConsolidationModel productModel = consolidationRepository.find(product.getId());
        if (productModel == null) {
            throw new InvalidParameterException("Product " + product.getId() + " not found");
        }
        productModel = new ConsolidationModel();
        ConsolidationData.toModel(product, productModel);

        consolidationAttributeRepository.deleteByConsolidationId(product.getId());
        consolidationRepository.save(productModel);

        List<ConsolidationAttributeData> productAttributeDatas = product.getAttributes();
        if (!productAttributeDatas.isEmpty()) {
            List<ConsolidationAttributeModel> productAttributeModels = new ArrayList<ConsolidationAttributeModel>();
            ConsolidationAttributeData.toModels(productAttributeDatas, productAttributeModels);
            consolidationAttributeRepository.save(productAttributeModels);
        }
    }

    @Override
    public void populateAttributes(ConsolidationData productData) {
        List<ConsolidationAttributeData> productAttributeDatas = consolidationAttributeService.getByConsolidationId(productData.getId());
        productData.setAttributes(productAttributeDatas);
    }

}
