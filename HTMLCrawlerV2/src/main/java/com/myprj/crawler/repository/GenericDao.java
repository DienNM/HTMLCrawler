package com.myprj.crawler.repository;

import java.util.List;

import com.myprj.crawler.domain.PageResult;
import com.myprj.crawler.domain.Pageable;

/**
 * @author DienNM (DEE)
 */

public interface GenericDao<E, Id> {
    
    List<E> findAll();
    
    PageResult<E> findAll(Pageable pageable);
    
    long count();
    
    E find(Id id);
    
    List<E> find(List<Id> ids);
    
    void save(E entity);
    
    void save(List<E> entities);
    
    void update(E entity);
    
    void delete(E entity);
    
    void deleteById(Id id);
    
    void deleteByIds(List<Id> ids);
    
    void deleteAll();
    
}
