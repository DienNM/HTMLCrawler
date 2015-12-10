package com.myprj.crawler.web.controller;

import java.util.ArrayList;
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
    
    @RequestMapping(value = "/by-item/{itemKey}/normal")
    @ResponseBody
    public JsonResponse getByItemKeyNormal(@PathVariable(value = "itemKey") String itemKey) {
        
        List<CrawlResultData> crawlResultDatas = crawlResultService.getByItemKey(itemKey);
        List<CrawlResultDTO> crawlResultDTOs = new ArrayList<CrawlResultDTO>();
        CrawlResultDTO.toDTOs(crawlResultDatas, crawlResultDTOs);
        
        return new JsonResponse(crawlResultDTOs, !crawlResultDTOs.isEmpty());
    }
    
    @RequestMapping(value = "/by-category/{itemId}/normal")
    @ResponseBody
    public JsonResponse getByCategoryKeyNormal(@PathVariable(value = "categoryKey") String categoryKey) {
        
        List<CrawlResultData> crawlResultDatas = crawlResultService.getByCategoryKey(categoryKey);

        List<CrawlResultDTO> crawlResultDTOs = new ArrayList<CrawlResultDTO>(); 
        CrawlResultDTO.toDTOs(crawlResultDatas, crawlResultDTOs);
        return new JsonResponse(crawlResultDTOs, !crawlResultDTOs.isEmpty());
    }    
    
    @RequestMapping(value = "/by-item/{itemKey}")
    @ResponseBody
    public JsonResponse getByItemKey(@PathVariable(value = "itemKey") String itemKey, 
            @RequestParam(value = "level", defaultValue = "DEFAULT") DTOLevel level) {
        
        List<CrawlResultData> crawlResultDatas = crawlResultService.getByItemKey(itemKey);
        List<Map<String, Object>> listDatas = getListMapResult(crawlResultDatas, CrawlResultDTO.class, level);
        
        return new JsonResponse(listDatas, !listDatas.isEmpty());
    }
    
    @RequestMapping(value = "/by-category/{itemId}")
    @ResponseBody
    public JsonResponse getByCategoryKey(@PathVariable(value = "categoryKey") String categoryKey, 
            @RequestParam(value = "level", defaultValue = "DEFAULT") DTOLevel level) {
        
        List<CrawlResultData> crawlResultDatas = crawlResultService.getByCategoryKey(categoryKey);
        List<Map<String, Object>> listDatas = getListMapResult(crawlResultDatas, CrawlResultDTO.class, level);
        
        return new JsonResponse(listDatas, !listDatas.isEmpty());
    }  
    
    @RequestMapping(value = "/by-request/{requestId}")
    @ResponseBody
    public JsonResponse getByRequestId(@PathVariable(value = "requestId") String requestId, 
            @RequestParam(value = "level", defaultValue = "DEFAULT") DTOLevel level) {
        
        List<CrawlResultData> crawlResultDatas = crawlResultService.getByRequestId(requestId);
        List<Map<String, Object>> listDatas = getListMapResult(crawlResultDatas, CrawlResultDTO.class, level);
        
        return new JsonResponse(listDatas, !listDatas.isEmpty());
    }  
}
