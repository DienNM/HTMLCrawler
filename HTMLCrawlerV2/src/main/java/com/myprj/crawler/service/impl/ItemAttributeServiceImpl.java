package com.myprj.crawler.service.impl;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        ItemAttributeModel itemAttributeModel = itemAttributeRepository.find(id);
        if(itemAttributeModel == null) {
            logger.warn("Cannot find Item Attribute: {}", id);
            return null;
        }
        ItemAttributeData itemAttributeData = new ItemAttributeData();
        ItemAttributeData.toData(itemAttributeModel, itemAttributeData);
        
        return itemAttributeData;
    }
    
    @Override
    public ItemAttributeData getAndPopulate(String id) {
        ItemAttributeData itemAttributeData = get(id);
        if(itemAttributeData == null) {
            return null;
        }
        populate(itemAttributeData);
        return itemAttributeData;
    }

    @Override
    public void populate(ItemAttributeData itemAttribute) {
        populateParent(itemAttribute);
        populateChildren(itemAttribute);
        populateAttribute(itemAttribute);
    }

    @Override
    public void populateChildren(ItemAttributeData itemAttribute) {
        List<ItemAttributeModel> childrenModel = itemAttributeRepository.findChildren(itemAttribute.getId());
        List<ItemAttributeData> childrenData = new ArrayList<ItemAttributeData>();
        ItemAttributeData.toDatas(childrenModel, childrenData);
        itemAttribute.setChildren(childrenData);
    }

    @Override
    public void populateParent(ItemAttributeData itemAttribute) {
        String parentId = itemAttribute.getParentId();
        if(parentId == null) {
            logger.warn("No Parent Item Attribute of {}. Cannot populate its parent", itemAttribute.getId());
            return;
        }
        ItemAttributeData parentItemAttribute = get(itemAttribute.getParentId());
        if(parentItemAttribute != null) {
            itemAttribute.setParent(parentItemAttribute);
        }
    }
    
    @Override
    public void populateAttribute(ItemAttributeData itemAttribute) {
        // TODO
    }

    @Override
    public List<ItemAttributeData> getByWorkerItemId(long workerItemId) {
        
        List<ItemAttributeModel> itemAttributeModels = itemAttributeRepository.findByWorkerItemId(workerItemId);
        List<ItemAttributeData> itemAttributeDatas = new ArrayList<ItemAttributeData>();
        ItemAttributeData.toDatas(itemAttributeModels, itemAttributeDatas);
        
        return itemAttributeDatas;
    }
    
    @Override
    public ItemAttributeData getRootWithPopulateTree(long workerItemId) {
        
        List<ItemAttributeData> itemAttributeDatas = getByWorkerItemId(workerItemId);
        if(itemAttributeDatas.isEmpty()) {
            throw new InvalidParameterException("Worker Item " + workerItemId + " has not been built Selectors yet.");
        }
        
        Map<String, List<ItemAttributeData>> mapParents = new HashMap<String, List<ItemAttributeData>>();
        for(ItemAttributeData itemAtt : itemAttributeDatas) {
            String parentId = itemAtt.getParentId();
            if(parentId == null) {
                parentId = "-1";
            }
            List<ItemAttributeData> children = mapParents.get(parentId);
            if(children == null) {
                children = new ArrayList<ItemAttributeData>();
                mapParents.put(parentId, children);
            }
            children.add(itemAtt);
        }
        
        ItemAttributeData root = mapParents.get("-1").get(0);
        populateTree(root, mapParents);
        
        return root;
    }
    
    private void populateTree(ItemAttributeData current, Map<String, List<ItemAttributeData>> mapParents) {
        List<ItemAttributeData> children = mapParents.get(current.getId());
        if(children == null) {
            return;
        }
        for(ItemAttributeData child : children) {
            child.setParent(current);
            populateTree(child, mapParents);
            if(current.getChildren() == null) {
                current.setChildren(new ArrayList<ItemAttributeData>());
            }
            current.getChildren().add(child);
        }
    }

    @Override
    @Transactional
    public ItemAttributeData save(ItemAttributeData itemAttribute) {
        ItemAttributeModel itemAttributeModel = new ItemAttributeModel();
        ItemAttributeData.toModel(itemAttribute, itemAttributeModel);
        
        itemAttributeRepository.save(itemAttributeModel);
        ItemAttributeData persisted = new ItemAttributeData();
        ItemAttributeData.toData(itemAttributeModel, persisted);
        
        return persisted;
    }

    @Override
    @Transactional
    public List<ItemAttributeData> save(List<ItemAttributeData> itemAttributes) {
        List<ItemAttributeModel> itemAttributeModels = new ArrayList<ItemAttributeModel>();
        ItemAttributeData.toModels(itemAttributes, itemAttributeModels);
        
        itemAttributeRepository.save(itemAttributeModels);
        List<ItemAttributeData> persisteds = new ArrayList<ItemAttributeData>();
        ItemAttributeData.toDatas(itemAttributeModels, persisteds);
        
        return persisteds;
    }

}
