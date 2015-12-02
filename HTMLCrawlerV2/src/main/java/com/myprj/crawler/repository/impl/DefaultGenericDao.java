package com.myprj.crawler.repository.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

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
    
    @SuppressWarnings("unchecked")
    @Override
    public List<E> findAll() {
        Query query = entityManager.createQuery("from " + getClassName());
        List<E> students = query.getResultList();
        return students;
    }

    @Override
    public E find(Id id) {
        E entity = entityManager.find(clazz, id);
        return entity;
    }

    @Override
    public void save(E entity) {
        entityManager.persist(entity);
        
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
