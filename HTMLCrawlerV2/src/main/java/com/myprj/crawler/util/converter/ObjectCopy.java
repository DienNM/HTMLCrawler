package com.myprj.crawler.util.converter;

/**
 * @author DienNM (DEE)
 */

public interface ObjectCopy<S, D> {

    void copy(S src, D dest);
}