package com.myprj.crawler.web.controller;

import static com.myprj.crawler.web.enumeration.DTOLevel.SIMPLE;

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
import com.myprj.crawler.service.CategoryService;
import com.myprj.crawler.web.dto.CategoryDTO;
import com.myprj.crawler.web.dto.JsonResponse;
import com.myprj.crawler.web.enumeration.DTOLevel;
import com.myprj.crawler.web.facades.CategoryFacade;

/**
 * @author DienNM (DEE)
 */
@Controller
@RequestMapping(value = "/categories", produces = "application/json")
public class CategoryController extends AbstractController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CategoryFacade categoryFacade;

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    @ResponseBody
    public JsonResponse getAll() {
        List<CategoryData> categories = categoryService.getAll();

        List<Map<String, Object>> results = getListMapResult(categories, CategoryDTO.class, SIMPLE);
        JsonResponse response = new JsonResponse(results, !results.isEmpty());

        return response;
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public JsonResponse getPaging(@RequestParam(value = "currentPage", defaultValue = "0") int currentPage,
            @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
            @RequestParam(value = "level", defaultValue = "SIMPLE") DTOLevel level) {

        Pageable pageable = new Pageable(pageSize, currentPage);
        PageResult<CategoryData> pageResult = categoryService.getAllWithPaging(pageable);

        List<CategoryData> sources = pageResult.getContent();
        List<Map<String, Object>> listDatas = getListMapResult(sources, CategoryDTO.class, level);
        JsonResponse jsonResponse = new JsonResponse(listDatas, !listDatas.isEmpty());
        jsonResponse.putPaging(pageResult.getPageable());

        return jsonResponse;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public JsonResponse getByKey(@PathVariable(value = "id") String id,
            @RequestParam(value = "level", defaultValue = "DEFAULT") DTOLevel target) {

        CategoryData category = categoryService.getById(id);
        if (category == null) {
            JsonResponse jsonResponse = new JsonResponse(false);
            jsonResponse.putMessage("Category Id " + id + " not found");
            return jsonResponse;
        }
        Map<String, Object> datas = getMapResult(category, CategoryDTO.class, target);
        JsonResponse response = new JsonResponse(!datas.isEmpty());
        response.putData(datas);

        return response;
    }
    
    @RequestMapping(value = "/{ids}/delete", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse delete(@PathVariable(value = "ids") List<String> ids) {
        try {
            categoryService.delete(ids);
            return new JsonResponse(true);
        } catch (Exception e) {
            JsonResponse response = new JsonResponse(false);
            response.putMessage(e.getMessage());
            return response;
        }
    }

    @RequestMapping(value = "/import", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse importFromFile(@RequestParam(value = "file") MultipartFile file) {

        if (file == null) {
            JsonResponse response = new JsonResponse(false);
            response.putMessage("File are missing");
            return response;
        }

        List<CategoryData> categoryDatas = new ArrayList<CategoryData>();
        ;
        try {
            categoryDatas = categoryFacade.loadCategoriesFromSource(file.getInputStream());
            if (categoryDatas.isEmpty()) {
                JsonResponse response = new JsonResponse(false);
                response.putMessage("No Categories are loaded");
                return response;
            }
            categoryDatas = categoryService.saveOrUpdate(categoryDatas);
        } catch (Exception e) {
            JsonResponse response = new JsonResponse(false);
            response.putMessage(e.getMessage());
            return response;
        }

        JsonResponse response = new JsonResponse(true);
        response.putMessage("Loaded " + categoryDatas.size() + " categories");
        return response;
    }
}
