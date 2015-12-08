package com.myprj.crawler.service.target.impl;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.myprj.crawler.domain.target.ProductAttributeData;
import com.myprj.crawler.domain.target.ProductData;
import com.myprj.crawler.domain.target.ProductPriceData;
import com.myprj.crawler.model.target.ProductAttributeModel;
import com.myprj.crawler.model.target.ProductModel;
import com.myprj.crawler.model.target.ProductPriceModel;
import com.myprj.crawler.repository.target.ProductAttributeRepository;
import com.myprj.crawler.repository.target.ProductPriceRepository;
import com.myprj.crawler.repository.target.ProductRepository;
import com.myprj.crawler.service.target.ProductAttributeService;
import com.myprj.crawler.service.target.ProductPriceService;
import com.myprj.crawler.service.target.ProductService;

/**
 * @author DienNM (DEE)
 */
@Service
public class ProductServiceImpl implements ProductService {

    private Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductPriceRepository productPriceRepository;

    @Autowired
    private ProductAttributeRepository productAttributeRepository;

    @Autowired
    private ProductPriceService productPriceService;

    @Autowired
    private ProductAttributeService productAttributeService;

    @Override
    public ProductData getById(long id) {
        ProductModel productModel = productRepository.find(id);
        if (productModel == null) {
            return null;
        }
        ProductData productData = new ProductData();
        ProductData.toData(productModel, productData);
        
        populateAttributes(productData);
        
        return productData;
    }

    @Override
    public ProductData getByKey(String key, String site) {
        ProductModel productModel = productRepository.findByKeyAndSite(key, site);
        if (productModel == null) {
            return null;
        }
        ProductData productData = new ProductData();
        ProductData.toData(productModel, productData);
        
        populateAttributes(productData);
        
        return productData;
    }

    @Override
    public List<ProductData> getByCategory(String category, String site) {
        List<ProductModel> productModels = productRepository.findByCategoryAndSite(category, site);
        List<ProductData> productDatas = new ArrayList<ProductData>();
        ProductData.toDatas(productModels, productDatas);
        return productDatas;
    }

    @Override
    public List<ProductData> getByName(String name) {
        List<ProductModel> productModels = productRepository.findByName(name);
        List<ProductData> productDatas = new ArrayList<ProductData>();
        ProductData.toDatas(productModels, productDatas);
        return productDatas;
    }

    private List<String> validateAndNormalizeProduct(ProductData product) {
        List<String> errors = new ArrayList<String>();

        if (StringUtils.isEmpty(product.getKey())) {
            errors.add("Product Key is required");
        } else {
            product.setKey(product.getKey().trim());
        }

        if (StringUtils.isEmpty(product.getCategoryKey())) {
            errors.add("Product Category is required");
        } else {
            product.setCategoryKey(product.getCategoryKey().trim());
        }

        if (StringUtils.isEmpty(product.getSite())) {
            errors.add("Product Site is required");
        } else {
            product.setSite(product.getSite().trim());
        }
        return errors;
    }

    @Override
    @Transactional
    public ProductData save(ProductData product) {
        List<String> errors = validateAndNormalizeProduct(product);
        if (!errors.isEmpty()) {
            logger.error("Product cannot be saved. Errors: {}", errors);
            return null;
        }

        ProductModel productModel = new ProductModel();
        ProductData.toModel(product, productModel);
        productRepository.save(productModel);

        ProductData persisted = new ProductData();
        ProductData.toData(productModel, persisted);
        
        List<ProductAttributeData> productAttributeDatas = product.getProductAttributes();
        if(!productAttributeDatas.isEmpty()) {
            List<ProductAttributeModel> productAttributeModels = new ArrayList<ProductAttributeModel>();
            ProductAttributeData.toModels(productAttributeDatas, productAttributeModels);
            productAttributeRepository.save(productAttributeModels);
            
            List<ProductAttributeData> productAttributePersisteds = new ArrayList<ProductAttributeData>();
            ProductAttributeData.toDatas(productAttributeModels, productAttributePersisteds);
            persisted.setProductAttributes(productAttributePersisteds);
        }
        
        ProductPriceModel productPriceModel = new ProductPriceModel();
        ProductPriceData.toModel(product.getProductPriceData(), productPriceModel);
        productPriceRepository.save(productPriceModel);
        
        return persisted;
    }

    @Override
    @Transactional
    public void update(ProductData product) {
        ProductModel productModel = productRepository.find(product.getId());
        if(productModel == null) {
            throw new InvalidParameterException("Product " + product.getId() + " not found");
        }
        productModel = new ProductModel();
        ProductData.toModel(product, productModel);
        
        productAttributeRepository.deleteByProductId(product.getId());
        productRepository.save(productModel);
        
        List<ProductAttributeData> productAttributeDatas = product.getProductAttributes();
        if(!productAttributeDatas.isEmpty()) {
            List<ProductAttributeModel> productAttributeModels = new ArrayList<ProductAttributeModel>();
            ProductAttributeData.toModels(productAttributeDatas, productAttributeModels);
            productAttributeRepository.save(productAttributeModels);
        }
        
        ProductPriceModel productPriceModel = new ProductPriceModel();
        ProductPriceData.toModel(product.getProductPriceData(), productPriceModel);
        productPriceRepository.save(productPriceModel);
    }

    @Override
    public void populateAttributes(ProductData productData) {
        List<ProductAttributeData> productAttributeDatas = productAttributeService.getByProductId(productData.getId());
        productData.setProductAttributes(productAttributeDatas);
    }

}
