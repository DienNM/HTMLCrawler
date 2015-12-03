package com.myprj.crawler.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.myprj.crawler.domain.config.AttributeData;
import com.myprj.crawler.model.config.AttributeModel;
import com.myprj.crawler.repository.AttributeRepository;
import com.myprj.crawler.service.AttributeService;

/**
 * @author DienNM (DEE)
 */
@Service("attributeService")
public class AttributeServiceImpl implements AttributeService {

    private Logger logger = LoggerFactory.getLogger(AttributeServiceImpl.class);

    @Autowired
    private AttributeRepository attributeRepository;

    @Override
    public AttributeData get(String id) {
        AttributeModel attributeModel = attributeRepository.find(id);
        if (attributeModel == null) {
            logger.warn("Cannot find Attribute: {}", id);
            return null;
        }
        AttributeData attributeData = new AttributeData();
        AttributeData.toData(attributeModel, attributeData);

        return attributeData;
    }

    @Override
    public AttributeData getAndPopulate(String id) {
        AttributeData attributeData = get(id);
        if (attributeData == null) {
            return null;
        }
        populate(attributeData);
        return attributeData;
    }

    @Override
    public AttributeData getRoot(long itemId) {
        AttributeModel attributeModel =attributeRepository.findRootByItemId(itemId);
        if (attributeModel == null) {
            logger.warn("Cannot find Root Attribute for Item: {}", itemId);
            return null;
        }
        AttributeData attributeData = new AttributeData();
        AttributeData.toData(attributeModel, attributeData);

        return attributeData;
    }

    @Override
    public void populate(AttributeData attribute) {
        populateChildren(attribute);
        populateParent(attribute);
    }

    @Override
    public void populateChildren(AttributeData attribute) {
        List<AttributeModel> childrenModels = attributeRepository.findChildren(attribute.getId());
        List<AttributeData> childrenDatas = new ArrayList<AttributeData>();

        AttributeData.toDatas(childrenModels, childrenDatas);
        attribute.setChildren(childrenDatas);
    }

    @Override
    public void populateParent(AttributeData attribute) {
        String parentId = attribute.getParentId();
        if (parentId == null) {
            logger.warn("No Parent Attribute of {}. Cannot populate its parent", attribute.getId());
            return;
        }
        AttributeData parent = get(parentId);
        if (parent != null) {
            attribute.setParent(parent);
        }
    }

    @Override
    public List<AttributeData> getByItemId(long itemId) {
        List<AttributeModel> attributeModels = attributeRepository.findByItemId(itemId);
        List<AttributeData> attributeDatas = new ArrayList<AttributeData>();

        AttributeData.toDatas(attributeModels, attributeDatas);
        return attributeDatas;
    }

    @Override
    @Transactional
    public AttributeData save(AttributeData attribute) {
        AttributeModel attributeModel = new AttributeModel();
        AttributeData.toModel(attribute, attributeModel);

        attributeRepository.save(attributeModel);
        AttributeData attributeData = new AttributeData();
        AttributeData.toData(attributeModel, attributeData);

        return attributeData;
    }

    @Override
    @Transactional
    public List<AttributeData> save(List<AttributeData> attributes) {
        List<AttributeData> attributeDatas = new ArrayList<AttributeData>();

        for (AttributeData attributeData : attributes) {
            AttributeModel attributeModel = new AttributeModel();
            AttributeData.toModel(attributeData, attributeModel);
            attributeRepository.save(attributeModel);

            AttributeData persisted = new AttributeData();
            AttributeData.toData(attributeModel, persisted);
            attributeDatas.add(persisted);
        }
        return attributeDatas;
    }

}
