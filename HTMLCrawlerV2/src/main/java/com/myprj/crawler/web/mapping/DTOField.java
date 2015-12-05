package com.myprj.crawler.web.mapping;

import static com.myprj.crawler.web.mapping.DTOField.DTOFieldType.field;

import com.myprj.crawler.web.enumeration.DTOLevel;

/**
 * @author DienNM (DEE)
 */

public class DTOField {
    
    public enum DTOFieldType {
        field,
        ref,
        list
    }
    
    private String fieldName;
    
    private DTOLevel targetRefType;
    
    private DTOFieldType type = field;
    
    private DTOFieldType subType;
    
    public DTOField() {
    }
    
    public DTOField(String fielName) {
        this.fieldName = fielName;
    }
    
    public DTOField(String fielName, DTOFieldType type) {
        this.type = type;
        this.fieldName = fielName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public DTOFieldType getType() {
        return type;
    }

    public void setType(DTOFieldType type) {
        this.type = type;
    }

    public DTOLevel getTargetRefType() {
        return targetRefType;
    }

    public void setTargetRefType(DTOLevel targetRefType) {
        this.targetRefType = targetRefType;
    }

    public DTOFieldType getSubType() {
        return subType;
    }

    public void setSubType(DTOFieldType subType) {
        this.subType = subType;
    }
    
}
