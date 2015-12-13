package com.myprj.crawler.web.controller;

import static com.myprj.crawler.web.enumeration.DTOLevel.SIMPLE;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.myprj.crawler.domain.RuleScriptData;
import com.myprj.crawler.service.RuleScriptService;
import com.myprj.crawler.web.dto.JsonResponse;
import com.myprj.crawler.web.dto.RuleScriptDTO;
import com.myprj.crawler.web.enumeration.DTOLevel;
import com.myprj.crawler.web.facades.RuleScriptFacade;

/**
 * @author DienNM (DEE)
 */

@Controller
@RequestMapping(value = "/categories", produces = "application/json")
public class RuleScriptController extends AbstractController {
    
    @Autowired
    private RuleScriptFacade ruleScriptFacade;
    
    @Autowired
    private RuleScriptService ruleScriptService;
    
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    @ResponseBody
    public JsonResponse getAll() {
        List<RuleScriptData> ruleScripts = ruleScriptService.getAll();
        List<Map<String, Object>> results = getListMapResult(ruleScripts, RuleScriptDTO.class, SIMPLE);
        JsonResponse response = new JsonResponse(results, !results.isEmpty());
        return response;
    }

    @RequestMapping(value = "/{codes}", method = RequestMethod.GET)
    @ResponseBody
    public JsonResponse getByKey(@PathVariable(value = "codes") List<String> codes,
            @RequestParam(value = "level", defaultValue = "DEFAULT") DTOLevel target) {

        List<RuleScriptData> ruleScripts = ruleScriptService.get(codes);
        List<Map<String, Object>> datas = getListMapResult(ruleScripts, RuleScriptDTO.class, target);
        JsonResponse response = new JsonResponse(!datas.isEmpty());
        response.putData(datas);

        return response;
    }
    
    @RequestMapping(value = "/import", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse importFromFile(@RequestParam(value = "file") MultipartFile file) {
        if (file == null) {
            JsonResponse response = new JsonResponse(false);
            response.putMessage("File are missing");
            return response;
        }

        List<RuleScriptData> ruleScriptDatas = new ArrayList<RuleScriptData>();
        ;
        try {
            ruleScriptDatas = ruleScriptFacade.loadScriptsFromSource(file.getInputStream());
            if (ruleScriptDatas.isEmpty()) {
                JsonResponse response = new JsonResponse(false);
                response.putMessage("No Scripts are loaded");
                return response;
            }
            ruleScriptService.saveOrUpdate(ruleScriptDatas);
        } catch (Exception e) {
            JsonResponse response = new JsonResponse(false);
            response.putMessage(e.getMessage());
            return response;
        }

        JsonResponse response = new JsonResponse(true);
        response.putMessage("Loaded " + ruleScriptDatas.size() + " scripts");
        return response;
    }
}
