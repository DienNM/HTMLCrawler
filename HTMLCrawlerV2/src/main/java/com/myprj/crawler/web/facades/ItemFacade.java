package com.myprj.crawler.web.facades;

import java.io.InputStream;
import java.util.List;

/**
 * @author DienNM (DEE)
 */

public interface ItemFacade {
    
    List<String> loadItemsFromSource(InputStream inputStream, boolean forceBuild);
    
    List<String> buildItemStructure(InputStream inputStream, boolean forceBuild);
    
}
