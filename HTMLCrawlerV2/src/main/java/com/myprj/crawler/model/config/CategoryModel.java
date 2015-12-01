package com.myprj.crawler.model.config;

import static com.myprj.crawler.enumeration.GlobalStatus.STAGE;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.myprj.crawler.enumeration.GlobalStatus;
import com.myprj.crawler.model.AuditModel;

/**
 * @author DienNM (DEE)
 */
@Entity
@Table(name = "category")
public class CategoryModel extends AuditModel {

    private static final long serialVersionUID = 1L;
    
    public static final String TABLE_NAME = "category";
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "name", length = 50)
    private String name;

    @Column(name = "description", length = 150)
    private String description;

    @Column(name = "status", length = 10)
    @Enumerated(EnumType.STRING)
    private GlobalStatus status = STAGE;

    @Column(name = "parent_ctg_id")
    private long parentCategoryId = -1;

    public CategoryModel() {
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getParentCategoryId() {
        return parentCategoryId;
    }

    public void setParentCategoryId(long parentCategoryId) {
        this.parentCategoryId = parentCategoryId;
    }

    public GlobalStatus getStatus() {
        return status;
    }

    public void setStatus(GlobalStatus status) {
        this.status = status;
    }

}
