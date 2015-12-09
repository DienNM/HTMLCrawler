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

import com.myprj.crawler.domain.SiteData;
import com.myprj.crawler.service.SiteService;
import com.myprj.crawler.web.dto.CategoryDTO;
import com.myprj.crawler.web.dto.JsonResponse;
import com.myprj.crawler.web.dto.SiteDTO;
import com.myprj.crawler.web.enumeration.DTOLevel;
import com.myprj.crawler.web.facades.SiteFacade;

/**
 * @author DienNM (DEE)
 */

@Controller
@RequestMapping(value = "/sites", produces = "application/json")
public class SiteController extends AbstractController {

    @Autowired
    private SiteService siteService;

    @Autowired
    private SiteFacade siteFacade;

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    @ResponseBody
    public JsonResponse getAll() {
        List<SiteData> siteDats = siteService.getAll();
        List<Map<String, Object>> results = getListMapResult(siteDats, SiteDTO.class, SIMPLE);
        JsonResponse response = new JsonResponse(results, !results.isEmpty());
        return response;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public JsonResponse getByKey(@PathVariable(value = "id") String id,
            @RequestParam(value = "level", defaultValue = "DEFAULT") DTOLevel level) {

        SiteData siteData = siteService.get(id);
        if(siteData == null) {
            JsonResponse response = new JsonResponse(false);
            response.putMessage("Site ID " + id + " not found");
            return response;
        }
        SiteDTO siteDTO = new SiteDTO();
        SiteDTO.toDTO(siteData, siteDTO);
        Map<String, Object> datas = getMapResult(siteDTO, level);

        JsonResponse response = new JsonResponse(!datas.isEmpty());
        response.putData(datas);

        return response;
    }

    @RequestMapping(value = "/{id}/delete", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse delete(@PathVariable(value = "id") String id) {
        try {
            siteService.delete(id);
            return new JsonResponse(true);
        } catch (Exception e) {
            JsonResponse response = new JsonResponse(false);
            response.putMessage(e.getMessage());
            return response;
        }
    }

    @RequestMapping(value = "/import", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse importFromFile(@RequestParam(value = "file") MultipartFile file) {

        if (file == null) {
            JsonResponse response = new JsonResponse(false);
            response.putMessage("File are missing");
            return response;
        }
        List<SiteData> siteDatas = new ArrayList<SiteData>();
        try {
            siteDatas = siteFacade.loadSitesFromSource(file.getInputStream());
            if (siteDatas.isEmpty()) {
                JsonResponse response = new JsonResponse(false);
                response.putMessage("No Sites are loaded");
                return response;
            }
            siteDatas = siteService.saveOrUpdate(siteDatas);
        } catch (Exception e) {
            JsonResponse response = new JsonResponse(false);
            response.putMessage(e.getMessage());
            return response;
        }

        JsonResponse response = new JsonResponse(true);
        response.putMessage("Loaded " + siteDatas.size() + " sites");
        return response;
    }
}
