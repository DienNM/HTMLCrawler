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
import com.myprj.crawler.domain.config.ItemData;
import com.myprj.crawler.service.ItemService;
import com.myprj.crawler.util.StreamUtil;
import com.myprj.crawler.web.dto.JsonResponse;

/**
 * @author DienNM (DEE)
 */
@Controller
@RequestMapping(value = "/items", produces = "application/json")
public class ItemController extends AbstractController{

    @Autowired
    private ItemService itemService;

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    @ResponseBody
    public JsonResponse getAll() {
        List<ItemData> itemDatas = itemService.getAll();
        JsonResponse response = new JsonResponse(itemDatas, true);
        return response;
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public JsonResponse getPaging(@RequestParam(value = "currentPage", defaultValue = "0") int currentPage,
            @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        Pageable pageable = new Pageable(pageSize, currentPage);
        PageResult<ItemData> pageResult = itemService.getPaging(pageable);
        return returnDataPaging(pageResult);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public JsonResponse getItem(@PathVariable(value = "id") long id) {
        ItemData itemData = itemService.get(id);
        return new JsonResponse(itemData, itemData != null);
    }
    
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse deleteItem(@PathVariable(value = "id") long id) {
        try {
            itemService.delete(id);
            return new JsonResponse(true);
        } catch(Exception e) {
            JsonResponse response = new JsonResponse(false);
            response.putMessage(e.getMessage());
            return response;
        }
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse addItem(@RequestBody ItemData item) {
        ItemData itemData = itemService.save(item);
        return new JsonResponse(itemData, itemData != null);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse updateItem(@RequestBody ItemData item, @PathVariable(value = "id") long id) {
        if (id != item.getId()) {
            JsonResponse response = new JsonResponse(false);
            response.putMessage("Item Ids do not match: " + id + " vs " + item.getId());
            return response;
        }
        ItemData itemData = itemService.get(id);
        if (itemData == null) {
            JsonResponse response = new JsonResponse(false);
            response.putMessage(String.format("Item %s  not found", id));
            return response;
        }
        itemService.update(item);

        JsonResponse response = new JsonResponse(true);
        return response;
    }

    @RequestMapping(value = "/{id}/build", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse buildItem(@RequestParam(value = "file") MultipartFile file, @PathVariable(value = "id") long id,
            @RequestParam(value = "forceBuilt", defaultValue = "false") boolean forceBuilt) {
        
        if(file == null) {
            JsonResponse response = new JsonResponse(false);
            response.putMessage("Attributes are missing");
            return response;
        }
        
        String json = readLines2String(file);
        if(StringUtils.isEmpty(json)) {
            JsonResponse response = new JsonResponse(false);
            response.putMessage("Attribute File is empty");
            return response;
        }
        
        try {
            ItemData itemData = itemService.buildItem(id, json, forceBuilt);
            itemService.populateAttributes(itemData);
            JsonResponse response = new JsonResponse(itemData, true);
            return response;
        } catch(Exception e) {
            JsonResponse response = new JsonResponse(false);
            response.putMessage(e.getMessage());
            return response;
        }
    }
    
    private String readLines2String(MultipartFile file) {
        try {
            String content = StreamUtil.readFile2String(file.getInputStream());
            if(content == null) {
                return "";
            }
            return content.trim();
        } catch (Exception e) {
            return "";
        }
    }
}
