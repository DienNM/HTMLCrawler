package com.myprj.crawler.model.target;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    @Column(name = "name", length = 50)
    private String name;

    @Column(name = "value", length = 2000)
    private String value;

    @Column(name = "consolidation_id")
    private long consolidationId;

    public ConsolidationAttributeModel() {
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
