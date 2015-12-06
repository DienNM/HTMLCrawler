package com.myprj.crawler.util.converter;

import static com.myprj.crawler.util.ReflectionUtil.getFieldWithAncestors;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.myprj.crawler.annotation.DataCopy;

/**
 * @author DienNM (DEE)
 */

public final class DomainCopy {

    private static final Logger LOGGER = LoggerFactory.getLogger(DomainCopy.class.getName());

    public static <S, D> void copy(S source, D dest) {
        copy(source, dest, null);
    }

    public static <S, D> void copy(S source, D dest, ObjectCopy<S, D> objectCopy) {
        if (source == null || dest == null) {
            return;
        }
        List<Field> destFields = getFieldWithAncestors(dest.getClass());
        List<Field> srcFields = getFieldWithAncestors(source.getClass());

        Map<String, Field> destFieldsMap = new HashMap<String, Field>();

        // Get Map of annotated name and Field in DestinationField annotation
        for (Field destField : destFields) {
            DataCopy destAnnotationField = destField.getAnnotation(DataCopy.class);
            if (destAnnotationField == null) {
                continue;
            }
            destFieldsMap.put(destAnnotationField.value(), destField);
        }

        Map<Field, String> mappedFieldsValues = new HashMap<Field, String>();
        for (Field srcField : srcFields) {
            DataCopy srcAnnotationField = srcField.getAnnotation(DataCopy.class);

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
                destField.set(dest, srcField.get(source));
                mappedFieldsValues.put(destField, srcAnnotationField.value());
            } catch (IllegalAccessException e) {
                LOGGER.error("Count not init " + dest.getClass().getName() + " Source from Dest ", e);
            }
        }
        if (objectCopy != null) {
            objectCopy.copy(source, dest);
        }
    }

}
