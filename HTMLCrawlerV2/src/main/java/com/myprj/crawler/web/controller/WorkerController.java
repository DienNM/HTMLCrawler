package com.myprj.crawler.web.controller;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
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
import com.myprj.crawler.web.dto.WorkerItemDTO;

/**
 * @author DienNM (DEE)
 */

@Controller
@RequestMapping(value = "/workers", produces = "application/json")
public class WorkerController  extends AbstractController{
    
    @Autowired
    private WorkerService workerService;
    
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    @ResponseBody
    public JsonResponse getAll() {
        List<WorkerData> workerDatas = workerService.getAll();
        JsonResponse response = new JsonResponse(workerDatas, true);
        return response;
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public JsonResponse getPaging(@RequestParam(value = "currentPage", defaultValue = "0") int currentPage,
            @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        Pageable pageable = new Pageable(pageSize, currentPage);
        PageResult<WorkerData> pageResult = workerService.getPaging(pageable);
        return returnDataPaging(pageResult); 
    }
    
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse addWorker(@RequestBody WorkerData worker) {
        WorkerData itemData = workerService.save(worker);
        return new JsonResponse(itemData, itemData != null);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse updateWorker(@RequestBody WorkerData worker, @PathVariable(value = "id") long id) {
        if (id != worker.getId()) {
            JsonResponse response = new JsonResponse(false);
            response.putMessage("Worker Ids do not match: " + id + " vs " + worker.getId());
            return response;
        }
        WorkerData workerData = workerService.get(id);
        if (workerData == null) {
            JsonResponse response = new JsonResponse(false);
            response.putMessage(String.format("WorkerId %s cannot find", id));
            return response;
        }
        workerService.update(worker);
        JsonResponse response = new JsonResponse(true);
        return response;
    }
    
    @RequestMapping(value = "/{id}/items", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse addWorkerItems(@RequestBody List<WorkerItemDTO> workerItemDTOs, @PathVariable(value = "id") long workerId) {
        WorkerData workerData = workerService.get(workerId);
        if (workerData == null) {
            JsonResponse response = new JsonResponse(false);
            response.putMessage(String.format("Worker Id %s not found", workerId));
            return response;
        }
        if(workerItemDTOs == null || workerItemDTOs.isEmpty()) {
            JsonResponse response = new JsonResponse(false);
            response.putMessage("No Worker Items");
            return response;
        }
        List<WorkerItemData> workerItems = WorkerItemDTO.toDatas(workerItemDTOs); 
        try {
            workerService.addWorkerItems(workerData, workerItems);
            JsonResponse response = new JsonResponse(workerData, !workerData.getWorkerItems().isEmpty());
            return response;
        } catch(Exception e) {
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
        if(StringUtils.isEmpty(json)) {
            JsonResponse response = new JsonResponse(false);
            response.putMessage("Attributes and Selectors File is empty");
            return response;
        }
        
        try {
            workerService.addWorkerItems(workerData, workerItems);
            JsonResponse response = new JsonResponse(workerData, !workerData.getWorkerItems().isEmpty());
            return response;
        } catch(Exception e) {
            JsonResponse response = new JsonResponse(false);
            response.putMessage(e.getMessage());
            return response;
        }
    }
    
    
}
