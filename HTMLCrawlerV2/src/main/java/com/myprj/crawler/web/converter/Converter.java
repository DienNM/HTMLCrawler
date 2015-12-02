package com.myprj.crawler.web.converter;

import java.util.List;

/**
 * @author DienNM (DEE)
 */

public interface Converter<S, D> {

    void convertSource2Dest(S source, D dest);
    
    void convertSources2Dests(List<S> sources, List<D> dests);
    
    void convertDest2Source(S source, D dest);
    
    void convertDests2Sources(List<S> sources, List<D> dests);
    
}
