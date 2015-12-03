package com.myprj.crawler.service.impl;

import java.security.InvalidParameterException;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.myprj.crawler.domain.config.AttributeData;
import com.myprj.crawler.domain.config.ItemAttributeData;
import com.myprj.crawler.domain.config.ItemData;
import com.myprj.crawler.model.config.ItemModel;
import com.myprj.crawler.repository.ItemRepository;
import com.myprj.crawler.service.AttributeService;
import com.myprj.crawler.service.ItemService;
import com.myprj.crawler.service.ItemStructureService;
import com.myprj.crawler.util.ItemStructureUtil;

/**
 * @author DienNM (DEE)
 */

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemRepository itemRepository;
    
    @Autowired
    private AttributeService attributeService;
    
    @Autowired
    @Qualifier("attributeStructureService")
    private ItemStructureService<AttributeData> attributeStructureService;
    
    @Autowired
    @Qualifier("itemAttributeStructureService")
    private ItemStructureService<ItemAttributeData> itemAttrItemStructureService;
    
    @Override
    @Transactional
    public ItemData buildItem(ItemData item, String jsonAttributes) {
        ItemData itemData = save(item);
        
        AttributeData root = attributeStructureService.buildAttributes(itemData, jsonAttributes);
        List<AttributeData> attributeDatas = ItemStructureUtil.navigateAttribtesFromRoot(root);
        
        attributeDatas = attributeService.save(attributeDatas);
        itemData.setAttributes(attributeDatas);
        
        return update(itemData);
    }
    
    @Override
    public ItemData save(ItemData item) {
        ItemModel itemModel = new ItemModel();
        ItemData.toModel(item, itemModel);
        
        itemRepository.save(itemModel);
        ItemData persisted = new ItemData();
        ItemData.toData(itemModel, persisted);
        
        return persisted;
    }

    @Override
    public ItemData update(ItemData item) {
        ItemModel itemModel = itemRepository.find(item.getId());
        if(itemModel == null) {
            throw new InvalidParameterException("Cannot find item: " + item.getId());
        }
        
        ItemData.toModel(item, itemModel);
        
        itemRepository.update(itemModel);
        ItemData persisted = new ItemData();
        ItemData.toData(itemModel, persisted);
        
        return persisted;
    }

    @Override
    public ItemData get(long id) {
        return null;
    }
}
