package com.myprj.crawler.model.config;

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
@Table(name = "item")
public class ItemModel extends AuditModel {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "category_id", nullable = false)
    private long categoryId;

    @Column(name = "name", length = 50, nullable = false)
    private String name;

    @Column(name = "description", length = 150)
    private String description;

    @Column(name = "status", length = 10)
    @Enumerated(EnumType.STRING)
    private GlobalStatus status = GlobalStatus.ONLINE;

    public ItemModel() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
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

    public GlobalStatus getStatus() {
        return status;
    }

    public void setStatus(GlobalStatus status) {
        this.status = status;
    }

}