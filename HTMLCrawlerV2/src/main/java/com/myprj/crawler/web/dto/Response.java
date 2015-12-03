package com.myprj.crawler.web.dto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author DienNM (DEE)
 */

public class Response extends HashMap<String, Object> {

    private static final long serialVersionUID = 1L;

    public static final String DATA = "data";
    public static final String SUCCESS = "success";
    public static final String MESSAGE = "message";

    public Response() {
        this(false);
    }

    public Response(boolean success) {
        put(SUCCESS, success);
    }

    public Response(Object object, boolean success) {
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
