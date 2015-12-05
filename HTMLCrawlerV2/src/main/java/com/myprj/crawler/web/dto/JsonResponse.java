package com.myprj.crawler.web.dto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

/**
 * @author DienNM (DEE)
 */

public class JsonResponse extends HashMap<String, Object> {

    private static final long serialVersionUID = 1L;

    public static final String DATA = "data";
    public static final String PAGING = "paging";
    public static final String SUCCESS = "success";
    public static final String MESSAGE = "message";
    public static final String ERRORS = "errors";

    public JsonResponse() {
        this(false);
    }

    public JsonResponse(boolean success) {
        put(SUCCESS, success);
    }

    public JsonResponse(Object object) {
        putDataAsCollection(object);
        put(SUCCESS, object != null);
    }

    public JsonResponse(Object object, boolean success) {
        put(SUCCESS, success);
        put(DATA, object);
    }
    
    public void putPaging(Object pageble) {
        put(PAGING, pageble);
    }

    public void putMessage(String message) {
        put(MESSAGE, message);
    }
    
    public void putData(Object data) {
        put(DATA, data);
    }

    public void putDataAsCollection(Object data) {
        if (data == null) {
            put(DATA, new ArrayList<String>());
        } else if (data instanceof Collection) {
            put(DATA, data);
        } else {
            List<Object> list = new ArrayList<Object>();
            list.add(data);
            put(DATA, list);
        }
    }
    
    public void putErrors(List<?> errors) {
        put(ERRORS, errors);
    }

}
