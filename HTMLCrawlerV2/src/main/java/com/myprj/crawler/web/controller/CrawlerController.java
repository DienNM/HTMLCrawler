package com.myprj.crawler.web.controller;

import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.myprj.crawler.domain.HtmlDocument;
import com.myprj.crawler.domain.RequestCrawl;
import com.myprj.crawler.domain.config.AttributeSelector;
import com.myprj.crawler.domain.crawl.WorkerData;
import com.myprj.crawler.enumeration.AttributeType;
import com.myprj.crawler.service.WorkerService;
import com.myprj.crawler.service.crawl.CrawlerHandler;
import com.myprj.crawler.service.crawl.CrawlerService;
import com.myprj.crawler.service.handler.AttributeHandler;
import com.myprj.crawler.service.handler.HandlerRegister;
import com.myprj.crawler.util.AttributeSelectorUtil;
import com.myprj.crawler.util.HtmlDownloader;
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
    
    @Autowired
    private WorkerService workerService;

    @RequestMapping(value = "/{workerKey}", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse crawl(@PathVariable(value = "workerKey") String workerKey,
            @RequestParam(value = "type", defaultValue = "none") String type) {

        try {
            
            WorkerData workerData = workerService.getByKey(workerKey);
            if(workerData == null){
                JsonResponse response = new JsonResponse(true);
                response.putMessage("Worker key " + workerKey + " not found");
                return response;
            }
            
            RequestCrawl requestCrawl = new RequestCrawl();
            requestCrawl.setWorkerKey(workerKey);
            requestCrawl.setWorkerId(workerData.getId());
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
    
    @RequestMapping(value = "/css-selector/check", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse checkCssSelector(
            @RequestParam(value = "selector") String selector,
            @RequestParam(value = "url") String url,
            @RequestParam(value = "type", defaultValue = "TEXT") AttributeType type) {
        
        JsonResponse jsonResponse = new JsonResponse(true);
        AttributeSelector attributeSelector = AttributeSelectorUtil.parseAttritbuteSelector(selector);
        
        AttributeHandler handler = HandlerRegister.getHandler(type);
        if(handler == null) {
            jsonResponse = new JsonResponse(false);
            jsonResponse.putMessage("No handler for type: " + type);
        }
        
        Document document = HtmlDownloader.download(url);
        Object object = handler.handle(new HtmlDocument(document), attributeSelector);
        
        jsonResponse.put("CSS-Selector", attributeSelector);
        jsonResponse.put("result", object);
        
        return jsonResponse;
    }

}
