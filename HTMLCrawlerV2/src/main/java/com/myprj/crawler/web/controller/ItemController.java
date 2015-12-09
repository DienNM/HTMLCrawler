package com.myprj.crawler.web.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
import com.myprj.crawler.service.CategoryService;
import com.myprj.crawler.service.ItemService;
import com.myprj.crawler.web.dto.ItemDTO;
import com.myprj.crawler.web.dto.JsonResponse;
import com.myprj.crawler.web.enumeration.DTOLevel;
import com.myprj.crawler.web.facades.ItemFacade;

/**
 * @author DienNM (DEE)
 */
@Controller
@RequestMapping(value = "/items", produces = "application/json")
public class ItemController extends AbstractController {

    @Autowired
    private ItemFacade itemFacade;

    @Autowired
    private ItemService itemService;

    @Autowired
    private CategoryService categoryService;

    private void populateObjectByLevel(ItemData itemData, DTOLevel level) {
        switch (level) {
        case FULL:
            itemService.populateAttributes(itemData);
            break;
        default:
            break;
        }
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public JsonResponse getPaging(@RequestParam(value = "currentPage", defaultValue = "0") int currentPage,
            @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
            @RequestParam(value = "level", defaultValue = "DEFAULT") DTOLevel level) {
        Pageable pageable = new Pageable(pageSize, currentPage);
        PageResult<ItemData> pageResult = itemService.getAllWithPaging(pageable);

        for (ItemData itemData : pageResult.getContent()) {
            populateObjectByLevel(itemData, level);
        }

        List<ItemDTO> itemDTOs = new ArrayList<ItemDTO>();
        ItemDTO.toItemDTOs(pageResult.getContent(), itemDTOs);

        List<Map<String, Object>> listDatas = getListMapResult(itemDTOs, level);
        JsonResponse response = new JsonResponse(listDatas, !listDatas.isEmpty());
        response.putPaging(pageResult.getPageable());

        return response;
    }

    @RequestMapping(value = "/{ids}", method = RequestMethod.GET)
    @ResponseBody
    public JsonResponse getItem(@PathVariable(value = "ids") List<String> ids,
            @RequestParam(value = "level", defaultValue = "SIMPLE") DTOLevel level) {
        List<ItemData> itemDatas = itemService.get(ids);
        for(ItemData itemData : itemDatas) {
            populateObjectByLevel(itemData, level);
        }

        List<ItemDTO> itemDTOs = new ArrayList<ItemDTO>();
        ItemDTO.toItemDTOs(itemDatas, itemDTOs);

        List<Map<String, Object>> datas = getListMapResult(itemDTOs, level);
        JsonResponse response = new JsonResponse(!datas.isEmpty());
        response.putData(datas);

        return response;
    }

    @RequestMapping(value = "/{ids}/delete", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse deleteItems(@PathVariable(value = "ids") List<String> ids) {
        try {
            itemService.delete(ids);
            return new JsonResponse(true);
        } catch (Exception e) {
            JsonResponse response = new JsonResponse(false);
            response.putMessage(e.getMessage());
            return response;
        }
    }

    @RequestMapping(value = "/import", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse importFromFile(@RequestParam(value = "file") MultipartFile file,
            @RequestParam(value = "forceBuild", defaultValue = "false") boolean forceBuild) {
        if (file == null) {
            JsonResponse response = new JsonResponse(false);
            response.putMessage("File are missing");
            return response;
        }

        List<String> errors = new ArrayList<String>();
        try {
            errors = itemFacade.loadItemsFromSource(file.getInputStream(), forceBuild);
        } catch (Exception e) {
            errors.add(e.getMessage());
        }
        JsonResponse response = new JsonResponse(errors.isEmpty());
        response.put("error", errors);
        return response;
    }

    @RequestMapping(value = "/structures/build/multi", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse buildItemStructute(@RequestParam(value = "file") MultipartFile file,
            @RequestParam(value = "forceBuild", defaultValue = "false") boolean forceBuild) {

        if (file == null) {
            JsonResponse response = new JsonResponse(false);
            response.putMessage("File are missing");
            return response;
        }

        try {
            List<String> errorItems = itemFacade.buildItemStructure(file.getInputStream(), forceBuild);
            JsonResponse response = new JsonResponse(true);
            response.put("errorItems", errorItems);
            return response;
        } catch (Exception e) {
            JsonResponse response = new JsonResponse(false);
            response.putMessage(e.getMessage());
            return response;
        }
    }
}
