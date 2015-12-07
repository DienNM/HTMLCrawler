package com.myprj.crawler.web.facades;

import java.io.InputStream;
import java.util.List;

import com.myprj.crawler.domain.config.CategoryData;

/**
 * @author DienNM (DEE)
 */

public interface CategoryFacade {

    List<CategoryData> loadCategoriesFromSource(InputStream inputStream);

}
