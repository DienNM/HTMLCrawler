package com.myprj.crawler.service.target.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myprj.crawler.domain.target.ProductPriceData;
import com.myprj.crawler.model.target.ProductPriceModel;
import com.myprj.crawler.repository.target.ProductPriceRepository;
import com.myprj.crawler.service.target.ProductPriceService;

/**
 * @author DienNM (DEE)
 */
@Service
public class ProductPriceServiceImpl implements ProductPriceService {

    @Autowired
    private ProductPriceRepository productPriceRepository;

    @Override
    public List<ProductPriceData> getByProductId(long productId) {
        List<ProductPriceModel> productPriceModels = productPriceRepository.findByProductId(productId);
        List<ProductPriceData> productPriceDatas = new ArrayList<ProductPriceData>();
        ProductPriceData.toDatas(productPriceModels, productPriceDatas);
        return productPriceDatas;
    }

    @Override
    public ProductPriceData getLatestByProductId(long productId) {
        ProductPriceModel productPriceModel = productPriceRepository.findLatestByProductId(productId);
        ProductPriceData productPriceData = new ProductPriceData();
        ProductPriceData.toData(productPriceModel, productPriceData);
        return productPriceData;
    }

}
