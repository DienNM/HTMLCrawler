package com.myprj.crawler.web.mapping;

import java.util.Map;

/**
 * @author DienNM (DEE)
 */

public interface DTOConverter<S> {
    
    void convert(S src, Map<String, Object> dest);
    
}
