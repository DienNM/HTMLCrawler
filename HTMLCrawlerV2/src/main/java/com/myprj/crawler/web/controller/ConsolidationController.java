package com.myprj.crawler.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.myprj.crawler.domain.target.ConsolidationData;
import com.myprj.crawler.service.target.ConsolidationService;
import com.myprj.crawler.web.dto.JsonResponse;
import com.myprj.crawler.web.enumeration.DTOLevel;
import com.myprj.crawler.web.facades.MigrationFacade;

/**
 * @author DienNM (DEE)
 */

@Controller
@RequestMapping(value = "/consolidations", produces = "application/json")
public class ConsolidationController extends AbstractController {

    @Autowired
    private ConsolidationService consolidationService;

    @Autowired
    private MigrationFacade migrationFacade;

    @RequestMapping(value = "/{md5Key}", method = RequestMethod.GET)
    @ResponseBody
    public JsonResponse getConsolidationByMd5Key(@PathVariable(value = "md5Key") String md5Key,
            @RequestParam(value = "level", defaultValue = "DEFAULT") DTOLevel target) {

        ConsolidationData consolidation = consolidationService.getByMd5Key(md5Key);
        if (consolidation == null) {
            JsonResponse jsonResponse = new JsonResponse(false);
            jsonResponse.putMessage("Consolidation key " + md5Key + " not found");
            return jsonResponse;
        }

        JsonResponse response = new JsonResponse(true);
        response.putData(consolidation);

        return response;
    }

    @RequestMapping(value = "/migration/full", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse doMigration() {
        try {
            migrationFacade.doFullMapping();
            JsonResponse jsonResponse = new JsonResponse(true);
            return jsonResponse;
        } catch (Exception e) {
            JsonResponse jsonResponse = new JsonResponse(false);
            jsonResponse.putMessage(e.getMessage());
            return jsonResponse;
        }
    }
}
