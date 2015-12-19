package com.myprj.crawler.domain;

import java.util.HashSet;
import java.util.Set;

/**
 * @author DienNM (DEE)
 */

public class SearchParam {
    
    private String keyword;
    
    private String type;
    
    private Set<String> fields = new HashSet<String>();
    
    private Set<String> mainFields = new HashSet<String>();
    
    private Pageable pageable = new Pageable(20, 0);
    
    public SearchParam() {
    }
    
    public String[] toArrayFields() {
        String[] aFields = new String[fields.size()];
        int index = 0;
        for(String field : fields) {
            aFields[index++] = field;
        }
        return aFields;
    }
    
    public SearchParam(String keyword) {
        this.keyword = keyword;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Set<String> getFields() {
        return fields;
    }

    public void setFields(Set<String> fields) {
        this.fields = fields;
    }

    public Pageable getPageable() {
        return pageable;
    }

    public void setPageable(Pageable pageable) {
        this.pageable = pageable;
    }

    public Set<String> getMainFields() {
        return mainFields;
    }

    public void setMainFields(Set<String> mainFields) {
        this.mainFields = mainFields;
    }

    
}
