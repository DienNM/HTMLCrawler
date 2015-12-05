package com.myprj.crawler.web.mapping;

import java.util.Map;

/**
 * @author DienNM (DEE)
 */

public interface DTOMapper<S> {
    
    void map(S src, Map<String, Object> dest);
    
}
