package com.myprj.crawler.web.controller;

import static com.myprj.crawler.enumeration.CrawlType.DETAIL;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
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
import com.myprj.crawler.domain.config.ItemData;
import com.myprj.crawler.domain.crawl.WorkerData;
import com.myprj.crawler.domain.crawl.WorkerItemData;
import com.myprj.crawler.enumeration.Level;
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

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public JsonResponse getWorker(@PathVariable(value = "id") long id,
            @RequestParam(value = "level", defaultValue = "DEFAULT") DTOLevel level) {

        WorkerData workerData = workerService.get(id);
        if (workerData == null) {
            JsonResponse response = new JsonResponse(false);
            response.putMessage("Worker Id " + id + " not found");
            return response;
        }

        populateWorkerByLevel(workerData, level);

        Map<String, Object> datas = getMapResult(workerData, WorkerDTO.class, level);
        JsonResponse response = new JsonResponse(!datas.isEmpty());
        response.putData(datas);

        return response;
    }

    @RequestMapping(value = "/import", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse importWorkersAndItems(@RequestParam(value = "file") MultipartFile file,
            @RequestParam(value = "forceBuild", defaultValue = "false") boolean forceBuild) {

        if (file == null) {
            JsonResponse response = new JsonResponse(false);
            response.putMessage("File are missing");
            return response;
        }
        List<String> errors = new ArrayList<String>();
        try {
            errors = workerFacade.loadImportWorkersFromSource(file.getInputStream(), forceBuild);
        } catch (Exception e) {
            logger.error("{}", e);
            errors.add(e.getMessage());
        }

        JsonResponse response = new JsonResponse(errors.isEmpty());
        response.put("errors", errors);
        return response;
    }

    @RequestMapping(value = "/items/{level}", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse buildSelectors4WorkerItems(@PathVariable(value = "level") Level level,
            @RequestParam(value = "file") MultipartFile file) {

        List<Map<String, String>> listItemLines = loadItemAttributes(file);
        for (Map<String, String> itemLineMap : listItemLines) {
            for (String key : itemLineMap.keySet()) {
                String json = itemLineMap.get(key);
                String[] arrays = key.split(Pattern.quote("|"));
                String[] workerIds = arrays[0].split(",");
                String itemKey = arrays[1];
                ItemData itemData = itemService.get(itemKey);
                if (itemData == null) {
                    logger.warn("Item Id " + itemKey + " not found");
                    continue;
                }
                for (String workerId : workerIds) {
                    WorkerData workerData = workerService.get(Long.valueOf(workerId));
                    if (workerData == null) {
                        logger.warn("Worker Id " + workerId + " not found");
                        continue;
                    }

                    WorkerItemData workerItemData = workerService.getWorkerItem(workerData, level);
                    if (workerItemData == null) {
                        JsonResponse response = new JsonResponse(false);
                        response.putMessage("Cannot find worker item for: WorkerId=" + workerId + " and Level=" + level);
                        return response;
                    }

                    if (!DETAIL.equals(workerItemData.getCrawlType())) {
                        JsonResponse response = new JsonResponse(false);
                        response.putMessage("Only be able to build selectors for DETAIL type");
                        return response;
                    }
                    workerItemData.setItemKey(itemKey);
                    try {
                        workerItemData = workerService.buildSelector4Item(workerItemData, json);
                    } catch (Exception e) {
                        logger.error("{}", e);
                    }
                }
            }
        }
        JsonResponse response = new JsonResponse(true);
        return response;
    }

    protected List<Map<String, String>> loadItemAttributes(MultipartFile file) {
        List<Map<String, String>> itemAttributes = new ArrayList<Map<String, String>>();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(file.getInputStream()));
            String line = null;

            List<String> lines = new ArrayList<String>();
            boolean loadingItemAttributeLines = false;

            String currentKey = null;

            while ((line = br.readLine()) != null) {
                if (line == null || line.trim().isEmpty() || line.startsWith("#")) {
                    continue;
                }
                if (line.startsWith(">") || line.startsWith("<END")) {
                    if (!lines.isEmpty()) {
                        Map<String, String> map = itemAttributes.get(itemAttributes.size() - 1);
                        map.put(currentKey, StringUtils.join(lines, " "));
                        loadingItemAttributeLines = false;
                        currentKey = null;
                    }
                }

                if (loadingItemAttributeLines) {
                    lines.add(line);
                }

                if (line.startsWith(">")) {
                    line = line.substring(1);
                    currentKey = line;
                    Map<String, String> map = new HashMap<String, String>();
                    map.put(line, null);
                    itemAttributes.add(map);

                    loadingItemAttributeLines = true;
                }
            }
        } catch (Exception ex) {
            return new ArrayList<Map<String, String>>();
        } finally {
            IOUtils.closeQuietly(br);
        }
        return itemAttributes;
    }
}
