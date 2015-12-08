package com.myprj.crawler.service.impl;

import static com.myprj.crawler.enumeration.CrawlType.DETAIL;

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
import com.myprj.crawler.domain.config.WorkerItemAttributeData;
import com.myprj.crawler.domain.crawl.WorkerData;
import com.myprj.crawler.domain.crawl.WorkerItemData;
import com.myprj.crawler.enumeration.Level;
import com.myprj.crawler.model.crawl.WorkerItemAttributeModel;
import com.myprj.crawler.model.crawl.WorkerItemModel;
import com.myprj.crawler.model.crawl.WorkerModel;
import com.myprj.crawler.repository.WorkerItemAttributeRepository;
import com.myprj.crawler.repository.WorkerItemRepository;
import com.myprj.crawler.repository.WorkerRepository;
import com.myprj.crawler.service.WorkerItemAttributeStructureService;
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
    private WorkerItemAttributeRepository workerItemAttributeRepository;

    @Autowired
    private WorkerItemAttributeStructureService workerItemAttrItemStructureService;

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
    public WorkerData getByKey(String key) {
        WorkerModel workerModel = workerRepository.findByKey(key);
        if (workerModel == null) {
            logger.warn("Cannot find worker: {}", key);
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
    public WorkerData saveOrUpdate(WorkerData worker) {
        WorkerModel workerModel = workerRepository.findByKey(worker.getKey());
        if (workerModel == null) {
            workerModel = new WorkerModel();
            WorkerData.toModel(worker, workerModel);
            workerRepository.save(workerModel);
        } else {
            workerModel.setName(worker.getName());
            workerModel.setAttemptTimes(worker.getAttemptTimes());
            workerModel.setDelayTime(worker.getDelayTime());
            workerModel.setDescription(worker.getDescription());
            workerModel.setRequestId(worker.getRequestId());
            workerModel.setStatus(worker.getStatus());
            workerModel.setThreads(worker.getThreads());
            workerRepository.update(workerModel);
        }
        WorkerData persisted = new WorkerData();
        WorkerData.toData(workerModel, persisted);

        return persisted;
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
    public List<WorkerItemData> buildWorkerItems(WorkerData worker, List<WorkerItemData> workerItems) {
        Map<Level, WorkerItemData> workerItemMap = new HashMap<Level, WorkerItemData>();

        for (WorkerItemData workerItem : workerItems) {
            if (workerItemMap.containsKey(workerItem.getLevel())) {
                throw new InvalidParameterException("Duplicated Level: " + workerItem.getLevel());
            }
            WorkerItemValidator.validateCreationPhase(workerItem);

            if (DETAIL.equals(workerItem.getCrawlType())) {
                workerItem.setPagingConfig(null);
            }

            workerItem.setWorkerId(worker.getId());
            workerItemMap.put(workerItem.getLevel(), workerItem);
        }

        List<WorkerItemModel> workerItemModels = new ArrayList<WorkerItemModel>();
        WorkerItemData.toModels(new ArrayList<WorkerItemData>(workerItemMap.values()), workerItemModels);

        List<WorkerItemModel> workerItemModelOlds = workerItemRepository.findByWorkerId(worker.getId());
        for (WorkerItemModel workerItemModelOld : workerItemModelOlds) {
            workerItemAttributeRepository.deleteByWorkerItemId(workerItemModelOld.getId());
        }

        workerItemRepository.deleteByWorkerId(worker.getId());
        workerItemRepository.save(workerItemModels);
        worker.setBuilt(true);

        List<WorkerItemData> workerItemDatas = new ArrayList<WorkerItemData>();
        WorkerItemData.toDatas(workerItemModels, workerItemDatas);
        worker.setWorkerItems(workerItemDatas);

        update(worker);

        return workerItemDatas;
    }

    @Override
    @Transactional
    public WorkerItemData buildSelector4Item(WorkerItemData workerItem, String jsonSelector) {
        WorkerItemAttributeData attribute = workerItemAttrItemStructureService.build(workerItem, jsonSelector);

        if (attribute == null) {
            return workerItem;
        }

        workerItemAttributeRepository.deleteByWorkerItemId(workerItem.getId());

        List<WorkerItemAttributeData> itemAttributes = new ArrayList<WorkerItemAttributeData>();
        WorkerItemAttributeData.collectionAllItemAttributes(attribute, itemAttributes);
        List<WorkerItemAttributeModel> itemAttributeModels = new ArrayList<WorkerItemAttributeModel>();
        WorkerItemAttributeData.toModels(itemAttributes, itemAttributeModels);
        workerItemAttributeRepository.save(itemAttributeModels);

        workerItem.setRootWorkerItemAttribute(attribute);

        WorkerItemModel workerItemModel = new WorkerItemModel();
        WorkerItemData.toModel(workerItem, workerItemModel);

        workerItemRepository.update(workerItemModel);

        return workerItem;
    }

    @Override
    public WorkerItemData getWorkerItem(WorkerData worker, Level level) {
        WorkerItemModel workerItemModel = workerItemRepository.findByWorkerIdAndLevel(worker.getId(), level);
        if (workerItemModel == null) {
            logger.warn("Cannot find Worker Item: Worker={} and Level={}", worker.getId(), level);
            return null;
        }
        WorkerItemData workerItemData = new WorkerItemData();
        WorkerItemData.toData(workerItemModel, workerItemData);
        return workerItemData;
    }

}
