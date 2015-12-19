package com.myprj.crawler.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.myprj.crawler.service.index.IndexingService;
import com.myprj.crawler.web.dto.JsonResponse;

/**
 * @author DienNM (DEE)
 */
@Controller
@RequestMapping(value = "/indexes", produces = "application/json")
public class IndexingController {

    @Autowired
    private IndexingService indexingService;

    @RequestMapping(value = "/full", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse importFromFile() {
        indexingService.fullIndex();
        return new JsonResponse(true);
    }

}
