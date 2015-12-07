package com.myprj.crawler.web.facades;

import java.io.InputStream;
import java.util.List;

import com.myprj.crawler.domain.config.ItemData;

/**
 * @author DienNM (DEE)
 */

public interface ItemFacade {
    
    List<ItemData> loadItemsFromSource(InputStream inputStream);
    
}
