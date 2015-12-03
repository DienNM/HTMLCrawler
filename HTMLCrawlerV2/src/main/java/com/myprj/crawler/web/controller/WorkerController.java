package com.myprj.crawler.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.myprj.crawler.domain.PageResult;
import com.myprj.crawler.domain.Pageable;
import com.myprj.crawler.domain.crawl.WorkerData;
import com.myprj.crawler.service.WorkerService;
import com.myprj.crawler.web.dto.JsonResponse;

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
    public JsonResponse addItem(@RequestBody WorkerData worker) {
        WorkerData itemData = workerService.save(worker);
        return new JsonResponse(itemData, itemData != null);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse updateItem(@RequestBody WorkerData worker, @PathVariable(value = "id") long id) {
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
    
}
