package com.myprj.crawler.repository.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.myprj.crawler.domain.PageResult;
import com.myprj.crawler.domain.Pageable;
import com.myprj.crawler.repository.GenericDao;

/**
 * @author DienNM (DEE)
 */

public abstract class DefaultGenericDao<E, Id>  implements GenericDao<E, Id> {

    @PersistenceContext
    protected EntityManager entityManager;
    
    private Class<E> clazz;
    
    private String className;
    
    public DefaultGenericDao() {
        setClazz(getTargetClass());
        setClassName(clazz.getName());
    }
    
    protected abstract Class<E> getTargetClass();
    
    @Override
    public long count() {
        Query query = entityManager.createQuery("Select count(*) from " + getClassName());
        Object obj = query.getSingleResult();
        return Long.valueOf(obj.toString());
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public List<E> findAll() {
        Query query = entityManager.createQuery("from " + getClassName());
        List<E> entities = query.getResultList();
        return entities;
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public PageResult<E> findAll(Pageable pageable) {
        Query query = entityManager.createQuery("from " + getClassName());
        query.setFirstResult(pageable.getCurrentPage() *  pageable.getPageSize());
        query.setMaxResults(pageable.getPageSize());
        List<E> entities = query.getResultList();
        
        long totalRecords = count();
        long totalPages = totalRecords / pageable.getPageSize();
        if(totalRecords % pageable.getPageSize() != 0 ) {
            totalPages += 1;
        }
        
        Pageable resultPageable = new Pageable(pageable);
        resultPageable.setTotalPages(totalPages);
        resultPageable.setTotalRecords(totalRecords);
        
        PageResult<E> pageResult = new PageResult<>(resultPageable);
        pageResult.setContent(entities);
        
        return pageResult;
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public List<E> find(Pageable pageable) {
        Query query = entityManager.createQuery("from " + getClassName());
        query.setFirstResult(pageable.getCurrentPage() *  pageable.getPageSize());
        query.setMaxResults(pageable.getPageSize());
        return query.getResultList();
    }
    
    @Override
    public E find(Id id) {
        E entity = entityManager.find(clazz, id);
        return entity;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<E> find(List<Id> ids) {
        Query query = entityManager.createQuery("FROM " + getClassName() + " WHERE id in (:ids)");
        query.setParameter("ids", ids);
        return query.getResultList();
    }
    
    @Override
    public void save(E entity) {
        entityManager.persist(entity);
    }
    
    @Override
    public void save(List<E> entities) {
        for(E entity : entities) {
            entityManager.persist(entity);
        }
    }

    @Override
    public void update(E entity) {
        entityManager.merge(entity);
    }

    @Override
    public void delete(E entity) {
        entityManager.remove(entity);
    }
    
    @Override
    public void deleteById(Id id) {
        Query query = entityManager.createQuery("DELETE FROM " + getClassName() + " WHERE id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }
    
    @Override
    public void deleteByIds(List<Id> ids) {
        Query query = entityManager.createQuery("DELETE FROM " + getClassName() + " WHERE id in (:ids)");
        query.setParameter("ids", ids);
        query.executeUpdate();
    }
    
    @Override
    public void deleteAll() {
        Query query = entityManager.createQuery("DELETE FROM " + getClassName());
        query.executeUpdate();
    }

    public Class<E> getClazz() {
        return clazz;
    }

    public void setClazz(Class<E> clazz) {
        this.clazz = clazz;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

}
