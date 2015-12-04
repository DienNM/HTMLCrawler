package com.myprj.crawler.web.mapping;

import static com.myprj.crawler.web.mapping.DTOField.DTOFieldType.field;

import com.myprj.crawler.web.enumeration.TargetDTOLevel;

/**
 * @author DienNM (DEE)
 */

public class DTOField {
    
    public enum DTOFieldType {
        field,
        ref
    }
    
    private String fieldName;
    
    private TargetDTOLevel targetRefType;
    
    private DTOFieldType type = field;
    
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

    public TargetDTOLevel getTargetRefType() {
        return targetRefType;
    }

    public void setTargetRefType(TargetDTOLevel targetRefType) {
        this.targetRefType = targetRefType;
    }
    
}
