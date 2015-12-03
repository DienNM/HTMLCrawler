package com.myprj.crawler.service.impl;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.myprj.crawler.domain.PageResult;
import com.myprj.crawler.domain.Pageable;
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

    private Logger logger = LoggerFactory.getLogger(ItemServiceImpl.class);

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    @Qualifier("attributeService")
    private AttributeService attributeService;

    @Autowired
    @Qualifier("attributeStructureService")
    private ItemStructureService<AttributeData> attributeStructureService;

    @Autowired
    @Qualifier("itemAttributeStructureService")
    private ItemStructureService<ItemAttributeData> itemAttrItemStructureService;

    @Override
    public ItemData get(long id) {
        ItemModel itemModel = itemRepository.find(id);
        if (itemModel == null) {
            logger.warn("Cannot find Item: {}", id);
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
    public PageResult<ItemData> getAll(Pageable pageable) {
        PageResult<ItemModel> pageResult = itemRepository.find(pageable);

        PageResult<ItemData> results = new PageResult<ItemData>();
        PageResult.copy(pageResult, results);
        List<ItemData> itemDatas = new ArrayList<ItemData>();
        ItemData.toDatas(pageResult.getContent(), itemDatas);
        results.setContent(itemDatas);

        return results;
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
        if (itemModel == null) {
            throw new InvalidParameterException("Cannot find item: " + item.getId());
        }

        ItemData.toModel(item, itemModel);

        itemRepository.update(itemModel);
        ItemData persisted = new ItemData();
        ItemData.toData(itemModel, persisted);

        return persisted;
    }

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
}
