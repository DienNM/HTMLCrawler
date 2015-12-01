package com.myprj.crawler.repository;

import java.util.List;

/**
 * @author DienNM (DEE)
 */

public interface GenericDao<E, Id> {
    
    List<E> findAll();
    
    E find(Id id);
    
    void save(E entity);
    
    void update(E entity);
    
    void delete(E entity);
    
    void deleteById(Id id);
    
}
