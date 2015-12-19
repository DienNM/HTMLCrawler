package com.myprj.crawler.web.controller.page;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.myprj.crawler.domain.PageResult;
import com.myprj.crawler.domain.Pageable;
import com.myprj.crawler.domain.SearchParam;
import com.myprj.crawler.web.facades.SearchFacade;

/**
 * @author DienNM (DEE)
 */
@Controller
public class GlobalSearchController {
    
    @Autowired
    private SearchFacade searchFacade;
    
    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public String search(@RequestParam(value = "q", required = false) String q,
            @RequestParam(value = "type", required = false) String type,
            @RequestParam(value = "currentPage", defaultValue = "0") int currentPage,
            @RequestParam(value = "pageSize", defaultValue = "50") int pageSize,
            Model model) {
        Pageable pageable = new Pageable(pageSize, currentPage);
        SearchParam searchParam = new SearchParam();
        searchParam.setPageable(pageable);
        searchParam.setKeyword(q);
        PageResult<Map<String, String>> results = searchFacade.search(searchParam);
        model.addAttribute("results", results.getContent());
        model.addAttribute("pageable", results.getPageable());
        model.addAttribute("q", q);
        return "search";
    }
}
