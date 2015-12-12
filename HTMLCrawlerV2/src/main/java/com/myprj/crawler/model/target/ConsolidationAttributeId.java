package com.myprj.crawler.model.target;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * @author DienNM (DEE)
 */

@Embeddable
public class ConsolidationAttributeId implements Serializable{

    private static final long serialVersionUID = 1L;
    
    @Column(name = "name", length = 50)
    private String name;

    @Column(name = "consolidation_id")
    private String consolidationId;
    
    public String getConsolidationId() {
        return consolidationId;
    }
    
    public ConsolidationAttributeId() {
    }
    
    public ConsolidationAttributeId(String consolitaionId, String name) {
        this.consolidationId = consolitaionId;
        this.name = name;
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
}
