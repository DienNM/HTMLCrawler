package com.myprj.crawler.util;

import static com.myprj.crawler.util.converter.TypeConverter.convertString2Boolean;
import static com.myprj.crawler.util.converter.TypeConverter.convertString2Date;
import static com.myprj.crawler.util.converter.TypeConverter.convertString2Int;
import static com.myprj.crawler.util.converter.TypeConverter.convertString2Long;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author DienNM (DEE)
 */

public final class ReflectionUtil {
    
    private final static Logger LOGGER = LoggerFactory.getLogger(ReflectionUtil.class);

    private ReflectionUtil() {
        throw new UnsupportedOperationException();
    }
    
    public static <T> T createInstance(Class<T> clazz) {
        try {
            Constructor<T> constructor = clazz.getConstructor();
            return constructor.newInstance();
        } catch (Exception e) {
            throw new InvalidParameterException("Cannot create instance of class: " + clazz);
        }
    }

    public static List<Field> getFieldWithAncestors(Class<?> clazz) {
        List<Field> entityFields = new ArrayList<Field>();
        while (clazz != null) {
            entityFields.addAll(Arrays.asList(clazz.getDeclaredFields()));
            clazz = clazz.getSuperclass();
        }
        return entityFields;
    }

    public static List<Field> getFieldWithoutAncestors(Class<?> clazz) {
        List<Field> entityFields = new ArrayList<Field>();
        entityFields.addAll(Arrays.asList(clazz.getDeclaredFields()));
        return entityFields;
    }

    public static Field getFieldByName(Class<?> entityClass, String fieldName) {
        Field field = null;
        while (entityClass != null) {
            try {
                field = entityClass.getDeclaredField(fieldName);
                if (field != null) {
                    return field;
                }
            } catch (Exception e) {
                entityClass = entityClass.getSuperclass();
            }
        }
        return field;
    }
    
    public static void setValue2Field(Field objField, Object destination, String data) {
        objField.setAccessible(true);
        
        try {
            objField.set(destination, getValueFromField(objField, data));
        } catch (Exception e) {
            LOGGER.error("{}", e);
        }
    }
    
    public static Object getValueFromField(Field objField, String data) {
        try {
            if (objField.getType().equals(Integer.class) || objField.getType().equals(int.class)) {
                return convertString2Int(data);
            } else if (objField.getType().equals(Long.class) || objField.getType().equals(long.class)) {
                return convertString2Long(data);
            } else if (objField.getType().equals(Date.class)) {
                return convertString2Date(data);
            } else if (objField.getType().equals(Boolean.class) || objField.getType().equals(boolean.class)) {
                return convertString2Boolean(data);
            }
        } catch (Exception e) {
            LOGGER.error("Error during converting type. Message={}", e.getMessage());
        }
        return data;
    }
}
