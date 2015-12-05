package com.myprj.crawler.util.converter;

import static com.myprj.crawler.util.ReflectionUtil.getFieldWithAncestors;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.myprj.crawler.annotation.EntityTransfer;
import com.myprj.crawler.util.ReflectionUtil;

/**
 * @author DienNM (DEE)
 */

public final class EntityConverter {

    private static final Logger LOGGER = LoggerFactory.getLogger(EntityConverter.class.getName());

    public static <S, D> void convert2Entity(S source, D dest) {
        convert2Entity(source, dest, null);
    }

    public static <S, D> void convert2Entity(S source, D dest, ObjectConverter<S, D> objectConverter) {
        if (source == null || dest == null) {
            return;
        }

        List<Field> destFields = getFieldWithAncestors(dest.getClass());
        List<Field> srcFields = getFieldWithAncestors(source.getClass());

        Map<String, Field> destFieldsMap = new HashMap<String, Field>();

        // Get Map of annotated name and Field in DestinationField annotation
        for (Field destField : destFields) {
            Column destAnnotationField = destField.getAnnotation(Column.class);
            if (destAnnotationField == null) {
                continue;
            }
            destFieldsMap.put(destAnnotationField.name(), destField);
        }

        Map<Field, String> mappedFieldsValues = new HashMap<Field, String>();
        for (Field srcField : srcFields) {
            EntityTransfer srcAnnotationField = srcField.getAnnotation(EntityTransfer.class);

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
                ReflectionUtil.setValue2Field(destField, dest, srcField.get(source));
                mappedFieldsValues.put(destField, srcAnnotationField.value());
            } catch (IllegalAccessException e) {
                LOGGER.error("Count not init " + dest.getClass().getName() + " Source from Dest ", e);
            }
        }
        if (objectConverter != null) {
            objectConverter.convert(source, dest);
        }
    }

    public static <S, D> void convert2Data(S source, D dest) {
        convert2Data(source, dest, null);
    }

    public static <S, D> void convert2Data(S source, D dest, ObjectConverter<S, D> objectConverter) {
        if (source == null || dest == null) {
            return;
        }

        List<Field> destFields = getFieldWithAncestors(dest.getClass());
        List<Field> srcFields = getFieldWithAncestors(source.getClass());

        Map<String, Field> destFieldsMap = new HashMap<String, Field>();

        // Get Map of annotated name and Field in DestinationField annotation
        for (Field destField : destFields) {
            EntityTransfer destAnnotationField = destField.getAnnotation(EntityTransfer.class);
            if (destAnnotationField == null) {
                continue;
            }
            destFieldsMap.put(destAnnotationField.value(), destField);
        }

        Map<Field, String> mappedFieldsValues = new HashMap<Field, String>();
        for (Field srcField : srcFields) {
            Column srcAnnotationField = srcField.getAnnotation(Column.class);

            // Check whether setting a mapping
            if (srcAnnotationField == null || !destFieldsMap.containsKey(srcAnnotationField.name())) {
                continue;
            }

            srcField.setAccessible(true);

            Field destField = destFieldsMap.get(srcAnnotationField.name());
            if (mappedFieldsValues.containsKey(destField)) {
                LOGGER.error("Duplicated for mapping field: " + destField.getName());
                return;
            }
            destField.setAccessible(true);
            try {
                ReflectionUtil.setValue2Field(destField, dest, srcField.get(source));
                mappedFieldsValues.put(destField, srcAnnotationField.name());
            } catch (IllegalAccessException e) {
                LOGGER.error("Count not init " + dest.getClass().getName() + " Source from Dest ", e);
            }
        }
        if (objectConverter != null) {
            objectConverter.convert(source, dest);
        }
    }

}
