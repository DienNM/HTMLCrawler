package com.myprj.crawler.util.converter;

import static com.myprj.crawler.util.ReflectionUtil.getFieldWithAncestors;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.myprj.crawler.annotation.Consolidation;
import com.myprj.crawler.util.ReflectionUtil;

/**
 * @author DienNM (DEE)
 */

public final class ConsolidationConverter {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConsolidationConverter.class.getName());

    public static <S, D> void convert(Map<String, Object> source, D dest) {
        if (source == null || dest == null) {
            return;
        }
        List<Field> destFields = getFieldWithAncestors(dest.getClass());
        Map<String, Field> destFieldsMap = new HashMap<String, Field>();
        for (Field destField : destFields) {
            Consolidation destAnnotationField = destField.getAnnotation(Consolidation.class);
            if (destAnnotationField == null) {
                continue;
            }
            destFieldsMap.put(destAnnotationField.value(), destField);
        }

        Map<Field, String> mappedFieldsValues = new HashMap<Field, String>();
        for (String key : source.keySet()) {
            Field destField = destFieldsMap.get(key);
            if (mappedFieldsValues.containsKey(destField)) {
                LOGGER.error("Duplicated for mapping field: " + destField.getName());
                return;
            }
            destField.setAccessible(true);
            ReflectionUtil.setValue2Field(destField, dest, source.get(key));
            mappedFieldsValues.put(destField, key);
        }
        
    }

}
