package com.myprj.crawler.web.mapping;

import static com.myprj.crawler.util.ReflectionUtil.getFieldWithAncestors;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.myprj.crawler.annotation.DataTransfer;
import com.myprj.crawler.util.ReflectionUtil;
import com.myprj.crawler.util.converter.TypeConverter;
import com.myprj.crawler.web.enumeration.TargetDTOLevel;
import com.myprj.crawler.web.exception.DtoConvertException;
import com.myprj.crawler.web.mapping.DTOField.DTOFieldType;

/**
 * @author DienNM (DEE)
 */

public final class DTOHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(DTOHandler.class.getName());

    private static Map<String, DTOMapping> dtoMappings = new HashMap<String, DTOMapping>();

    public static void register(String dtoClassName, DTOMapping dtoMapping) {
        dtoMappings.put(dtoClassName, dtoMapping);
    }
    
    public static <S> List<Map<String, Object>> convert(List<S> dtos, TargetDTOLevel targetDTOType) {
        return convert(dtos, targetDTOType, null);
    }

    public static <S> List<Map<String, Object>> convert(List<S> dtos, TargetDTOLevel targetDTOType, DTOConverter<S> dtoConverter) {
        List<Map<String, Object>> results = new ArrayList<Map<String, Object>>();
        for(S dto : dtos) {
            Map<String, Object> result = convert(dto, dto.getClass().getName(), targetDTOType, dtoConverter);
            results.add(result);
        }
        return results;
    }
    
    public static <S> Map<String, Object> convert(S dto, TargetDTOLevel targetDTOType) {
        return convert(dto, dto.getClass().getName(), targetDTOType, null);
    }

    public static <S> Map<String, Object> convert(S dto, String targetClass, TargetDTOLevel targetDTOType,
            DTOConverter<S> dtoConverter) {
        DTOMapping dtoMapping = dtoMappings.get(targetClass);
        if (dtoMapping == null) {
            throw new DtoConvertException(String.format("DTOMapping for %s has not been supported yet", targetClass));
        }
        Map<String, DTOField> destFieldsMap = dtoMapping.getTargetMapping(targetDTOType);
        return convert(dto, destFieldsMap, dtoConverter);
    }

    public static <S> Map<String, Object> convert(S dto, Map<String, DTOField> destFieldsMap,
            DTOConverter<S> dtoConverter) throws DtoConvertException {
        if (dto == null || destFieldsMap == null) {
            throw new DtoConvertException("DTO Object or Mapping is null");
        }
        Map<String, Object> result = new HashMap<String, Object>();
        List<Field> dtoFields = getFieldWithAncestors(dto.getClass());

        Map<String, String> mappedFieldsValues = new HashMap<String, String>();
        for (Field srcField : dtoFields) {
            DataTransfer srcAnnotationField = srcField.getAnnotation(DataTransfer.class);
            // Check whether setting a mapping
            if (srcAnnotationField == null || !destFieldsMap.containsKey(srcAnnotationField.value())) {
                continue;
            }
            srcField.setAccessible(true);

            DTOField destField = destFieldsMap.get(srcAnnotationField.value());
            if (destField == null) {
                continue;
            }
            if (mappedFieldsValues.containsKey(destField.getFieldName())) {
                throw new DtoConvertException("Duplicated for mapping field: " + destField.getFieldName());
            }

            try {
                Object value = srcField.get(dto);
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
                    Map<String, Object> resultRef = convert(value, refDestFieldMap, null);
                    result.put(destField.getFieldName(), resultRef);
                } else {
                    String valueString = TypeConverter.convertObject2String(value);
                    result.put(destField.getFieldName(), ReflectionUtil.getValueFromField(srcField, valueString));
                }
                mappedFieldsValues.put(destField.getFieldName(), null);
            } catch (IllegalAccessException e) {
                LOGGER.error("Error during mapping DTO: {} with Mapping: {}", dto.getClass().getName(),
                        destFieldsMap.toString());
            }
        }

        if (dtoConverter != null) {
            dtoConverter.convert(dto, result);
        }

        return result;
    }

}
