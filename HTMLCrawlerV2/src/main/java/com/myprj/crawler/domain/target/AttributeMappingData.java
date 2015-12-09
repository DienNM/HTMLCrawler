package com.myprj.crawler.domain.target;

import java.util.List;

import javax.persistence.Column;

import com.myprj.crawler.annotation.EntityTransfer;
import com.myprj.crawler.domain.AuditData;
import com.myprj.crawler.model.target.AttributeMappingId;
import com.myprj.crawler.model.target.AttributeMappingModel;
import com.myprj.crawler.util.converter.EntityConverter;
import com.myprj.crawler.util.converter.ObjectConverter;

/**
 * @author DienNM (DEE)
 */

public class AttributeMappingData extends AuditData {

    private static final long serialVersionUID = 1L;

    @EntityTransfer("category_key")
    private String categoryKey;

    @EntityTransfer("item_key")
    private String itemKey;

    @EntityTransfer("site_key")
    private String siteKey;

    @EntityTransfer("site_name")
    private String attributeName;

    @EntityTransfer("value_mapping")
    private String valueMapping;

    @EntityTransfer("mapping_strategy")
    @Column(name = "mapping_strategy")
    private String mappingStrategy;

    public AttributeMappingData() {
    }

    public static void toDatas(List<AttributeMappingModel> sources, List<AttributeMappingData> dests) {
        for (AttributeMappingModel source : sources) {
            AttributeMappingData dest = new AttributeMappingData();
            toData(source, dest);
            dests.add(dest);
        }
    }

    public static void toData(AttributeMappingModel source, AttributeMappingData dest) {
        EntityConverter.convert2Data(source, dest, new ObjectConverter<AttributeMappingModel, AttributeMappingData>() {
            @Override
            public void convert(AttributeMappingModel src, AttributeMappingData dest) {
                AttributeMappingId id = src.getId();
                dest.setCategoryKey(id.getCategoryKey());
                dest.setItemKey(id.getItemKey());
                dest.setSiteKey(id.getSiteKey());
                dest.setAttributeName(id.getAttributeName());
            }
        });
    }

    public static void toModels(List<AttributeMappingData> sources, List<AttributeMappingModel> dests) {
        for (AttributeMappingData source : sources) {
            AttributeMappingModel dest = new AttributeMappingModel();
            toModel(source, dest);
            dests.add(dest);
        }
    }

    public static void toModel(AttributeMappingData source, AttributeMappingModel dest) {
        EntityConverter.convert2Data(source, dest, new ObjectConverter<AttributeMappingData, AttributeMappingModel>() {
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
