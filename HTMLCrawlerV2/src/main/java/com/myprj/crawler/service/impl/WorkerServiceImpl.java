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
import com.myprj.crawler.domain.crawl.WorkerData;
import com.myprj.crawler.model.crawl.WorkerModel;
import com.myprj.crawler.repository.WorkerRepository;
import com.myprj.crawler.service.WorkerService;

/**
 * @author DienNM (DEE)
 */
@Service
public class WorkerServiceImpl implements WorkerService {

    private final Logger logger = LoggerFactory.getLogger(WorkerServiceImpl.class);

    @Autowired
    private WorkerRepository workerRepository;

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
    public PageResult<WorkerData> getPaging(Pageable pageable) {
        PageResult<WorkerModel> pageResult = workerRepository.find(pageable);

        PageResult<WorkerData> results = new PageResult<WorkerData>();
        PageResult.copy(pageResult, results);
        List<WorkerData> workerDatas = new ArrayList<WorkerData>();
        WorkerData.toDatas(pageResult.getContent(), workerDatas);
        results.setContent(workerDatas);

        return results;
    }

    @Override
    @Transactional
    public WorkerData save(WorkerData worker) {
        WorkerModel workerModel = new WorkerModel();
        WorkerData.toModel(worker, workerModel);
        workerRepository.save(workerModel);
        return WorkerData.toData(workerModel);
    }

    @Override
    public WorkerData update(WorkerData worker) {
        WorkerModel workerModel = workerRepository.find(worker.getId());
        if (workerModel == null) {
            throw new InvalidParameterException("Cannot find worker: " + worker.getId());
        }
        WorkerData.toModel(worker, workerModel);
        workerRepository.update(workerModel);

        return WorkerData.toData(workerModel);
    }
}
