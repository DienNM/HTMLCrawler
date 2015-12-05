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

import com.myprj.crawler.domain.PageResult;
import com.myprj.crawler.domain.Pageable;
import com.myprj.crawler.domain.config.ItemAttributeData;
import com.myprj.crawler.domain.crawl.WorkerData;
import com.myprj.crawler.domain.crawl.WorkerItemData;
import com.myprj.crawler.enumeration.Level;
import com.myprj.crawler.model.config.ItemAttributeModel;
import com.myprj.crawler.model.crawl.WorkerItemModel;
import com.myprj.crawler.model.crawl.WorkerModel;
import com.myprj.crawler.repository.ItemAttributeRepository;
import com.myprj.crawler.repository.WorkerItemRepository;
import com.myprj.crawler.repository.WorkerRepository;
import com.myprj.crawler.service.ItemAttributeStructureService;
import com.myprj.crawler.service.WorkerService;
import com.myprj.crawler.util.WorkerItemValidator;

/**
 * @author DienNM (DEE)
 */
@Service
public class WorkerServiceImpl implements WorkerService {

    private final Logger logger = LoggerFactory.getLogger(WorkerServiceImpl.class);

    @Autowired
    private WorkerRepository workerRepository;

    @Autowired
    private WorkerItemRepository workerItemRepository;

    @Autowired
    private ItemAttributeRepository itemAttributeRepository;

    @Autowired
    private ItemAttributeStructureService itemAttrItemStructureService;

    @Override
    public WorkerData get(long id) {
        WorkerModel workerModel = workerRepository.find(id);
        if (workerModel == null) {
            logger.warn("Cannot find worker: {}", id);
            return null;
        }
        WorkerData workerData = new WorkerData();
        WorkerData.toData(workerModel, workerData);

        return workerData;
    }

    @Override
    public List<WorkerData> getAll() {
        List<WorkerModel> workerModels = workerRepository.findAll();
        List<WorkerData> workerDatas = new ArrayList<WorkerData>();
        WorkerData.toDatas(workerModels, workerDatas);

        return workerDatas;
    }

    @Override
    public PageResult<WorkerData> getAllWithPaging(Pageable pageable) {
        PageResult<WorkerModel> pageResult = workerRepository.findAll(pageable);

        PageResult<WorkerData> results = new PageResult<WorkerData>(pageResult.getPageable());
        List<WorkerData> workerDatas = new ArrayList<WorkerData>();
        WorkerData.toDatas(pageResult.getContent(), workerDatas);
        results.setContent(workerDatas);

        return results;
    }

    @Override
    public void populateWorkerItems(WorkerData worker) {
        List<WorkerItemModel> workerItemModels = workerItemRepository.findByWorkerId(worker.getId());
        List<WorkerItemData> workerItems = new ArrayList<WorkerItemData>();
        WorkerItemData.toDatas(workerItemModels, workerItems);
        worker.setWorkerItems(workerItems);
    }

    @Override
    @Transactional
    public WorkerData save(WorkerData worker) {
        WorkerModel workerModel = new WorkerModel();
        WorkerData.toModel(worker, workerModel);
        workerRepository.save(workerModel);

        WorkerData workerData = new WorkerData();
        WorkerData.toData(workerModel, workerData);
        return workerData;
    }

    @Override
    @Transactional
    public WorkerData update(WorkerData worker) {
        WorkerModel workerModel = workerRepository.find(worker.getId());
        if (workerModel == null) {
            throw new InvalidParameterException("Cannot find worker: " + worker.getId());
        }
        WorkerData.toModel(worker, workerModel);
        workerRepository.update(workerModel);

        WorkerData workerData = new WorkerData();
        WorkerData.toData(workerModel, workerData);
        return workerData;
    }

    @Override
    @Transactional
    public void addWorkerItems(WorkerData worker, List<WorkerItemData> workerItems) {
        Map<Level, WorkerItemData> workerItemMap = new HashMap<Level, WorkerItemData>();

        for (WorkerItemData workerItem : workerItems) {
            if (workerItemMap.containsKey(workerItem.getLevel())) {
                throw new InvalidParameterException("Duplicated Level: " + workerItem.getLevel());
            }
            WorkerItemValidator.validateCreationPhase(workerItem);
            workerItem.setWorkerId(worker.getId());
            workerItemMap.put(workerItem.getLevel(), workerItem);
        }

        List<WorkerItemModel> workerItemModels = new ArrayList<WorkerItemModel>();
        workerItemRepository.save(workerItemModels);

        List<WorkerItemData> workerItemDatas = new ArrayList<WorkerItemData>();
        WorkerItemData.toDatas(workerItemModels, workerItemDatas);
        worker.setWorkerItems(workerItemDatas);
    }

    @Override
    public ItemAttributeData buildSelector4Item(WorkerData worker, Level level, String jsonSelector) {
        WorkerItemModel workerItemModel = workerItemRepository.findByWorkerIdAndLevel(worker.getId(), level);
        if (workerItemModel == null) {
            throw new InvalidParameterException(String.format("Cannot find Worker Item: Worker=%s and Level=%s",
                    worker.getId(), level));
        }

        WorkerItemData workerItemData = new WorkerItemData();
        WorkerItemData.toData(workerItemModel, workerItemData);

        ItemAttributeData itemAttribute = itemAttrItemStructureService.build(workerItemData, jsonSelector);
        saveAllItemAttributesFromRoot(itemAttribute);
        return itemAttribute;
    }

    @Transactional
    private void saveAllItemAttributesFromRoot(ItemAttributeData itemAttribute) {

        List<ItemAttributeData> itemAttributes = new ArrayList<ItemAttributeData>();
        ItemAttributeData.collectionAllItemAttributes(itemAttribute, itemAttributes);
        List<ItemAttributeModel> itemAttributeModels = new ArrayList<ItemAttributeModel>();
        ItemAttributeData.toModels(itemAttributes, itemAttributeModels);

        itemAttributeRepository.save(itemAttributeModels);
    }

}
