package com.myprj.crawler.web.dto;

import java.util.List;

import com.myprj.crawler.annotation.DataTransfer;
import com.myprj.crawler.domain.target.AttributeMappingData;
import com.myprj.crawler.model.target.AttributeMappingId;
import com.myprj.crawler.model.target.AttributeMappingModel;
import com.myprj.crawler.util.converter.DomainConverter;
import com.myprj.crawler.util.converter.EntityConverter;
import com.myprj.crawler.util.converter.ObjectConverter;

/**
 * @author DienNM (DEE)
 */

public class AttributeMappingDTO extends AuditDTO {

    private static final long serialVersionUID = 1L;

    @DataTransfer("categoryKey")
    private String categoryKey;

    @DataTransfer("itemKey")
    private String itemKey;

    @DataTransfer("siteKey")
    private String siteKey;

    @DataTransfer("attributeName")
    private String attributeName;

    @DataTransfer("valueMapping")
    private String valueMapping;

    @DataTransfer("mappingStrategy")
    private String mappingStrategy;

    public AttributeMappingDTO() {
    }

    public static void toDTOs(List<AttributeMappingData> sources, List<AttributeMappingDTO> dests) {
        for (AttributeMappingData source : sources) {
            AttributeMappingDTO dest = new AttributeMappingDTO();
            toDTO(source, dest);
            dests.add(dest);
        }
    }

    public static void toDTO(AttributeMappingData source, AttributeMappingDTO dest) {
        DomainConverter.convert(source, dest);
    }

    public static void toModels(List<AttributeMappingData> sources, List<AttributeMappingModel> dests) {
        for (AttributeMappingData source : sources) {
            AttributeMappingModel dest = new AttributeMappingModel();
            toModel(source, dest);
            dests.add(dest);
        }
    }

    public static void toModel(AttributeMappingData source, AttributeMappingModel dest) {
        EntityConverter.convert2Entity(source, dest, new ObjectConverter<AttributeMappingData, AttributeMappingModel>() {
            @Override
            public void convert(AttributeMappingData src, AttributeMappingModel dest) {
                AttributeMappingId id = new AttributeMappingId();
                id.setCategoryKey(src.getCategoryKey());
                id.setItemKey(src.getItemKey());
                id.setSiteKey(src.getSiteKey());
                id.setAttributeName(src.getAttributeName());

                dest.setId(id);
            }
        });
    }

    public String getCategoryKey() {
        return categoryKey;
    }

    public void setCategoryKey(String categoryKey) {
        this.categoryKey = categoryKey;
    }

    public String getItemKey() {
        return itemKey;
    }

    public void setItemKey(String itemKey) {
        this.itemKey = itemKey;
    }

    public String getSiteKey() {
        return siteKey;
    }

    public void setSiteKey(String siteKey) {
        this.siteKey = siteKey;
    }

    public String getAttributeName() {
        return attributeName;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

    public String getValueMapping() {
        return valueMapping;
    }

    public void setValueMapping(String valueMapping) {
        this.valueMapping = valueMapping;
    }

    public String getMappingStrategy() {
        return mappingStrategy;
    }

    public void setMappingStrategy(String mappingStrategy) {
        this.mappingStrategy = mappingStrategy;
    }

}