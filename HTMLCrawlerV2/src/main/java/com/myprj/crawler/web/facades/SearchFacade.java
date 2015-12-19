package com.myprj.crawler.web.facades;

import java.util.Map;

import com.myprj.crawler.domain.PageResult;
import com.myprj.crawler.domain.SearchParam;

/**
 * @author DienNM (DEE)
 */

public interface SearchFacade {

    PageResult<Map<String, String>> search(SearchParam searchParam);

}
