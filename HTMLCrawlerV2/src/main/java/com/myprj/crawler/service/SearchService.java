package com.myprj.crawler.service;

import java.util.Map;

import com.myprj.crawler.domain.PageResult;
import com.myprj.crawler.domain.SearchParam;

/**
 * @author DienNM (DEE)
 */

public interface SearchService {

    PageResult<Map<String, String>> search(SearchParam searchParam);

}
