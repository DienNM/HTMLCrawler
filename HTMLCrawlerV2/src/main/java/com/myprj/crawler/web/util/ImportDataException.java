package com.myprj.crawler.web.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

/**
 * @author DienNM (DEE)
 */

public class ImportDataException extends Exception {

    private static final long serialVersionUID = 1L;
    
    private List<String> errors = new ArrayList<String>();
    
    public ImportDataException() {
    }
    
    public ImportDataException(String error) {
        super(error);
    }
    
    public ImportDataException(List<String> errors) {
        super(StringUtils.join(errors, ", "));
        errors.addAll(errors);
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

}
