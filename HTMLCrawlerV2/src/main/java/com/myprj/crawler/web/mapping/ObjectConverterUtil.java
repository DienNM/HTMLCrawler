package com.myprj.crawler.web.mapping;

import java.util.List;

import com.myprj.crawler.util.converter.DomainConverter;

/**
 * @author DienNM (DEE)
 */

public final class ObjectConverterUtil {
    
    public static <S, D> void convert(List<S> sources, List<D> dests, ObjectCreation<D> objCreation) {
        for(S source : sources) {
            D dest = objCreation.create();
            convert(source, dest);
            dests.add(dest);
        }
    }
    
    public static <S, D> void convert(S source, D dest) {
        DomainConverter.convert(source, dest);
    }
    
}
