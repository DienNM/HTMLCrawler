package com.myprj.crawler.web.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.myprj.crawler.domain.PageResult;
import com.myprj.crawler.domain.Pageable;
import com.myprj.crawler.domain.crawl.WorkerData;
import com.myprj.crawler.service.ItemService;
import com.myprj.crawler.service.WorkerService;
import com.myprj.crawler.web.dto.JsonResponse;
import com.myprj.crawler.web.dto.WorkerDTO;
import com.myprj.crawler.web.enumeration.DTOLevel;
import com.myprj.crawler.web.facades.WorkerFacade;

/**
 * @author DienNM (DEE)
 */

@Controller
@RequestMapping(value = "/workers", produces = "application/json")
public class WorkerController extends AbstractController {

    private Logger logger = LoggerFactory.getLogger(WorkerController.class);

    @Autowired
    private WorkerFacade workerFacade;

    @Autowired
    private WorkerService workerService;

    @Autowired
    private ItemService itemService;

    private void populateWorkerByLevel(WorkerData worker, DTOLevel level) {
        switch (level) {
        case FULL:
            workerService.populateWorkerItems(worker);
            break;
        default:
            break;
        }
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public JsonResponse getPaging(@RequestParam(value = "currentPage", defaultValue = "0") int currentPage,
            @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
            @RequestParam(value = "level", defaultValue = "SIMPLE") DTOLevel level) {

        Pageable pageable = new Pageable(pageSize, currentPage);
        PageResult<WorkerData> pageResult = workerService.getAllWithPaging(pageable);

        List<WorkerData> sources = pageResult.getContent();
        List<Map<String, Object>> listDatas = getListMapResult(sources, WorkerDTO.class, level);
        JsonResponse response = new JsonResponse(listDatas, !listDatas.isEmpty());
        response.putPaging(pageResult.getPageable());

        return response;
    }

    @RequestMapping(value = "/{ids}", method = RequestMethod.GET)
    @ResponseBody
    public JsonResponse getWorker(@PathVariable(value = "ids") List<Long> ids,
            @RequestParam(value = "level", defaultValue = "DEFAULT") DTOLevel level) {

        List<WorkerData> workerDatas = workerService.get(ids);
        for (WorkerData workerData : workerDatas) {
            populateWorkerByLevel(workerData, level);
        }

        List<Map<String, Object>> datas = getListMapResult(workerDatas, WorkerDTO.class, level);
        JsonResponse response = new JsonResponse(!datas.isEmpty());
        response.putData(datas);

        return response;
    }

    @RequestMapping(value = "/{keys}/by-keys", method = RequestMethod.GET)
    @ResponseBody
    public JsonResponse getWorkerByKeys(@PathVariable(value = "keys") List<String> keys,
            @RequestParam(value = "level", defaultValue = "DEFAULT") DTOLevel level) {

        List<WorkerData> workerDatas = workerService.getByKeys(keys);
        for (WorkerData workerData : workerDatas) {
            populateWorkerByLevel(workerData, level);
        }

        List<Map<String, Object>> datas = getListMapResult(workerDatas, WorkerDTO.class, level);
        JsonResponse response = new JsonResponse(!datas.isEmpty());
        response.putData(datas);

        return response;
    }
    
    @RequestMapping(value = "/{ids}/delete", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse deleteItems(@PathVariable(value = "ids") List<Long> ids) {
        try {
            workerService.delete(ids);
            return new JsonResponse(true);
        } catch (Exception e) {
            JsonResponse response = new JsonResponse(false);
            response.putMessage(e.getMessage());
            return response;
        }
    }

    @RequestMapping(value = "/import", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse importWorkersAndItems(@RequestParam(value = "file") MultipartFile file) {

        if (file == null) {
            JsonResponse response = new JsonResponse(false);
            response.putMessage("File are missing");
            return response;
        }
        List<String> errors = new ArrayList<String>();
        try {
            errors = workerFacade.loadImportWorkersFromSource(file.getInputStream());
        } catch (Exception e) {
            logger.error("{}", e);
            errors.add(e.getMessage());
        }

        JsonResponse response = new JsonResponse(errors.isEmpty());
        response.put("errors", errors);
        return response;
    }
}
