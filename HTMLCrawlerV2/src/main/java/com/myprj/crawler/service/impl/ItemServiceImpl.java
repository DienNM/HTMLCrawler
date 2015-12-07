package com.myprj.crawler.service.impl;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.myprj.crawler.domain.PageResult;
import com.myprj.crawler.domain.Pageable;
import com.myprj.crawler.domain.config.AttributeData;
import com.myprj.crawler.domain.config.ItemData;
import com.myprj.crawler.model.config.ItemModel;
import com.myprj.crawler.repository.AttributeRepository;
import com.myprj.crawler.repository.ItemAttributeRepository;
import com.myprj.crawler.repository.ItemRepository;
import com.myprj.crawler.service.AttributeService;
import com.myprj.crawler.service.AttributeStructureService;
import com.myprj.crawler.service.ItemService;
import com.myprj.crawler.util.ItemStructureUtil;

/**
 * @author DienNM (DEE)
 */

@Service
public class ItemServiceImpl implements ItemService {

    private Logger logger = LoggerFactory.getLogger(ItemServiceImpl.class);

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private AttributeRepository attributeRepository;

    @Autowired
    private ItemAttributeRepository itemAttributeRepository;

    @Autowired
    @Qualifier("attributeService")
    private AttributeService attributeService;

    @Autowired
    private AttributeStructureService attributeStructureService;

    @Override
    public ItemData get(long id) {
        ItemModel itemModel = itemRepository.find(id);
        if (itemModel == null) {
            logger.warn("Cannot find Item: {}", id);
            return null;
        }
        ItemData itemData = new ItemData();
        ItemData.toData(itemModel, itemData);
        return itemData;
    }

    @Override
    public List<ItemData> getAll() {
        List<ItemModel> itemModels = itemRepository.findAll();
        List<ItemData> itemDatas = new ArrayList<ItemData>();
        ItemData.toDatas(itemModels, itemDatas);
        return itemDatas;
    }

    @Override
    public PageResult<ItemData> getAllWithPaging(Pageable pageable) {
        PageResult<ItemModel> pageResult = itemRepository.findAll(pageable);

        PageResult<ItemData> results = new PageResult<ItemData>(pageResult.getPageable());
        List<ItemData> itemDatas = new ArrayList<ItemData>();
        ItemData.toDatas(pageResult.getContent(), itemDatas);
        results.setContent(itemDatas);

        return results;
    }

    @Override
    public void populateAttributes(ItemData item) {
        List<AttributeData> attributeDatas = attributeService.getByItemId(item.getId());
        item.setAttributes(attributeDatas);
    }

    @Override
    @Transactional
    public ItemData save(ItemData item) {
        ItemModel itemModel = new ItemModel();
        ItemData.toModel(item, itemModel);

        itemRepository.save(itemModel);
        ItemData persisted = new ItemData();
        ItemData.toData(itemModel, persisted);

        persisted.setAttributes(item.getAttributes());
        persisted.setSampleContent(item.getSampleContent());

        return persisted;
    }

    @Override
    @Transactional
    public List<ItemData> saveOrUpdate(List<ItemData> items) {
        List<ItemData> persistedItems = new ArrayList<ItemData>();
        for (ItemData item : items) {
            ItemModel itemModel = itemRepository.findByKey(item.getKey());
            if (itemModel == null) {
                itemModel = new ItemModel();
                ItemData.toModel(item, itemModel);
                itemRepository.save(itemModel);
            } else {
                itemModel.setName(item.getName());
                itemModel.setDescription(item.getDescription());
                itemModel.setCategoryId(item.getCategoryId());
                itemRepository.update(itemModel);
            }
            ItemData persisted = new ItemData();
            ItemData.toData(itemModel, persisted);
            persistedItems.add(persisted);
        }
        return persistedItems;
    }

    @Override
    @Transactional
    public ItemData update(ItemData item) {
        ItemModel itemModel = new ItemModel();
        ItemData.toModel(item, itemModel);

        itemRepository.update(itemModel);
        ItemData persisted = new ItemData();
        ItemData.toData(itemModel, persisted);

        persisted.setAttributes(item.getAttributes());
        persisted.setSampleContent(item.getSampleContent());

        return persisted;
    }

    @Override
    @Transactional
    public void delete(long itemId) {
        ItemModel itemModel = itemRepository.find(itemId);
        if (itemModel == null) {
            throw new InvalidParameterException(String.format("Item %s not found. Cannot delete", itemId));
        }
        attributeRepository.deleteByItemId(itemId);
        itemAttributeRepository.deleteByItemId(itemId);
        itemRepository.deleteById(itemId);
    }
    


    @Override
    @Transactional
    public void delete(List<Long> ids) {
        for(Long id : ids) {
            ItemModel itemModel = itemRepository.find(id);
            if (itemModel != null) {
                attributeRepository.deleteByItemId(id);
                itemAttributeRepository.deleteByItemId(id);
                itemRepository.deleteById(id);
            }
        }
    }

    @Override
    @Transactional
    public ItemData buildItem(long itemId, String jsonAttributes) {
        return buildItem(itemId, jsonAttributes, false);
    }

    @Override
    @Transactional
    public ItemData buildItem(long itemId, String jsonAttributes, boolean forceBuilt) {
        ItemData itemData = get(itemId);
        if (itemData == null) {
            throw new InvalidParameterException("Cannot find Item: " + itemId);
        }

        if (itemData.isBuilt() && !forceBuilt) {
            throw new InvalidParameterException(String.format("Item %s is already built", itemId));
        }

        AttributeData root = attributeStructureService.build(itemData, jsonAttributes);
        List<AttributeData> attributeDatas = ItemStructureUtil.navigateAttribtesFromRoot(root);

        if (attributeDatas.isEmpty()) {
            throw new InvalidParameterException("No Attributes are built for Item: " + itemId);
        }

        // Delete existing attributes
        attributeRepository.deleteByItemId(itemId);
        itemAttributeRepository.deleteByItemId(itemId);

        // Save new Attributes
        attributeDatas = attributeService.save(attributeDatas);
        itemData.setAttributes(attributeDatas);
        itemData.setBuilt(true);

        return update(itemData);
    }
}
