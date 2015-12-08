package com.myprj.crawler.service.target;

import com.myprj.crawler.domain.PageResult;
import com.myprj.crawler.domain.Pageable;
import com.myprj.crawler.domain.target.TabletProductData;

/**
 * @author DienNM (DEE)
 */

public interface TabletProductService {

    TabletProductData getById(long id);

    PageResult<TabletProductData> getPaging(Pageable pageable);

    TabletProductData save(TabletProductData tabletProduct);

}
