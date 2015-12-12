package com.myprj.crawler.web.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.myprj.crawler.domain.PageResult;
import com.myprj.crawler.domain.Pageable;
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

    @RequestMapping(value = "/by-request/{requestId}")
    @ResponseBody
    public JsonResponse getByRequestId(@PathVariable(value = "requestId") String requestId,
            @RequestParam(value = "level", defaultValue = "DEFAULT") DTOLevel level) {

        List<CrawlResultData> crawlResultDatas = crawlResultService.getByRequestId(requestId);

        List<CrawlResultDTO> crawlResultDTOs = new ArrayList<CrawlResultDTO>();
        CrawlResultDTO.toDTOs(crawlResultDatas, crawlResultDTOs);
        return new JsonResponse(crawlResultDTOs, !crawlResultDTOs.isEmpty());
    }

    @ResponseBody
    public JsonResponse query(@RequestParam(value = "siteKey", defaultValue = "") String siteKey,
            @RequestParam(value = "categoryKey", defaultValue = "") String categoryKey,
            @RequestParam(value = "itemKey", defaultValue = "") String itemKey,
            @RequestParam(value = "currentPage", defaultValue = "0") int currentPage,
            @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {

        Pageable pageable = new Pageable(pageSize, currentPage);
        PageResult<CrawlResultData>  pageResults = crawlResultService.getPaging(siteKey, categoryKey, itemKey, pageable);

        JsonResponse jsonResponse = new JsonResponse(!pageResults.getContent().isEmpty());
        jsonResponse.put("paging", pageResults.getPageable());
        jsonResponse.putData(pageResults.getContent());
        
        return jsonResponse;
    }
}
