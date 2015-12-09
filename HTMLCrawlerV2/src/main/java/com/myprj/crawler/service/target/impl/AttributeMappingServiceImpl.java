package com.myprj.crawler.service.target.impl;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.myprj.crawler.domain.target.AttributeMappingData;
import com.myprj.crawler.model.target.AttributeMappingId;
import com.myprj.crawler.model.target.AttributeMappingModel;
import com.myprj.crawler.repository.target.AttributeMappingRepository;
import com.myprj.crawler.service.target.AttributeMappingService;

/**
 * @author DienNM (DEE)
 */
@Service
public class AttributeMappingServiceImpl implements AttributeMappingService {

    @Autowired
    private AttributeMappingRepository attributeMappingRepository;

    @Override
    @Transactional
    public AttributeMappingData save(AttributeMappingData attributeMappingData) {
        AttributeMappingModel attributeMappingModel = new AttributeMappingModel();
        AttributeMappingData.toModel(attributeMappingData, attributeMappingModel);

        attributeMappingRepository.save(attributeMappingModel);
        AttributeMappingData persisted = new AttributeMappingData();
        AttributeMappingData.toData(attributeMappingModel, persisted);

        return persisted;
    }

    @Override
    @Transactional
    public List<AttributeMappingData> saveOrUpdate(List<AttributeMappingData> attributeMappingDatas) {
        List<AttributeMappingData> persistedAttributes = new ArrayList<AttributeMappingData>();
        for (AttributeMappingData attribute : attributeMappingDatas) {
            AttributeMappingModel newAttributeModel = new AttributeMappingModel();
            AttributeMappingData.toModel(attribute, newAttributeModel);

            AttributeMappingModel attributeModel = attributeMappingRepository.find(newAttributeModel.getId());
            if (attributeModel == null) {
                attributeMappingRepository.save(newAttributeModel);
            } else {
                attributeModel.setMappingStrategy(attribute.getMappingStrategy());
                attributeModel.setValueMapping(attribute.getValueMapping());
                attributeMappingRepository.update(attributeModel);
            }
            AttributeMappingData persisted = new AttributeMappingData();
            AttributeMappingData.toData(attributeModel, persisted);
            persistedAttributes.add(persisted);
        }
        return persistedAttributes;

    }

    @Override
    @Transactional
    public void update(AttributeMappingData attributeMappingData) {
        AttributeMappingModel newObject = new AttributeMappingModel();
        AttributeMappingData.toModel(attributeMappingData, newObject);

        AttributeMappingModel attributeMappingModel = attributeMappingRepository.find(newObject.getId());
        if (attributeMappingModel == null) {
            throw new InvalidParameterException(String.format(
                    "Attribute Mapping: [site=%, category=%s, item=%s, name=%s] not found",
                    attributeMappingData.getSiteKey(), attributeMappingData.getCategoryKey(),
                    attributeMappingData.getItemKey(), attributeMappingData.getAttributeName()));
        }
        attributeMappingRepository.save(newObject);
        AttributeMappingData persisted = new AttributeMappingData();
        AttributeMappingData.toData(newObject, persisted);
    }

    @Override
    public AttributeMappingData get(String categoryKey, String itemKey, String siteKey, String name) {
        AttributeMappingId attributeMappingId = new AttributeMappingId();
        attributeMappingId.setAttributeName(name);
        attributeMappingId.setCategoryKey(categoryKey);
        attributeMappingId.setItemKey(itemKey);
        attributeMappingId.setSiteKey(siteKey);

        AttributeMappingModel attributeMappingModel = attributeMappingRepository.find(attributeMappingId);
        if (attributeMappingModel == null) {
            return null;
        }
        AttributeMappingData attributeMappingData = new AttributeMappingData();
        AttributeMappingData.toData(attributeMappingModel, attributeMappingData);

        return attributeMappingData;
    }

    @Override
    public List<AttributeMappingData> get(String categoryKey, String itemKey, String siteKey) {
        List<AttributeMappingModel> attributeMappingModels = attributeMappingRepository.findByGroup(siteKey,
                categoryKey, itemKey);
        List<AttributeMappingData> attributeMappingDatas = new ArrayList<AttributeMappingData>();
        AttributeMappingData.toDatas(attributeMappingModels, attributeMappingDatas);
        
        return attributeMappingDatas;
    }

}
