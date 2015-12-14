package com.myprj.crawler.util;

import com.google.gson.Gson;

/**
 * @author DienNM
 **/

public class Serialization {

    public static <T> String serialize(T obj) {
        return new Gson().toJson(obj);
    }

    public static <T> T deserialize(String json, Class<T> klass) {
        return new Gson().fromJson(json, klass);
    }
}
