package com.myprj.crawler.domain.target;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.myprj.crawler.model.AuditModel;
import com.myprj.crawler.model.target.ConsolidationModel;
import com.myprj.crawler.util.converter.EntityConverter;

/**
 * @author DienNM (DEE)
 */

public class ConsolidationData extends AuditModel {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    @Column(name = "result_key", length = 200)
    private String resultKey;

    @Column(name = "name", length = 200)
    private String name;

    @Column(name = "category_key", length = 50)
    private String categoryKey;

    @Column(name = "item_key", length = 50)
    private String itemKey;

    @Column(name = "site", length = 50)
    private String site;

    private List<ConsolidationAttributeData> attributes = new ArrayList<ConsolidationAttributeData>();

    public ConsolidationData() {
        
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
        EntityConverter.convert2Data(source, dest);
    }

    public static void toModel(ConsolidationData source, ConsolidationModel dest) {
        EntityConverter.convert2Entity(source, dest);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
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
}
