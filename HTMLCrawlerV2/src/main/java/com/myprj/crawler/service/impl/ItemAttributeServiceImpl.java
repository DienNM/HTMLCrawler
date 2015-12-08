package com.myprj.crawler.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.myprj.crawler.domain.config.ItemAttributeData;
import com.myprj.crawler.model.config.ItemAttributeModel;
import com.myprj.crawler.repository.ItemAttributeRepository;
import com.myprj.crawler.service.ItemAttributeService;

/**
 * @author DienNM (DEE)
 */
@Service
public class ItemAttributeServiceImpl implements ItemAttributeService {

    private Logger logger = LoggerFactory.getLogger(ItemAttributeServiceImpl.class);

    @Autowired
    private ItemAttributeRepository itemAttributeRepository;

    @Override
    public ItemAttributeData get(String id) {
        ItemAttributeModel attributeModel = itemAttributeRepository.find(id);
        if (attributeModel == null) {
            logger.warn("Cannot find Attribute: {}", id);
            return null;
        }
        ItemAttributeData attributeData = new ItemAttributeData();
        ItemAttributeData.toData(attributeModel, attributeData);

        return attributeData;
    }

    @Override
    public ItemAttributeData getAndPopulate(String id) {
        ItemAttributeData attributeData = get(id);
        if (attributeData == null) {
            return null;
        }
        populate(attributeData);
        return attributeData;
    }

    @Override
    public ItemAttributeData getRoot(long itemId) {
        ItemAttributeModel attributeModel =itemAttributeRepository.findRootByItemId(itemId);
        if (attributeModel == null) {
            logger.warn("Cannot find Root Attribute for Item: {}", itemId);
            return null;
        }
        ItemAttributeData attributeData = new ItemAttributeData();
        ItemAttributeData.toData(attributeModel, attributeData);

        return attributeData;
    }

    @Override
    public void populate(ItemAttributeData attribute) {
        populateChildren(attribute);
        populateParent(attribute);
    }

    @Override
    public void populateChildren(ItemAttributeData attribute) {
        List<ItemAttributeModel> childrenModels = itemAttributeRepository.findChildren(attribute.getId());
        List<ItemAttributeData> childrenDatas = new ArrayList<ItemAttributeData>();

        ItemAttributeData.toDatas(childrenModels, childrenDatas);
        attribute.setChildren(childrenDatas);
    }

    @Override
    public void populateParent(ItemAttributeData attribute) {
        String parentId = attribute.getParentId();
        if (parentId == null) {
            logger.warn("No Parent Attribute of {}. Cannot populate its parent", attribute.getId());
            return;
        }
        ItemAttributeData parent = get(parentId);
        if (parent != null) {
            attribute.setParent(parent);
        }
    }

    @Override
    public List<ItemAttributeData> getByItemId(long itemId) {
        List<ItemAttributeModel> attributeModels = itemAttributeRepository.findByItemId(itemId);
        List<ItemAttributeData> attributeDatas = new ArrayList<ItemAttributeData>();

        ItemAttributeData.toDatas(attributeModels, attributeDatas);
        return attributeDatas;
    }

    @Override
    @Transactional
    public ItemAttributeData save(ItemAttributeData attribute) {
        ItemAttributeModel attributeModel = new ItemAttributeModel();
        ItemAttributeData.toModel(attribute, attributeModel);

        itemAttributeRepository.save(attributeModel);
        ItemAttributeData attributeData = new ItemAttributeData();
        ItemAttributeData.toData(attributeModel, attributeData);

        return attributeData;
    }

    @Override
    @Transactional
    public List<ItemAttributeData> save(List<ItemAttributeData> attributes) {
        List<ItemAttributeData> attributeDatas = new ArrayList<ItemAttributeData>();

        for (ItemAttributeData attributeData : attributes) {
            ItemAttributeModel attributeModel = new ItemAttributeModel();
            ItemAttributeData.toModel(attributeData, attributeModel);
            itemAttributeRepository.save(attributeModel);

            ItemAttributeData persisted = new ItemAttributeData();
            ItemAttributeData.toData(attributeModel, persisted);
            attributeDatas.add(persisted);
        }
        return attributeDatas;
    }

}
