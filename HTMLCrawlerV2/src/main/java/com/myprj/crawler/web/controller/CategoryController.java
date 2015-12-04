package com.myprj.crawler.web.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.myprj.crawler.domain.config.CategoryData;
import com.myprj.crawler.service.CategoryService;
import com.myprj.crawler.web.dto.CategoryDTO;
import com.myprj.crawler.web.dto.JsonResponse;
import com.myprj.crawler.web.enumeration.TargetDTOLevel;
import com.myprj.crawler.web.mapping.ObjectConverterUtil;

/**
 * @author DienNM (DEE)
 */
@Controller
@RequestMapping(value = "/categories", produces = "application/json")
public class CategoryController extends AbstractController {

    @Autowired
    private CategoryService categoryService;

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public JsonResponse getAll(@RequestParam(value = "level", defaultValue = "SIMPLE") TargetDTOLevel target) {
        List<CategoryData> categories = categoryService.getAll();
        List<CategoryDTO> categoryDTOs = new ArrayList<CategoryDTO>();
        ObjectConverterUtil.convert(categories, categoryDTOs, CategoryDTO.creation());
        return returnResponses(categoryDTOs, target);
    }

    @RequestMapping(value = "/{categoryId}", method = RequestMethod.GET)
    @ResponseBody
    public JsonResponse getById(@PathVariable(value = "categoryId") long id,
            @RequestParam(value = "level", defaultValue = "DEFAULT") TargetDTOLevel target) {
        CategoryData category = categoryService.getById(id);
        CategoryDTO categoryDTO = new CategoryDTO(); 
        ObjectConverterUtil.convert(category, categoryDTO);
        return returnResponse(categoryDTO, target);
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse addCategory(@RequestBody CategoryData categoryRequest) {
        CategoryData category = categoryService.save(categoryRequest);
        JsonResponse response = new JsonResponse(category != null);
        return response;
    }
}
