package com.myprj.crawler.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.myprj.crawler.domain.target.AttributeMappingData;
import com.myprj.crawler.service.target.AttributeMappingService;
import com.myprj.crawler.web.dto.JsonResponse;
import com.myprj.crawler.web.facades.AttributeMappingFacade;

/**
 * @author DienNM (DEE)
 */
@Controller
@RequestMapping(value = "/mappings", produces = "application/json")
public class MappingController {

    @Autowired
    private AttributeMappingFacade attributeMappingFacade;

    @Autowired
    private AttributeMappingService attributeMappingService;

    @RequestMapping(value = "/import", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse importFromFile(@RequestParam(value = "file") MultipartFile file) {
        if (file == null) {
            JsonResponse response = new JsonResponse(false);
            response.putMessage("File are missing");
            return response;
        }
        try {
            List<AttributeMappingData> attributeDatas = attributeMappingFacade.importFromSource(file.getInputStream());
            JsonResponse response = new JsonResponse(!attributeDatas.isEmpty());
            response.putMessage("Loaded " + attributeDatas.size() + " attribute mappings");
            return response;
        } catch (Exception e) {
            JsonResponse response = new JsonResponse(false);
            response.putMessage(e.getMessage());
            return response;
        }
    }

}
