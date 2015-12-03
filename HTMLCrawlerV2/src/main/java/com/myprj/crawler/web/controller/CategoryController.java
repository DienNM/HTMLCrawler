package com.myprj.crawler.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.myprj.crawler.domain.config.CategoryData;
import com.myprj.crawler.service.CategoryService;
import com.myprj.crawler.web.dto.JsonResponse;

/**
 * @author DienNM (DEE)
 */
@Controller
@RequestMapping(value = "/categories", produces = "application/json")
public class CategoryController extends AbstractController{
    
    @Autowired
    private CategoryService categoryService;
    
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public JsonResponse getCategoryAllCategories() {
        List<CategoryData> categories = categoryService.getAll();
        JsonResponse response = new JsonResponse(categories , true);
        return response;
    }
    
    @RequestMapping(value = "/{categoryId}", method = RequestMethod.GET)
    @ResponseBody
    public JsonResponse getCategoryByIdCategories(@PathVariable(value = "categoryId") long categoryId) {
        CategoryData category = categoryService.getById(categoryId);
        JsonResponse response = new JsonResponse(category , category != null);
        return response;
    }
    
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse addCategory(@RequestBody CategoryData categoryRequest) {
        CategoryData category = categoryService.save(categoryRequest);
        JsonResponse response = new JsonResponse(category , category != null);
        return response;
    }
}
