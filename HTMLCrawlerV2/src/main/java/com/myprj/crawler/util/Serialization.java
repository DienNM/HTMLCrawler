package com.myprj.crawler.util;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.type.TypeFactory;

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

    public static <T> List<T> desetialize2List(String json, Class<T> clazz) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(json, TypeFactory.defaultInstance().constructCollectionType(List.class, clazz));
        } catch (Exception e) {
            return new ArrayList<T>();
        }
    }

}
