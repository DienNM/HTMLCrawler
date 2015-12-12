package com.myprj.crawler.domain.target;

import java.util.List;

import com.myprj.crawler.annotation.EntityTransfer;
import com.myprj.crawler.model.AuditModel;
import com.myprj.crawler.model.target.ConsolidationAttributeId;
import com.myprj.crawler.model.target.ConsolidationAttributeModel;
import com.myprj.crawler.util.converter.EntityConverter;
import com.myprj.crawler.util.converter.ObjectConverter;

/**
 * @author DienNM (DEE)
 */

public class ConsolidationAttributeData extends AuditModel {

    private static final long serialVersionUID = 1L;

    @EntityTransfer("name")
    private String name;

    @EntityTransfer("consolidation_id")
    private String consolidationId;

    @EntityTransfer("value")
    private String value;

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
        EntityConverter.convert2Data(source, dest, new ObjectConverter<ConsolidationAttributeModel, ConsolidationAttributeData>() {

            @Override
            public void convert(ConsolidationAttributeModel src, ConsolidationAttributeData dest) {
                ConsolidationAttributeId id = src.getId();
                dest.setConsolidationId(id.getConsolidationId());
                dest.setName(id.getName());
            }
        });
    }

    public static void toModel(ConsolidationAttributeData source, ConsolidationAttributeModel dest) {
        EntityConverter.convert2Entity(source, dest, new ObjectConverter<ConsolidationAttributeData, ConsolidationAttributeModel>() {

            @Override
            public void convert(ConsolidationAttributeData src, ConsolidationAttributeModel dest) {
                ConsolidationAttributeId id = new ConsolidationAttributeId();
                id.setConsolidationId(src.getConsolidationId());
                id.setName(src.getName());
                dest.setId(id);
            }
        });
    }

    public String getConsolidationId() {
        return consolidationId;
    }

    public void setConsolidationId(String consolidationId) {
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