package com.myprj.crawler.web.controller;

import static com.myprj.crawler.web.enumeration.DTOLevel.SIMPLE;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
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
import com.myprj.crawler.domain.crawl.WorkerItemData;
import com.myprj.crawler.enumeration.Level;
import com.myprj.crawler.service.WorkerService;
import com.myprj.crawler.web.dto.JsonResponse;
import com.myprj.crawler.web.dto.RequestError;
import com.myprj.crawler.web.dto.WorkerDTO;
import com.myprj.crawler.web.dto.WorkerItemDTO;
import com.myprj.crawler.web.enumeration.DTOLevel;

/**
 * @author DienNM (DEE)
 */

@Controller
@RequestMapping(value = "/workers", produces = "application/json")
public class WorkerController extends AbstractController {

    @Autowired
    private WorkerService workerService;

    private void populateObjectByLevel(WorkerData worker, DTOLevel level) {
        switch (level) {
        case FULL:
            workerService.populateWorkerItems(worker);
            break;
        default:
            break;
        }
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    @ResponseBody
    public JsonResponse getAll() {
        List<WorkerData> workerDatas = workerService.getAll();

        List<Map<String, Object>> results = getListMapResult(workerDatas, WorkerDTO.class, SIMPLE);
        JsonResponse response = new JsonResponse(results, !results.isEmpty());
        return response;
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

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public JsonResponse getWorker(@PathVariable(value = "id") long id,
            @RequestParam(value = "level", defaultValue = "DEFAULT") DTOLevel level) {

        WorkerData workerData = workerService.get(id);
        if (workerData == null) {
            JsonResponse response = new JsonResponse(false);
            response.putMessage("Worker Id " + id + " not found");
            return response;
        }

        populateObjectByLevel(workerData, level);
        
        Map<String, Object> datas = getMapResult(workerData, WorkerDTO.class, level);
        JsonResponse response = new JsonResponse(!datas.isEmpty());
        response.putData(datas);

        return response;
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse addWorker(@RequestParam(value = "name") String name, 
            @RequestParam(value = "site") String site, 
            @RequestParam(value = "description") String description, 
            @RequestParam(value = "threads", defaultValue = "1") int threads, 
            @RequestParam(value = "attemptTimes", defaultValue = "3") int attemptTimes) {
        
        List<RequestError> errors = new ArrayList<RequestError>();
        
        if(StringUtils.isEmpty(name)) {
            errors.add(new RequestError("name", "Name is required"));
        }
        if(StringUtils.isEmpty(name)) {
            errors.add(new RequestError("site", "Site is required"));
        }
        
        if(!errors.isEmpty()) {
            JsonResponse jsonResponse = new JsonResponse(false);
            jsonResponse.putErrors(errors);
            return jsonResponse;
        }
        
        WorkerData worker = new WorkerData();
        worker.setName(name);
        worker.setSite(site);
        worker.setDescription(description);
        worker.setThreads(threads);
        worker.setAttemptTimes(attemptTimes);
        
        WorkerData itemData = workerService.save(worker);
        return new JsonResponse(itemData != null);
    }

    @RequestMapping(value = "/{id}/items", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse addWorkerItems(
            @RequestParam(value = "file") MultipartFile file,
            @PathVariable(value = "id") long workerId) {
        
        if (file == null) {
            JsonResponse response = new JsonResponse(false);
            response.putMessage("Worker Items File are missing");
            return response;
        }

        List<WorkerItemDTO> workerItemDTOs = readLinesFile2List(file, WorkerItemDTO.class);
        if (workerItemDTOs.isEmpty()) {
            JsonResponse response = new JsonResponse(false);
            response.putMessage("No Worker Items");
            return response;
        }
        
        List<WorkerItemData> workerItems = new ArrayList<WorkerItemData>();
        WorkerItemDTO.toDatasDeeply(workerItemDTOs, workerItems);
        
        WorkerData workerData = workerService.get(workerId);
        
        if (workerData == null) {
            JsonResponse response = new JsonResponse(false);
            response.putMessage(String.format("Worker Id %s not found", workerId));
            return response;
        }
        
        try {
            workerService.addWorkerItems(workerData, workerItems);
            JsonResponse response = new JsonResponse(workerData, !workerData.getWorkerItems().isEmpty());
            return response;
        } catch (Exception e) {
            JsonResponse response = new JsonResponse(false);
            response.putMessage(e.getMessage());
            return response;
        }
    }

    @RequestMapping(value = "/{id}/items/{level}", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse buildSelector4WorkerItem(@PathVariable(value = "id") long workerId,
            @PathVariable(value = "level") Level level, @RequestParam(value = "file") MultipartFile file) {
        WorkerData workerData = workerService.get(workerId);
        if (workerData == null) {
            JsonResponse response = new JsonResponse(false);
            response.putMessage(String.format("Worker Id %s not found", workerId));
            return response;
        }

        String json = readLinesFile2String(file);
        if (StringUtils.isEmpty(json)) {
            JsonResponse response = new JsonResponse(false);
            response.putMessage("Attributes and Selectors File is empty");
            return response;
        }

        try {
            workerService.buildSelector4Item(workerData, level, json);
            JsonResponse response = new JsonResponse(workerData, !workerData.getWorkerItems().isEmpty());
            return response;
        } catch (Exception e) {
            JsonResponse response = new JsonResponse(false);
            response.putMessage(e.getMessage());
            return response;
        }
    }
}
