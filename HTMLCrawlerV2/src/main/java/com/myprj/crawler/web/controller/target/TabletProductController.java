package com.myprj.crawler.web.controller.target;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.myprj.crawler.domain.PageResult;
import com.myprj.crawler.domain.Pageable;
import com.myprj.crawler.domain.target.TabletProductData;
import com.myprj.crawler.service.target.TabletProductService;
import com.myprj.crawler.web.controller.AbstractController;
import com.myprj.crawler.web.dto.JsonResponse;
import com.myprj.crawler.web.enumeration.DTOLevel;

/**
 * @author DienNM (DEE)
 */

@Controller
@RequestMapping(value = "/targets/tablets", produces = "application/json")
public class TabletProductController extends AbstractController {
    
    @Autowired
    private TabletProductService tabletProductService;
    
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public JsonResponse getPaging(@RequestParam(value = "currentPage", defaultValue = "0") int currentPage,
            @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
            @RequestParam(value = "level", defaultValue = "SIMPLE") DTOLevel level) {

        Pageable pageable = new Pageable(pageSize, currentPage);
        PageResult<TabletProductData> pageResult = tabletProductService.getPaging(pageable);
        List<TabletProductData> sources = pageResult.getContent();
        JsonResponse jsonResponse = new JsonResponse(pageResult, !sources.isEmpty());
        jsonResponse.putPaging(pageResult.getPageable());

        return jsonResponse;
    }

    @RequestMapping(value = "/{categoryId}", method = RequestMethod.GET)
    @ResponseBody
    public JsonResponse getById(@PathVariable(value = "categoryId") long id,
            @RequestParam(value = "level", defaultValue = "DEFAULT") DTOLevel target) {

        TabletProductData tabletProduct = tabletProductService.getById(id);
        if (tabletProduct == null) {
            JsonResponse jsonResponse = new JsonResponse(false);
            jsonResponse.putMessage("Tablet Id " + id + " not found");
            return jsonResponse;
        }

        JsonResponse response = new JsonResponse(true);
        response.putData(tabletProduct);

        return response;
    }
}
