package com.myprj.crawler.util.converter;

/**
 * @author DienNM (DEE)
 */

public interface ObjectConverter<S, D> {

    void convert(S src, D dest);
}