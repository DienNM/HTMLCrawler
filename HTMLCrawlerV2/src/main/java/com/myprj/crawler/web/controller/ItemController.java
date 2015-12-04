package com.myprj.crawler.web.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
import com.myprj.crawler.web.dto.ItemDTO;
import com.myprj.crawler.web.dto.JsonResponse;
import com.myprj.crawler.web.enumeration.TargetDTOLevel;
import com.myprj.crawler.web.mapping.DTOHandler;
import com.myprj.crawler.web.mapping.ObjectConverterUtil;

/**
 * @author DienNM (DEE)
 */
@Controller
@RequestMapping(value = "/items", produces = "application/json")
public class ItemController extends AbstractController {

    @Autowired
    private ItemService itemService;

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    @ResponseBody
    public JsonResponse getAll(@RequestParam(value = "level", defaultValue = "SIMPLE") TargetDTOLevel target) {
        List<ItemData> itemDatas = itemService.getAll();

        List<ItemDTO> itemDTOs = new ArrayList<ItemDTO>();
        ObjectConverterUtil.convert(itemDatas, itemDTOs, ItemDTO.creation());

        return returnResponses(itemDTOs, target);
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public JsonResponse getPaging(@RequestParam(value = "currentPage", defaultValue = "0") int currentPage,
            @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
            @RequestParam(value = "level", defaultValue = "SIMPLE") TargetDTOLevel target) {
        Pageable pageable = new Pageable(pageSize, currentPage);
        PageResult<ItemData> pageResult = itemService.getPaging(pageable);

        JsonResponse jsonResponse = returnDataPaging(pageResult);

        List<ItemDTO> itemDTOs = new ArrayList<ItemDTO>();
        ObjectConverterUtil.convert(pageResult.getContent(), itemDTOs, ItemDTO.creation());
        List<Map<String, Object>> json = DTOHandler.convert(itemDTOs, target);

        jsonResponse.putData(json);
        jsonResponse.put(JsonResponse.SUCCESS, !json.isEmpty());
        return jsonResponse;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public JsonResponse getItem(@PathVariable(value = "id") long id,
            @RequestParam(value = "level", defaultValue = "SIMPLE") TargetDTOLevel target) {
        ItemData itemData = itemService.get(id);
        ItemDTO itemDTO = new ItemDTO();
        ObjectConverterUtil.convert(itemData, itemDTO);

        return returnResponse(itemDTO, target);
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse deleteItem(@PathVariable(value = "id") long id) {
        try {
            itemService.delete(id);
            return new JsonResponse(true);
        } catch (Exception e) {
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
    public JsonResponse buildItem(@RequestParam(value = "file") MultipartFile file,
            @PathVariable(value = "id") long id,
            @RequestParam(value = "forceBuilt", defaultValue = "false") boolean forceBuilt) {

        if (file == null) {
            JsonResponse response = new JsonResponse(false);
            response.putMessage("Attributes are missing");
            return response;
        }

        String json = readLinesFile2String(file);
        if (StringUtils.isEmpty(json)) {
            JsonResponse response = new JsonResponse(false);
            response.putMessage("Attribute File is empty");
            return response;
        }

        try {
            ItemData itemData = itemService.buildItem(id, json, forceBuilt);
            itemService.populateAttributes(itemData);
            JsonResponse response = new JsonResponse(itemData, true);
            return response;
        } catch (Exception e) {
            JsonResponse response = new JsonResponse(false);
            response.putMessage(e.getMessage());
            return response;
        }
    }
}
