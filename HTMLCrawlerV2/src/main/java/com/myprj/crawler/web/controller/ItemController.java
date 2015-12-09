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
import com.myprj.crawler.domain.config.CategoryData;
import com.myprj.crawler.domain.config.ItemData;
import com.myprj.crawler.service.CategoryService;
import com.myprj.crawler.service.ItemService;
import com.myprj.crawler.web.dto.ItemDTO;
import com.myprj.crawler.web.dto.JsonResponse;
import com.myprj.crawler.web.dto.RequestError;
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

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public JsonResponse getItem(@PathVariable(value = "id") String id,
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

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse addItem(@RequestParam(value = "name", required = true) String name,
            @RequestParam(value = "id", required = true) String id,
            @RequestParam(value = "categoryId", required = true) String categoryId,
            @RequestParam(value = "description", required = false) String description) {

        List<RequestError> errors = new ArrayList<RequestError>();

        ItemData item = itemService.get(id);
        if (item != null) {
            errors.add(new RequestError("id", "id" + categoryId + " already exists"));
        }

        CategoryData categoryData = categoryService.getById(categoryId);
        if (categoryData == null) {
            errors.add(new RequestError("categoryId", "Category Id " + categoryId + " not found"));
        }

        if (!errors.isEmpty()) {
            JsonResponse response = new JsonResponse(false);
            response.putErrors(errors);
            return response;
        }

        item = new ItemData();
        item.setName(name);
        item.setCategoryId(categoryId);
        item.setDescription(description);

        item = itemService.save(item);
        return new JsonResponse(item != null);
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
