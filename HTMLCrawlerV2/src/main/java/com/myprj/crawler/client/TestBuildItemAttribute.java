package com.myprj.crawler.client;

import com.myprj.crawler.domain.config.AttributeData;
import com.myprj.crawler.domain.config.ItemAttributeData;
import com.myprj.crawler.domain.config.ItemData;
import com.myprj.crawler.service.AttributeService;
import com.myprj.crawler.service.impl.InMemoryAttributeServiceImpl;
import com.myprj.crawler.service.impl.ItemAttributeStructureServiceImpl;
import com.myprj.crawler.util.ItemStructureUtil;

/**
 * @author DienNM (DEE)
 */

public class TestBuildItemAttribute {
    private ItemAttributeStructureServiceImpl attributeStructureService;
    private TestAttributeBuilder2 attributeBuilder;
    private ItemAttributeData root = null;
    
    public TestBuildItemAttribute() {
        attributeBuilder = new TestAttributeBuilder2();
        attributeBuilder.buildAttributeStructure();
        attributeBuilder.populateData();
    }
    
    public static void saveAttributes(AttributeService attributeService, AttributeData attribute) {
        attributeService.save(attribute);
        for(AttributeData attributeData : attribute.getChildren()) {
            saveAttributes(attributeService, attributeData);
        }
    }
    
    public void buildItemAttribute() {
        String text = "{"
                + "\"name\":\"I@div.product-info-head div.product-info-name\","
                + "\"price\" : \"I@div.product-prices span.product-price||E@div.product-prices||E@div.span.product-price\","
                + "\"pastPrice\": \"I@div.product-prices span.product-price-past\","
                + "\"includedInBox\": [\"I@div.catWrapper.whatisinbox div.description-detail ul li span\"],"
                + "\"specifications\": ["
                    + "\"I@div.catWrapper.specifications.specifications-detail div.description-detail ul li span\""
                  + "],"
                + "\"descriptionDetails\" : ["
                    + "{"
                        + "\"key\": \"I@div.catWrapper div.description-detail ul li\","
                        + "\"value\": \"I@div.catWrapper div.description-detail ul li span\""
                    + "}]}";
        
        root = attributeStructureService.buildAttributes(this.getAttributeBuilder().getItemData(), text);
    }

    public static void main(String[] args) {
        ItemAttributeStructureServiceImpl attributeStructureService = new ItemAttributeStructureServiceImpl();
        AttributeService attributeService = new InMemoryAttributeServiceImpl();
        attributeStructureService.setAttributeService(attributeService);
        
        TestBuildItemAttribute itemAttributeBuilder = new TestBuildItemAttribute();
        itemAttributeBuilder.setAttributeStructureService(attributeStructureService);
        
        saveAttributes(attributeService, itemAttributeBuilder.getAttributeBuilder().getRoot());
        
        itemAttributeBuilder.buildItemAttribute();
        
        ItemStructureUtil.print(itemAttributeBuilder.getRoot());
    }
    
    public TestAttributeBuilder2 getAttributeBuilder() {
        return attributeBuilder;
    }
    
    public void setAttributeStructureService(ItemAttributeStructureServiceImpl attributeStructureService) {
        this.attributeStructureService = attributeStructureService;
    }

    public ItemAttributeData getRoot() {
        return root;
    }

    public void setRoot(ItemAttributeData root) {
        this.root = root;
    }
}
