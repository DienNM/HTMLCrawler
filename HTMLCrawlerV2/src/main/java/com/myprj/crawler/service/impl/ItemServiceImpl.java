package com.myprj.crawler.service.impl;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.myprj.crawler.domain.PageResult;
import com.myprj.crawler.domain.Pageable;
import com.myprj.crawler.domain.config.ItemAttributeData;
import com.myprj.crawler.domain.config.ItemData;
import com.myprj.crawler.model.config.ItemModel;
import com.myprj.crawler.repository.ItemAttributeRepository;
import com.myprj.crawler.repository.ItemRepository;
import com.myprj.crawler.repository.WorkerItemAttributeRepository;
import com.myprj.crawler.service.ItemAttributeService;
import com.myprj.crawler.service.ItemAttributeStructureService;
import com.myprj.crawler.service.ItemService;
import com.myprj.crawler.util.AttributeStructureUtil;

/**
 * @author DienNM (DEE)
 */

@Service
public class ItemServiceImpl implements ItemService {

    private Logger logger = LoggerFactory.getLogger(ItemServiceImpl.class);

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private ItemAttributeRepository attributeRepository;

    @Autowired
    private ItemAttributeService itemAttributeService;

    @Autowired
    private WorkerItemAttributeRepository workerItemAttributeRepository;

    @Autowired
    private ItemAttributeStructureService itemAttributeStructureService;

    @Override
    public ItemData get(String id) {
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
    public List<ItemData> get(List<String> ids) {
        List<ItemModel> itemModels = itemRepository.find(ids);
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
        List<ItemAttributeData> attributeDatas = itemAttributeService.getByItemId(item.getId());
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
            ItemModel itemModel = itemRepository.find(item.getId());
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
    public void delete(String itemId) {
        ItemModel itemModel = itemRepository.find(itemId);
        if (itemModel == null) {
            throw new InvalidParameterException(String.format("Item %s not found. Cannot delete", itemId));
        }
        attributeRepository.deleteByItemId(itemId);
        workerItemAttributeRepository.deleteByItemId(itemModel.getId());
        itemRepository.deleteById(itemId);
    }

    @Override
    @Transactional
    public void delete(List<String> ids) {
        for (String id : ids) {
            ItemModel itemModel = itemRepository.find(id);
            if (itemModel != null) {
                attributeRepository.deleteByItemId(id);
                workerItemAttributeRepository.deleteByItemId(itemModel.getId());
                itemRepository.deleteById(id);
            }
        }
    }

    @Override
    @Transactional
    public ItemData buildItem(String itemKey, String jsonAttributes, boolean forceBuild) {
        ItemData itemData = get(itemKey);
        if (itemData == null) {
            throw new InvalidParameterException("Cannot find Item: " + itemKey);
        }
        return buildItem(itemData, jsonAttributes, forceBuild);
    }

    private ItemData buildItem(ItemData itemData, String jsonAttributes, boolean forceBuild) {

        if (itemData.isBuilt() && !forceBuild) {
            throw new InvalidParameterException(String.format("Item %s is already built", itemData.getId()));
        }

        ItemAttributeData root = itemAttributeStructureService.build(itemData, jsonAttributes);
        List<ItemAttributeData> attributeDatas = AttributeStructureUtil.navigateAttribtesFromRoot(root);

        if (attributeDatas.isEmpty()) {
            throw new InvalidParameterException("No Attributes are built for Item: " + itemData.getId());
        }

        // Delete existing attributes
        attributeRepository.deleteByItemId(itemData.getId());
        workerItemAttributeRepository.deleteByItemId(itemData.getId());

        // Save new Attributes
        attributeDatas = itemAttributeService.save(attributeDatas);
        itemData.setAttributes(attributeDatas);
        itemData.setBuilt(true);

        return update(itemData);
    }
}
