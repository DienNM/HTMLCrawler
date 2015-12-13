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
@Table(name = "consolidation")
public class ConsolidationModel extends AuditModel {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id")
    private ConsolidationId id;

    @Column(name = "md5_key")
    private String md5Key;

    @Column(name = "md5_attributes")
    private String md5Attributes;

    @Column(name = "url", length = 1000)
    private String url;
    

    public ConsolidationModel() {
    }
    
    public ConsolidationId getId() {
        return id;
    }
    
    public void setId(ConsolidationId id) {
        this.id = id;
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
