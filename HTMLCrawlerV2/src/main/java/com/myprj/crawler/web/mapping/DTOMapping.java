package com.myprj.crawler.web.mapping;

import static com.myprj.crawler.web.enumeration.TargetDTOLevel.DEFAULT;
import static com.myprj.crawler.web.enumeration.TargetDTOLevel.FULL;
import static com.myprj.crawler.web.enumeration.TargetDTOLevel.SIMPLE;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;

import com.myprj.crawler.web.enumeration.TargetDTOLevel;
import com.myprj.crawler.web.mapping.DTOField.DTOFieldType;

/**
 * @author DienNM (DEE)
 */

public class DTOMapping {

    private Pattern patten = Pattern.compile("\\w+\\(\\w+\\)");

    private Map<TargetDTOLevel, String> targetMappings;
    
    private String targetClassName;

    private Map<String, DTOField> simpleMap = new HashMap<String, DTOField>();
    private Map<String, DTOField> defaultMap = new HashMap<String, DTOField>();
    private Map<String, DTOField> fullMap = new HashMap<String, DTOField>();

    public DTOMapping() {
    }

    public Map<String, DTOField> getTargetMapping(TargetDTOLevel targetType) {
        if (TargetDTOLevel.SIMPLE.equals(targetType)) {
            return simpleMap;
        }
        if (TargetDTOLevel.DEFAULT.equals(targetType)) {
            return defaultMap;
        }
        if (TargetDTOLevel.FULL.equals(targetType)) {
            return fullMap;
        }
        return new HashMap<String, DTOField>();
    }

    @PostConstruct
    public void init() {
        intFields(simpleMap, targetMappings.get(SIMPLE));
        intFields(defaultMap, targetMappings.get(DEFAULT));
        intFields(fullMap, targetMappings.get(FULL));
        DTOHandler.register(getTargetClassName(), this);
    }

    private void intFields(Map<String, DTOField> map, String FIELD) {
        if(FIELD == null) {
            return;
        }
        String[] fields = FIELD.split(",");
        for (String field : fields) {
            if (TargetDTOLevel.SIMPLE.name().equalsIgnoreCase(field)) {
                intFields(map, targetMappings.get(SIMPLE));
            } else if (TargetDTOLevel.DEFAULT.name().equalsIgnoreCase(field)) {
                intFields(map, targetMappings.get(DEFAULT));
            } else if (TargetDTOLevel.FULL.name().equalsIgnoreCase(field)) {
                intFields(map, targetMappings.get(FULL));
            } else if (isReferenceField(field)) {
                DTOField dtoField = createDTOFieldRef(field);
                map.put(dtoField.getFieldName(), dtoField);
            } else {
                DTOField dtoField = createDTOField(field);
                map.put(dtoField.getFieldName(), dtoField);
            }
        }
    }

    private static DTOField createDTOFieldRef(String input) {
        String[] results = new String[2];
        results[0] = input.substring(0, input.indexOf("("));
        results[1] = input.substring(input.indexOf("(") + 1, input.indexOf(")"));

        DTOField dtoField = new DTOField(results[0], DTOFieldType.ref);
        dtoField.setTargetRefType(TargetDTOLevel.valueOf(results[1]));

        return dtoField;
    }

    private static DTOField createDTOField(String input) {
        DTOField dtoField = new DTOField(input, DTOFieldType.field);
        return dtoField;
    }

    private boolean isReferenceField(String field) {
        return patten.matcher(field).matches();
    }

    public Map<String, DTOField> getSimpleMap() {
        return simpleMap;
    }

    public Map<String, DTOField> getDefaultMap() {
        return defaultMap;
    }

    public Map<String, DTOField> getFullMap() {
        return fullMap;
    }

    public String getTargetClassName() {
        return targetClassName;
    }

    public void setTargetClassName(String targetClassName) {
        this.targetClassName = targetClassName;
    }

    public Map<TargetDTOLevel, String> getTargetMappings() {
        return targetMappings;
    }

    public void setTargetMappings(Map<TargetDTOLevel, String> targetMappings) {
        this.targetMappings = targetMappings;
    }

}
