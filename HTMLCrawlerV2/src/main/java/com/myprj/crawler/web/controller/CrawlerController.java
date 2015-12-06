package com.myprj.crawler.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.myprj.crawler.domain.RequestCrawl;
import com.myprj.crawler.service.crawl.CrawlerHandler;
import com.myprj.crawler.service.crawl.CrawlerService;
import com.myprj.crawler.web.dto.JsonResponse;

/**
 * @author DienNM (DEE)
 */
@Controller
@RequestMapping(value = "/crawler", produces = "application/json")
public class CrawlerController extends AbstractController {

    @Autowired
    @Qualifier("defaultCrawlerService")
    private CrawlerService defaultCrawlerService;

    @Autowired
    private CrawlerHandler crawlerHandler;

    @RequestMapping(value = "/{workerId}")
    @ResponseBody
    public JsonResponse crawl(@PathVariable(value = "workerId") long workerId,
            @RequestParam(value = "type", defaultValue = "none") String type) {

        try {
            RequestCrawl requestCrawl = new RequestCrawl();
            requestCrawl.setWorkerId(workerId);
            requestCrawl.getType();
            
            String requestId = crawlerHandler.handle(type, requestCrawl);
            if(requestId != null) {
                JsonResponse response = new JsonResponse(true);
                response.putMessage("Crawler is running with request Id=" + requestId);
                return response;
            }
            return new JsonResponse(false);
        } catch (Exception e) {
            JsonResponse response = new JsonResponse(false);
            response.putMessage(e.getMessage());
            return response;
        }
    }

}
