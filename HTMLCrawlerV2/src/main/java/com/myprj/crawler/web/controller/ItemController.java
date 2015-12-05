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
import com.myprj.crawler.domain.config.CategoryData;
import com.myprj.crawler.domain.config.ItemData;
import com.myprj.crawler.service.CategoryService;
import com.myprj.crawler.service.ItemService;
import com.myprj.crawler.web.dto.ItemDTO;
import com.myprj.crawler.web.dto.JsonResponse;
import com.myprj.crawler.web.dto.RequestError;
import com.myprj.crawler.web.enumeration.DTOLevel;

/**
 * @author DienNM (DEE)
 */
@Controller
@RequestMapping(value = "/items", produces = "application/json")
public class ItemController extends AbstractController {

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

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    @ResponseBody
    public JsonResponse getAll() {
        List<ItemData> itemDatas = itemService.getAll();

        List<Map<String, Object>> results = getListMapResult(itemDatas, ItemDTO.class, SIMPLE);
        JsonResponse response = new JsonResponse(results, !results.isEmpty());

        return response;
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public JsonResponse getPaging(@RequestParam(value = "currentPage", defaultValue = "0") int currentPage,
            @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
            @RequestParam(value = "level", defaultValue = "DEFAULT") DTOLevel level) {
        Pageable pageable = new Pageable(pageSize, currentPage);
        PageResult<ItemData> pageResult = itemService.getAllWithPaging(pageable);
        
        for(ItemData itemData : pageResult.getContent()) {
            populateObjectByLevel(itemData, level);
        }
        

        List<ItemDTO> itemDTOs = new ArrayList<ItemDTO>();
        ItemDTO.toItemDTOs(pageResult.getContent(), itemDTOs);

        List<Map<String, Object>> listDatas = getListMapResult(itemDTOs, level);
        JsonResponse response = new JsonResponse(listDatas, !listDatas.isEmpty());
        response.putPaging(pageResult.getPageable());

        return response;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public JsonResponse getItem(@PathVariable(value = "id") long id,
            @RequestParam(value = "level", defaultValue = "SIMPLE") DTOLevel level) {
        ItemData itemData = itemService.get(id);
        if (itemData == null) {
            JsonResponse response = new JsonResponse(false);
            response.putMessage("Item Id " + id + " not found");
            return response;
        }
        populateObjectByLevel(itemData, level);
        
        ItemDTO itemDTO = new ItemDTO();
        ItemDTO.toItemDTO(itemData, itemDTO);
        
        Map<String, Object> datas = getMapResult(itemDTO, level);
        JsonResponse response = new JsonResponse(!datas.isEmpty());
        response.putData(datas);

        return response;
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
    public JsonResponse addItem(@RequestParam(value = "name") String name,
            @RequestParam(value = "categoryId") long categoryId,
            @RequestParam(value = "description") String description) {

        List<RequestError> errors = new ArrayList<RequestError>();
        if (StringUtils.isEmpty(name)) {
            errors.add(new RequestError("name", "Item Name is required"));
        }
        CategoryData categoryData = categoryService.getById(categoryId);
        if(categoryData == null) {
            errors.add(new RequestError("categoryId", "Category Id " + categoryId + " not found"));
        }
        
        if (!errors.isEmpty()) {
            JsonResponse response = new JsonResponse(false);
            response.putErrors(errors);
            return response;
        }
        
        ItemData item = new ItemData();
        item.setName(name);
        item.setCategoryId(categoryId);
        item.setDescription(description);

        ItemData itemData = itemService.save(item);
        return new JsonResponse(itemData != null);
    }
    
    @RequestMapping(value = "/{id}/build", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse buildItemAttributes(@RequestParam(value = "file") MultipartFile file,
            @PathVariable(value = "id") long id,
            @RequestParam(value = "forceBuild", defaultValue = "false") boolean forceBuild) {

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
            
            ItemData itemData = itemService.buildItem(id, json, forceBuild);
            JsonResponse response = new JsonResponse(itemData, itemData != null);
            
            return response;
        } catch (Exception e) {
            JsonResponse response = new JsonResponse(false);
            response.putMessage(e.getMessage());
            return response;
        }
    }
}
