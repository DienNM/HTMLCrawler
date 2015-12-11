package com.myprj.crawler.domain.target;

import java.util.List;

import com.myprj.crawler.annotation.EntityTransfer;
import com.myprj.crawler.model.AuditModel;
import com.myprj.crawler.model.target.ConsolidationAttributeModel;
import com.myprj.crawler.util.converter.EntityConverter;

/**
 * @author DienNM (DEE)
 */

public class ConsolidationAttributeData extends AuditModel {

    private static final long serialVersionUID = 1L;

    @EntityTransfer("id")
    private long id;

    @EntityTransfer("name")
    private String name;

    @EntityTransfer("value")
    private String value;

    @EntityTransfer("consolidation_id")
    private long consolidationId;

    public ConsolidationAttributeData() {
    }

    public static void toDatas(List<ConsolidationAttributeModel> sources, List<ConsolidationAttributeData> dests) {
        for (ConsolidationAttributeModel source : sources) {
            ConsolidationAttributeData dest = new ConsolidationAttributeData();
            toData(source, dest);
            dests.add(dest);
        }
    }

    public static void toModels(List<ConsolidationAttributeData> sources, List<ConsolidationAttributeModel> dests) {
        for (ConsolidationAttributeData source : sources) {
            ConsolidationAttributeModel dest = new ConsolidationAttributeModel();
            toModel(source, dest);
            dests.add(dest);
        }
    }

    public static void toData(ConsolidationAttributeModel source, ConsolidationAttributeData dest) {
        EntityConverter.convert2Data(source, dest);
    }

    public static void toModel(ConsolidationAttributeData source, ConsolidationAttributeModel dest) {
        EntityConverter.convert2Entity(source, dest);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getConsolidationId() {
        return consolidationId;
    }

    public void setConsolidationId(long consolidationId) {
        this.consolidationId = consolidationId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}