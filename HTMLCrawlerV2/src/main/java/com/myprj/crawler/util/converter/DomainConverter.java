package com.myprj.crawler.util.converter;

import static com.myprj.crawler.util.ReflectionUtil.createInstance;
import static com.myprj.crawler.util.ReflectionUtil.getFieldWithAncestors;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.myprj.crawler.annotation.DataTransfer;
import com.myprj.crawler.util.ReflectionUtil;

/**
 * @author DienNM (DEE)
 */

public final class DomainConverter {

    private static final Logger LOGGER = LoggerFactory.getLogger(DomainConverter.class.getName());

    public static <S, D> void convert(List<S> sources, List<D> dests, Class<D> clazz) {
        convert(sources, dests, clazz, null);
    }

    public static <S, D> void convert(List<S> sources, List<D> dests, Class<D> clazz,
            ObjectConverter<S, D> callbackConverter) {
        for (S source : sources) {
            D dest = createInstance(clazz);
            convert(source, dest, callbackConverter);
            dests.add(dest);
        }
    }

    public static <S, D> void convert(S source, D dest) {
        convert(source, dest, null);
    }

    public static <S, D> void convert(S source, D dest, ObjectConverter<S, D> callbackConverter) {
        if (source == null || dest == null) {
            return;
        }
        List<Field> destFields = getFieldWithAncestors(dest.getClass());
        List<Field> srcFields = getFieldWithAncestors(source.getClass());

        Map<String, Field> destFieldsMap = new HashMap<String, Field>();

        // Get Map of annotated name and Field in DestinationField annotation
        for (Field destField : destFields) {
            DataTransfer destAnnotationField = destField.getAnnotation(DataTransfer.class);
            if (destAnnotationField == null) {
                continue;
            }
            destFieldsMap.put(destAnnotationField.value(), destField);
        }

        Map<Field, String> mappedFieldsValues = new HashMap<Field, String>();
        for (Field srcField : srcFields) {
            DataTransfer srcAnnotationField = srcField.getAnnotation(DataTransfer.class);

            // Check whether setting a mapping
            if (srcAnnotationField == null || !destFieldsMap.containsKey(srcAnnotationField.value())) {
                continue;
            }

            srcField.setAccessible(true);

            Field destField = destFieldsMap.get(srcAnnotationField.value());
            if (mappedFieldsValues.containsKey(destField)) {
                LOGGER.error("Duplicated for mapping field: " + destField.getName());
                return;
            }
            destField.setAccessible(true);
            try {
                String value = TypeConverter.convertObject2String(srcField.get(source));
                ReflectionUtil.setValue2Field(destField, dest, value);
                mappedFieldsValues.put(destField, srcAnnotationField.value());
            } catch (IllegalAccessException e) {
                LOGGER.error("Count not init " + dest.getClass().getName() + " Source from Dest ", e);
            }
        }
        if (callbackConverter != null) {
            callbackConverter.convert(source, dest);
        }
    }

}
