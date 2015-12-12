package com.myprj.crawler.domain;

import static java.io.File.separator;

import java.io.File;
import java.security.InvalidParameterException;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.myprj.crawler.util.Serialization;
import com.myprj.crawler.util.StreamUtil;

/**
 * @author DienNM (DEE)
 */

public class MigrationParam {

    private final static String INDEX = "index.mapping".intern();
    private final static String VALUE = "value.mapping".intern();

    private String categoryKey;

    private String itemKey;

    private String siteKey;
    
    private String indexMappingFile;

    private String valueMappingFile;
    
    private Map<String, Object> indexMapping;
    
    public MigrationParam(String categoryKey, String itemKey) {
        this(categoryKey, itemKey, null);
    }

    public MigrationParam(String categoryKey, String itemKey, String siteKey) {
        if (StringUtils.isEmpty(categoryKey)) {
            throw new InvalidParameterException("CategoryKey is required");
        }
        if (StringUtils.isEmpty(itemKey)) {
            throw new InvalidParameterException("Item Key is required");
        }
        this.categoryKey = categoryKey;
        this.itemKey = itemKey;
        this.siteKey = siteKey;
        
        //String root = FileUtil.getDirPath(Config.get("mapping.dir"));
        String root = "/media/diennm/Working/dee-projects/open_source/data/mappings/";
        setIndexMappingFile(root + subIndexPath() + INDEX);

        if (!StringUtils.isEmpty(itemKey)) {
            setValueMappingFile(root + subValuePath() + VALUE);
        }
        validateMappingFiles();
    }
    
    public String subIndexPath() {
        return categoryKey + separator + itemKey + separator;
    }
    
    public String subValuePath() {
        return categoryKey + separator + itemKey + separator + siteKey + separator;
    }

    @SuppressWarnings("unchecked")
    private void validateMappingFiles() {
        File file = new File(indexMappingFile);
        if (!file.exists()) {
            throw new InvalidParameterException("File " + indexMappingFile + " not found");
        }
        String jsonIndexMapping = StreamUtil.readFile2String(indexMappingFile);
        if(StringUtils.isEmpty(jsonIndexMapping)) {
            throw new InvalidParameterException("File " + indexMappingFile + " is empty");
        }
        indexMapping = Serialization.deserialize(jsonIndexMapping, Map.class);
    }

    public String getCategoryKey() {
        return categoryKey;
    }

    public void setCategoryKey(String categoryKey) {
        this.categoryKey = categoryKey;
    }

    public String getItemKey() {
        return itemKey;
    }

    public void setItemKey(String itemKey) {
        this.itemKey = itemKey;
    }

    public String getSiteKey() {
        return siteKey;
    }

    public void setSiteKey(String siteKey) {
        this.siteKey = siteKey;
    }

    public String getIndexMappingFile() {
        return indexMappingFile;
    }

    public void setIndexMappingFile(String indexMappingFile) {
        this.indexMappingFile = indexMappingFile;
    }

    public String getValueMappingFile() {
        return valueMappingFile;
    }

    public void setValueMappingFile(String valueMappingFile) {
        this.valueMappingFile = valueMappingFile;
    }
    
    public Map<String, Object> getIndexMapping() {
        return indexMapping;
    }

    public void setIndexMapping(Map<String, Object> indexMapping) {
        this.indexMapping = indexMapping;
    }

}
