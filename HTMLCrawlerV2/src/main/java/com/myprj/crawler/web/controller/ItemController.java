package com.myprj.crawler.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.myprj.crawler.domain.PageResult;
import com.myprj.crawler.domain.Pageable;
import com.myprj.crawler.domain.config.ItemData;
import com.myprj.crawler.service.ItemService;
import com.myprj.crawler.web.dto.Response;

/**
 * @author DienNM (DEE)
 */
@Controller
@RequestMapping(value = "/items", produces = "application/json")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public Response getAll() {
        List<ItemData> itemDatas = itemService.getAll();
        Response response = new Response(itemDatas, true);
        return response;
    }

    @RequestMapping(method = RequestMethod.GET)
    public Response getPaging(@RequestParam(value = "currentPage", defaultValue = "0") int currentPage,
            @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        Pageable pageable = new Pageable(pageSize, currentPage);
        PageResult<ItemData> pageResult = itemService.getAll(pageable);
        Response response = new Response(true);
        response.putData(pageResult.getContent());
        response.put("pageSize", pageSize);
        response.put("currentPage", currentPage);
        response.put("totalPages", pageResult.getTotalPages());
        response.put("totalRecords", pageResult.getTotalRecords());
        return response;
    }

    @RequestMapping(value = "/id", method = RequestMethod.GET)
    public Response getItem(@PathVariable(value = "id") long id) {
        ItemData itemData = itemService.get(id);
        return new Response(itemData, itemData != null);
    }
    
    @RequestMapping(method = RequestMethod.POST)
    public Response addItem(@RequestBody ItemData item) {
        ItemData itemData = itemService.save(item);
        return new Response(itemData, itemData != null);
    }
    
}
