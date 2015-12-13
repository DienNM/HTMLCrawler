package com.myprj.crawler.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * @author DienNM (DEE)
 */

public class DataMapping {
    
    private List<FieldMapping> mappings;
    
    public DataMapping() {
        mappings = new ArrayList<FieldMapping>();
    }

    public List<FieldMapping> getMappings() {
        return mappings;
    }

    public void setMappings(List<FieldMapping> mappings) {
        this.mappings = mappings;
    }
    
}
