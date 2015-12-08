package com.myprj.crawler.service.target.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.myprj.crawler.domain.PageResult;
import com.myprj.crawler.domain.Pageable;
import com.myprj.crawler.domain.target.TabletProductData;
import com.myprj.crawler.model.target.TabletProductModel;
import com.myprj.crawler.repository.TabletProductRepository;
import com.myprj.crawler.service.target.TabletProductService;

/**
 * @author DienNM (DEE)
 */
@Service
public class TabletProductServiceImpl implements TabletProductService {
    
    @Autowired
    private TabletProductRepository tabletProductRepository;
    
    @Override
    public TabletProductData getById(long id) {
        TabletProductModel tabletProductModel = tabletProductRepository.find(id);
        if(tabletProductModel == null) {
            return null;
        }
        TabletProductData tabletProductData = new TabletProductData();
        TabletProductData.toData(tabletProductModel, tabletProductData);
        
        return tabletProductData;
    }

    @Override
    public PageResult<TabletProductData> getPaging(Pageable pageable) {
        PageResult<TabletProductModel> pageResult = tabletProductRepository.findAll(pageable);

        PageResult<TabletProductData> results = new PageResult<TabletProductData>(pageResult.getPageable());
        List<TabletProductData> tabletProducts = new ArrayList<TabletProductData>();
        TabletProductData.toDatas(pageResult.getContent(), tabletProducts);
        results.setContent(tabletProducts);

        return results;
    }

    @Override
    @Transactional
    public TabletProductData save(TabletProductData tabletProduct) {
        TabletProductModel tabletProductModel = new TabletProductModel();
        TabletProductData.toModel(tabletProduct, tabletProductModel);
        
        tabletProductRepository.save(tabletProductModel);
        
        TabletProductData persisted = new TabletProductData();
        TabletProductData.toData(tabletProductModel, persisted);
        
        return persisted;
    }

}
