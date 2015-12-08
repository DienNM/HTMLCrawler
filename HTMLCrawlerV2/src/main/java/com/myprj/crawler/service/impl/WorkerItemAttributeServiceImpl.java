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

import com.myprj.crawler.domain.config.WorkerItemAttributeData;
import com.myprj.crawler.model.config.WorkerItemAttributeModel;
import com.myprj.crawler.repository.WorkerItemAttributeRepository;
import com.myprj.crawler.service.WorkerItemAttributeService;

/**
 * @author DienNM (DEE)
 */
@Service
public class WorkerItemAttributeServiceImpl implements WorkerItemAttributeService {
    
    private Logger logger = LoggerFactory.getLogger(WorkerItemAttributeServiceImpl.class);

    @Autowired
    private WorkerItemAttributeRepository workerItemAttributeRepository;
    
    @Override
    public WorkerItemAttributeData get(String id) {
        WorkerItemAttributeModel attributeModel = workerItemAttributeRepository.find(id);
        if(attributeModel == null) {
            logger.warn("Cannot find Worker Item Attribute: {}", id);
            return null;
        }
        WorkerItemAttributeData attributeData = new WorkerItemAttributeData();
        WorkerItemAttributeData.toData(attributeModel, attributeData);
        
        return attributeData;
    }
    
    @Override
    public WorkerItemAttributeData getAndPopulate(String id) {
        WorkerItemAttributeData attributeData = get(id);
        if(attributeData == null) {
            return null;
        }
        populate(attributeData);
        return attributeData;
    }

    @Override
    public void populate(WorkerItemAttributeData attribute) {
        populateParent(attribute);
        populateChildren(attribute);
        populateAttribute(attribute);
    }

    @Override
    public void populateChildren(WorkerItemAttributeData attribute) {
        List<WorkerItemAttributeModel> childrenModel = workerItemAttributeRepository.findChildren(attribute.getId());
        List<WorkerItemAttributeData> childrenData = new ArrayList<WorkerItemAttributeData>();
        WorkerItemAttributeData.toDatas(childrenModel, childrenData);
        attribute.setChildren(childrenData);
    }

    @Override
    public void populateParent(WorkerItemAttributeData attribute) {
        String parentId = attribute.getParentId();
        if(parentId == null) {
            logger.warn("No Parent Worker Item Attribute of {}. Cannot populate its parent", attribute.getId());
            return;
        }
        WorkerItemAttributeData parentAttribute = get(attribute.getParentId());
        if(parentAttribute != null) {
            attribute.setParent(parentAttribute);
        }
    }
    
    @Override
    public void populateAttribute(WorkerItemAttributeData attribute) {
        // TODO
    }

    @Override
    public List<WorkerItemAttributeData> getByWorkerItemId(long workerItemId) {
        
        List<WorkerItemAttributeModel> attributeModels = workerItemAttributeRepository.findByWorkerItemId(workerItemId);
        List<WorkerItemAttributeData> attributeDatas = new ArrayList<WorkerItemAttributeData>();
        WorkerItemAttributeData.toDatas(attributeModels, attributeDatas);
        
        return attributeDatas;
    }
    
    @Override
    public WorkerItemAttributeData getRootWithPopulateTree(long workerItemId) {
        
        List<WorkerItemAttributeData> attributeDatas = getByWorkerItemId(workerItemId);
        if(attributeDatas.isEmpty()) {
            throw new InvalidParameterException("Worker Item " + workerItemId + " has not been built Selectors yet.");
        }
        
        Map<String, List<WorkerItemAttributeData>> mapParents = new HashMap<String, List<WorkerItemAttributeData>>();
        for(WorkerItemAttributeData itemAtt : attributeDatas) {
            String parentId = itemAtt.getParentId();
            if(parentId == null) {
                parentId = "-1";
            }
            List<WorkerItemAttributeData> children = mapParents.get(parentId);
            if(children == null) {
                children = new ArrayList<WorkerItemAttributeData>();
                mapParents.put(parentId, children);
            }
            children.add(itemAtt);
        }
        
        WorkerItemAttributeData root = mapParents.get("-1").get(0);
        populateTree(root, mapParents);
        
        return root;
    }
    
    private void populateTree(WorkerItemAttributeData current, Map<String, List<WorkerItemAttributeData>> mapParents) {
        List<WorkerItemAttributeData> children = mapParents.get(current.getId());
        if(children == null) {
            return;
        }
        for(WorkerItemAttributeData child : children) {
            child.setParent(current);
            populateTree(child, mapParents);
            if(current.getChildren() == null) {
                current.setChildren(new ArrayList<WorkerItemAttributeData>());
            }
            current.getChildren().add(child);
        }
    }

    @Override
    @Transactional
    public WorkerItemAttributeData save(WorkerItemAttributeData attribute) {
        WorkerItemAttributeModel attributeModel = new WorkerItemAttributeModel();
        WorkerItemAttributeData.toModel(attribute, attributeModel);
        
        workerItemAttributeRepository.save(attributeModel);
        WorkerItemAttributeData persisted = new WorkerItemAttributeData();
        WorkerItemAttributeData.toData(attributeModel, persisted);
        
        return persisted;
    }

    @Override
    @Transactional
    public List<WorkerItemAttributeData> save(List<WorkerItemAttributeData> attributes) {
        List<WorkerItemAttributeModel> attributeModels = new ArrayList<WorkerItemAttributeModel>();
        WorkerItemAttributeData.toModels(attributes, attributeModels);
        
        workerItemAttributeRepository.save(attributeModels);
        List<WorkerItemAttributeData> persisteds = new ArrayList<WorkerItemAttributeData>();
        WorkerItemAttributeData.toDatas(attributeModels, persisteds);
        
        return persisteds;
    }

}
