package com.myprj.crawler.web.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.myprj.crawler.domain.crawl.CrawlResultData;
import com.myprj.crawler.service.CrawlResultService;
import com.myprj.crawler.web.dto.CrawlResultDTO;
import com.myprj.crawler.web.dto.JsonResponse;
import com.myprj.crawler.web.enumeration.DTOLevel;

/**
 * @author DienNM (DEE)
 */

@Controller
@RequestMapping(value = "/results", produces = "application/json")
public class ResultController extends AbstractController {
    
    @Autowired
    private CrawlResultService crawlResultService;
    
    @RequestMapping(value = "/{itemId}")
    @ResponseBody
    public JsonResponse getByItemId(@PathVariable(value = "itemId") long itemId, 
            @RequestParam(value = "level", defaultValue = "DEFAULT") DTOLevel level) {
        List<CrawlResultData> crawlResultDatas = crawlResultService.getByItemId(itemId);
        
        List<Map<String, Object>> listDatas = getListMapResult(crawlResultDatas, CrawlResultDTO.class, level);
        
        return new JsonResponse(listDatas, !listDatas.isEmpty());
    }    
}
