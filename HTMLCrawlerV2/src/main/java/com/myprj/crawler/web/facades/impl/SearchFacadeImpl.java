package com.myprj.crawler.web.facades.impl;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myprj.crawler.domain.PageResult;
import com.myprj.crawler.domain.SearchParam;
import com.myprj.crawler.service.SearchService;
import com.myprj.crawler.web.facades.SearchFacade;

/**
 * @author DienNM (DEE)
 */
@Service
public class SearchFacadeImpl implements SearchFacade {

    @Autowired
    private SearchService searchService;

    @Resource(name="elasticsearchFields") 
    private Map<String, Set<String>> elasticsearchFields;

    @Resource(name="elasticsearchMainFields") 
    private Map<String, Set<String>> elasticsearchMainFields;
    
    @Override
    public PageResult<Map<String, String>> search(SearchParam searchParam) {
        if (!StringUtils.isEmpty(searchParam.getType())) {
            Set<String> fields = elasticsearchFields.get(searchParam.getType());
            searchParam.setFields(fields);
            searchParam.setMainFields(elasticsearchMainFields.get(searchParam.getType()));
        } else {
            Set<String> fields = new HashSet<String>();
            for (Set<String> fieldList : elasticsearchFields.values()) {
                fields.addAll(fieldList);
            }
            searchParam.setFields(fields);
            
            Set<String> mainFields = new HashSet<String>();
            for (Set<String> mainFieldList : elasticsearchMainFields.values()) {
                mainFields.addAll(mainFieldList);
            }
            searchParam.setMainFields(mainFields);
        }
        return searchService.search(searchParam);
    }

}
