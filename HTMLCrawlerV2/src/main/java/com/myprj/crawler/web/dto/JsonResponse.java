package com.myprj.crawler.web.dto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import com.myprj.crawler.util.converter.TypeConverter;

/**
 * @author DienNM (DEE)
 */

public class JsonResponse extends HashMap<String, Object> {

    private static final long serialVersionUID = 1L;

    public static final String DATA = "data";
    public static final String SUCCESS = "success";
    public static final String MESSAGE = "message";

    public JsonResponse() {
        this(false);
    }

    public JsonResponse(boolean success) {
        put(SUCCESS, success);
    }

    @SuppressWarnings("rawtypes")
    public void putSuccess(Object object) {
        if (object instanceof Boolean) {
            put(SUCCESS, TypeConverter.convertObject2Boolean(object));
        } else if (object == null) {
            put(SUCCESS, false);
        } else if (object instanceof Collection) {
            put(SUCCESS, ((Collection) object).iterator().hasNext());
        } else {
            put(SUCCESS, true);
        }
    }

    public JsonResponse(Object object) {
        put(DATA, object);
        putSuccess(object);
    }

    public JsonResponse(Object object, boolean success) {
        put(SUCCESS, success);
        put(DATA, object);
    }

    public void putMessage(String message) {
        put(MESSAGE, message);
    }

    public void putData(Object data) {
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

}
