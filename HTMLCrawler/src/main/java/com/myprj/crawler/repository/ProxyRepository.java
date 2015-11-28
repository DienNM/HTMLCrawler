package com.myprj.crawler.repository;

import java.util.List;

import com.myprj.crawler.model.ProxyModel;

/**
 * @author DienNM (DEE)
 */

public interface ProxyRepository {
    
    List<ProxyModel> findByType(String type);
    
}
