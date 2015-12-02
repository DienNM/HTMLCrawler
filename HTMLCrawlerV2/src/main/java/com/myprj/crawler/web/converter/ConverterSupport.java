package com.myprj.crawler.web.converter;

import java.util.List;

/**
 * @author DienNM (DEE)
 */

public abstract class ConverterSupport<S, D> implements Converter<S, D> {
    
    @Override
    public void convertDests2Sources(List<S> sources, List<D> dests) {
        
    }
    
    @Override
    public void convertSources2Dests(List<S> sources, List<D> dests) {
        
    }
    
}
