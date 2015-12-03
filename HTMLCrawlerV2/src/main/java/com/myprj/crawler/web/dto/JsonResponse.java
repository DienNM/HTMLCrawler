package com.myprj.crawler.web.dto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
        } else if (data instanceof List) {
            put(DATA, data);
        } else {
            List<Object> list = new ArrayList<Object>();
            list.add(data);
            put(DATA, list);
        }

    }

}
