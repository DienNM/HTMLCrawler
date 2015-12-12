package com.myprj.crawler.model.target;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.myprj.crawler.model.AuditModel;

/**
 * @author DienNM (DEE)
 */
@Entity
@Table(name = "attribute_mapping")
public class AttributeMappingModel extends AuditModel {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id")
    private AttributeMappingId id;
    
    @Column(name = "value_mapping")
    private String valueMapping;

    @Column(name = "mapping_strategy")
    private String mappingStrategy;

    public AttributeMappingModel() {
    }

    public AttributeMappingId getId() {
        return id;
    }

    public void setId(AttributeMappingId id) {
        this.id = id;
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
