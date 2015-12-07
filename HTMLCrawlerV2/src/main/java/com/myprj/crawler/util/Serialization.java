package com.myprj.crawler.util;

import java.util.ArrayList;
import java.util.List;

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

    public static <T> List<T> deserialize(List<String> jsons, Class<T> clazz) {
        List<T> targets = new ArrayList<T>();
        try {
            for (String json : jsons) {
                targets.add(Serialization.deserialize(json, clazz));
            }
            return targets;
        } catch (Exception e) {
            return new ArrayList<T>();
        }
    }
}
