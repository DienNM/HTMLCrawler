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
import com.myprj.crawler.web.enumeration.TargetDTOType;
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
    
    public static <T> Map<String, Object> convert(T dto, String targetClass, TargetDTOType targetDTOType) throws DtoConvertException {
        DTOMapping dtoMapping = dtoMappings.get(targetClass);
        if(dtoMapping == null) {
            throw new DtoConvertException(String.format("DTOMapping for %s has not been supported yet", targetClass));
        }
        Map<String, DTOField> destFieldsMap = dtoMapping.getTargetMapping(targetDTOType);
        return convert(dto, destFieldsMap);
    }

    public static <T> Map<String, Object> convert(T dto, Map<String, DTOField> destFieldsMap)
            throws DtoConvertException {
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
                    Map<String, Object> resultRef = convert(value, refDestFieldMap);
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
        return result;
    }

}
