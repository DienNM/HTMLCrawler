package com.myprj.crawler.model.target;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import com.myprj.crawler.model.AuditModel;

/**
 * @author DienNM (DEE)
 */

@Entity
@Table(name = "consolidation_attribute")
public class ConsolidationAttributeModel extends AuditModel {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id")
    private ConsolidationAttributeId id;

    @Column(name = "value")
    @Lob
    private String value;

    public ConsolidationAttributeModel() {
    }

    public ConsolidationAttributeId getId() {
        return id;
    }

    public void setId(ConsolidationAttributeId id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
