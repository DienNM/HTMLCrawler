package com.myprj.crawler.domain.target;

import java.util.ArrayList;
import java.util.List;

import com.myprj.crawler.annotation.Consolidation;
import com.myprj.crawler.annotation.EntityTransfer;
import com.myprj.crawler.model.AuditModel;
import com.myprj.crawler.model.target.ConsolidationId;
import com.myprj.crawler.model.target.ConsolidationModel;
import com.myprj.crawler.util.Md5;
import com.myprj.crawler.util.converter.EntityConverter;
import com.myprj.crawler.util.converter.ObjectConverter;

/**
 * @author DienNM (DEE)
 */

public class ConsolidationData extends AuditModel {

    private static final long serialVersionUID = 1L;

    private String resultKey;

    private String categoryKey;

    private String itemKey;

    private String siteKey;

    @EntityTransfer("name")
    @Consolidation("name")
    private String name;

    @EntityTransfer("url")
    private String url;

    @EntityTransfer("md5_key")
    private String md5Key;

    @EntityTransfer("md5_attributes")
    private String md5Attributes;

    private List<ConsolidationAttributeData> attributes = new ArrayList<ConsolidationAttributeData>();

    public ConsolidationData() {
    }
    
    public static void createMd5Key(ConsolidationData consolidation) {
        String key = consolidation.getSiteKey() + "-" + consolidation.getCategoryKey() + consolidation.getItemKey()
                + consolidation.getResultKey();
        consolidation.setMd5Key(Md5.hex(key));
    }
    
    public static void createMd5Attribute(ConsolidationData consolidation, Object attributeObj) {
        consolidation.setMd5Attributes(Md5.hex(attributeObj.toString()));
    }

    public static void toDatas(List<ConsolidationModel> sources, List<ConsolidationData> dests) {
        for (ConsolidationModel source : sources) {
            ConsolidationData dest = new ConsolidationData();
            toData(source, dest);
            dests.add(dest);
        }
    }

    public static void toModels(List<ConsolidationData> sources, List<ConsolidationModel> dests) {
        for (ConsolidationData source : sources) {
            ConsolidationModel dest = new ConsolidationModel();
            toModel(source, dest);
            dests.add(dest);
        }
    }

    public static void toData(ConsolidationModel source, ConsolidationData dest) {
        EntityConverter.convert2Data(source, dest, new ObjectConverter<ConsolidationModel, ConsolidationData>() {

            @Override
            public void convert(ConsolidationModel src, ConsolidationData dest) {
                ConsolidationId id = src.getId();
                dest.setCategoryKey(id.getCategoryKey());
                dest.setItemKey(id.getItemKey());
                dest.setSiteKey(id.getSiteKey());
                dest.setResultKey(id.getResultKey());
            }
        });
    }

    public static void toModel(ConsolidationData source, ConsolidationModel dest) {
        EntityConverter.convert2Entity(source, dest, new ObjectConverter<ConsolidationData, ConsolidationModel>() {

            @Override
            public void convert(ConsolidationData src, ConsolidationModel dest) {
                ConsolidationId id = new ConsolidationId();
                id.setCategoryKey(src.getCategoryKey());
                id.setItemKey(src.getItemKey());
                id.setSiteKey(src.getSiteKey());
                id.setResultKey(src.getResultKey());
                dest.setId(id);
            }
        });
    }

    @Override
    public String toString() {
        return String.format("Site=%s, Category=%s, Item=%s, ResultKey=%s", siteKey, categoryKey, itemKey, resultKey);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getResultKey() {
        return resultKey;
    }

    public void setResultKey(String resultKey) {
        this.resultKey = resultKey;
    }

    public List<ConsolidationAttributeData> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<ConsolidationAttributeData> attributes) {
        this.attributes = attributes;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMd5Key() {
        return md5Key;
    }

    public void setMd5Key(String md5Key) {
        this.md5Key = md5Key;
    }

    public String getMd5Attributes() {
        return md5Attributes;
    }

    public void setMd5Attributes(String md5Attributes) {
        this.md5Attributes = md5Attributes;
    }
}
