package com.myprj.crawler.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public ItemAttributeData get(long id) {
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
    public ItemAttributeData getAndPopulate(long id) {
        ItemAttributeData itemAttributeData = get(id);
        if(itemAttributeData == null) {
            return null;
        }
        populate(itemAttributeData);
        return itemAttributeData;
    }

    @Override
    public ItemAttributeData getRoot(long itemId) {
        return null;
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
        long parentId = itemAttribute.getParentId();
        if(parentId == -1) {
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
        // TODO Auto-generated method stub
        
    }

    @Override
    public List<ItemAttributeData> getByItemId(long itemId) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ItemAttributeData save(ItemAttributeData itemAttribute) {
        return null;
    }

    @Override
    public List<ItemAttributeData> save(List<ItemAttributeData> itemAttributes) {
        // TODO Auto-generated method stub
        return null;
    }

}
