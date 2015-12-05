package com.myprj.crawler.web.mapping;

import static com.myprj.crawler.util.ReflectionUtil.getFieldWithAncestors;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.myprj.crawler.annotation.DataTransfer;
import com.myprj.crawler.util.ReflectionUtil;
import com.myprj.crawler.util.converter.TypeConverter;
import com.myprj.crawler.web.enumeration.DTOLevel;
import com.myprj.crawler.web.exception.DtoConvertException;
import com.myprj.crawler.web.mapping.DTOField.DTOFieldType;

/**
 * @author DienNM (DEE)
 */

public final class DTOMappingHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(DTOMappingHandler.class.getName());

    private static Map<String, DTOMapping> dtoMappings = new HashMap<String, DTOMapping>();

    public static void register(String dtoClassName, DTOMapping dtoMapping) {
        dtoMappings.put(dtoClassName, dtoMapping);
    }

    /**
     * Convert List<SOURCE> to List<Map<String, Object>> by DTOLevel
     */
    public static <S> void mapList(List<S> dtoSources, List<Map<String, Object>> dests, DTOLevel level) {
        mapList(dtoSources, dests, level, null);
    }

    /**
     * Including DTOMapper<S> callback Convert List<SOURCE> to List<Map<String,
     * Object>> by DTOLevel
     */
    public static <S> void mapList(List<S> dtos, List<Map<String, Object>> dests, DTOLevel level, DTOMapper<S> callback) {
        for (S dto : dtos) {
            Map<String, Object> dest = new HashMap<String, Object>();
            map(dto, dest, level, callback);
            dests.add(dest);
        }
    }

    /**
     * Without DTOMapper<S> Map S to Map<String, Object> by: TargetClass and
     * DTOLevel
     */
    public static <S> void map(S dto, Map<String, Object> dest, DTOLevel level) {
        map(dto, dest, level, null);
    }

    /**
     * Including: DTOMapper<S> Map S to Map<String, Object> by: TargetClass and
     * DTOLevel and DTOMapper<S>
     */
    public static <S> void map(S dto, Map<String, Object> dest, DTOLevel level, DTOMapper<S> callback) {

        String className = dto.getClass().getName();
        DTOMapping dtoMapping = dtoMappings.get(className);
        if (dtoMapping == null) {
            throw new DtoConvertException("DTOMapping for " + className + " has not been supported yet");
        }
        Map<String, DTOField> fieldsMap = dtoMapping.getTargetMapping(level);
        doMap(dto, dest, fieldsMap, callback);

    }

    private static <S> void doMap(S dtoSource, Map<String, Object> dest, Map<String, DTOField> fieldMaps,
            DTOMapper<S> callback) throws DtoConvertException {

        if (dtoSource == null || fieldMaps == null) {
            throw new DtoConvertException("DTO Object or Mapping is null");
        }

        List<Field> dtoFields = getFieldWithAncestors(dtoSource.getClass());

        Map<String, String> mappedFieldsValues = new HashMap<String, String>();
        for (Field srcField : dtoFields) {
            DataTransfer srcAnnotationField = srcField.getAnnotation(DataTransfer.class);
            // Check whether setting a mapping
            if (srcAnnotationField == null || !fieldMaps.containsKey(srcAnnotationField.value())) {
                continue;
            }

            srcField.setAccessible(true);
            DTOField destField = fieldMaps.get(srcAnnotationField.value());
            if (destField == null) {
                continue;
            }
            if (mappedFieldsValues.containsKey(destField.getFieldName())) {
                throw new DtoConvertException("Duplicated for mapping field: " + destField.getFieldName());
            }

            try {
                Object value = srcField.get(dtoSource);
                if (DTOFieldType.ref.equals(destField.getType())) {
                    DTOMapping dtoMapping = dtoMappings.get(value.getClass().getName());
                    if (dtoMapping == null) {
                        LOGGER.warn("There is no DTOMapping for " + destField.getFieldName());
                        continue;
                    }
                    Map<String, DTOField> refDestFieldMap = dtoMapping.getTargetMapping(destField.getTargetRefType());
                    if (refDestFieldMap.isEmpty()) {
                        LOGGER.warn("There is no DTOMapping on {} for {}", destField.getTargetRefType(),
                                destField.getFieldName());
                        continue;
                    }

                    Map<String, Object> destRefs = new HashMap<String, Object>();
                    doMap(value, destRefs, refDestFieldMap, null);

                    dest.put(destField.getFieldName(), destRefs);
                } else {
                    String valueString = TypeConverter.convertObject2String(value);
                    dest.put(destField.getFieldName(), ReflectionUtil.getValueFromField(srcField, valueString));
                }
                mappedFieldsValues.put(destField.getFieldName(), null);
            } catch (IllegalAccessException e) {
                LOGGER.error("Error during mapping DTO: {} with Mapping: {}", dtoSource.getClass().getName(),
                        fieldMaps.toString());
            }
        }

        if (callback != null) {
            callback.map(dtoSource, dest);
        }
    }
}
