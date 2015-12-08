package com.myprj.crawler.service.target.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myprj.crawler.domain.target.ProductAttributeData;
import com.myprj.crawler.model.target.ProductAttributeModel;
import com.myprj.crawler.repository.target.ProductAttributeRepository;
import com.myprj.crawler.service.target.ProductAttributeService;

/**
 * @author DienNM (DEE)
 */
@Service
public class ProductAttributeServiceImpl implements ProductAttributeService {

    @Autowired
    private ProductAttributeRepository productAttributeRepository;
    
    @Override
    public List<ProductAttributeData> getByProductId(long productId) {
        List<ProductAttributeModel> productAttributeModels = productAttributeRepository.findByProductId(productId);
        List<ProductAttributeData> productAttributeDatas = new ArrayList<ProductAttributeData>();
        ProductAttributeData.toDatas(productAttributeModels, productAttributeDatas);
        return productAttributeDatas;
    }

    @Override
    public ProductAttributeData getById(long id) {
        ProductAttributeModel productAttributeModel = productAttributeRepository.find(id);
        if(productAttributeModel == null) {
            return null;
        }
        ProductAttributeData productAttributeData = new ProductAttributeData();
        ProductAttributeData.toData(productAttributeModel, productAttributeData);
        
        return productAttributeData;
    }

    @Override
    public ProductAttributeData get(String name, long productId) {
        ProductAttributeModel productAttributeModel = productAttributeRepository.findByNameAndProductId(name, productId);
        if(productAttributeModel == null) {
            return null;
        }
        ProductAttributeData productAttributeData = new ProductAttributeData();
        ProductAttributeData.toData(productAttributeModel, productAttributeData);
        
        return productAttributeData;
    }

}
