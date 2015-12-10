package com.myprj.crawler.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
            @RequestParam(value = "selector1", required = false) String selector1,
            @RequestParam(value = "selector2", required = false) String selector2,
            @RequestParam(value = "selector3", required = false) String selector3,
            @RequestParam(value = "selector4", required = false) String selector4,
            @RequestParam(value = "selector5", required = false) String selector5,
            @RequestParam(value = "url") String url,
            @RequestParam(value = "type", defaultValue = "TEXT") String type) {
        
        JsonResponse jsonResponse = new JsonResponse(true);
        Document document = HtmlDownloader.download(url);
        
        List<String> selectors = new ArrayList<String>();
        selectors.add(selector);
        selectors.add(selector1);
        selectors.add(selector2);
        selectors.add(selector3);
        selectors.add(selector4);
        selectors.add(selector5);
        String[] types = type.split(",");

        List<Object> results = new ArrayList<Object>();
        for(int i = 0; i < selectors.size(); i++) {
            if(types.length > i) {
                AttributeSelector attributeSelector = AttributeSelectorUtil.parseAttritbuteSelector(selectors.get(i));
                AttributeHandler handler = HandlerRegister.getHandler(AttributeType.valueOf(types[i]));
                if(handler == null) {
                    jsonResponse = new JsonResponse(false);
                    jsonResponse.putMessage("No handler for type: " + type);
                }
                Object object = handler.handle(new HtmlDocument(document), attributeSelector);
                Map<String, Object> info = new HashMap<String, Object>();
                
                info.put("CSS-Selector", attributeSelector);
                info.put("Type", types[i]);
                info.put("Data", object);
                results.add(info);
            }
        }
        jsonResponse.put("result", results);
        return jsonResponse;
    }

}
