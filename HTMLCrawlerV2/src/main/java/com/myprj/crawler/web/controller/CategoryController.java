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
import com.myprj.crawler.web.dto.Response;

/**
 * @author DienNM (DEE)
 */
@Controller
@RequestMapping(value = "/categories", produces = "application/json")
public class CategoryController {
    
    @Autowired
    private CategoryService categoryService;
    
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public Response getCategoryAllCategories() {
        List<CategoryData> categories = categoryService.getAll();
        Response response = new Response(categories , true);
        return response;
    }
    
    @RequestMapping(value = "/{categoryId}", method = RequestMethod.GET)
    @ResponseBody
    public Response getCategoryByIdCategories(@PathVariable(value = "categoryId") long categoryId) {
        CategoryData category = categoryService.getById(categoryId);
        Response response = new Response(category , category != null);
        return response;
    }
    
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public Response addCategory(@RequestBody CategoryData categoryRequest) {
        CategoryData category = categoryService.save(categoryRequest);
        Response response = new Response(category , category != null);
        return response;
    }
}
