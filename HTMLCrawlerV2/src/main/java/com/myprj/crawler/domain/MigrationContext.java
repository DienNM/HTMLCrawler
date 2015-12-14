package com.myprj.crawler.domain;

import static java.io.File.separator;

import java.io.File;
import java.security.InvalidParameterException;

import org.apache.commons.lang3.StringUtils;

import com.myprj.crawler.service.mapping.Mapping;
import com.myprj.crawler.util.Config;
import com.myprj.crawler.util.FileUtil;
import com.myprj.crawler.util.Serialization;
import com.myprj.crawler.util.StreamUtil;

/**
 * @author DienNM (DEE)
 */

public class MigrationContext {

    private final static String INDEX = "index.mapping".intern();
    private final static String VALUE = "value.mapping".intern();

    private String categoryKey;

    private String itemKey;

    private String siteKey;
    
    private String indexMappingFile;

    private String valueMappingFile;
    
    private Mapping index;
    
    public MigrationContext(String categoryKey, String itemKey) {
        this(categoryKey, itemKey, null);
    }

    public MigrationContext(String categoryKey, String itemKey, String siteKey) {
        if (StringUtils.isEmpty(categoryKey)) {
            throw new InvalidParameterException("CategoryKey is required");
        }
        if (StringUtils.isEmpty(itemKey)) {
            throw new InvalidParameterException("Item Key is required");
        }
        this.categoryKey = categoryKey;
        this.itemKey = itemKey;
        this.siteKey = siteKey;
        
        String root = FileUtil.getDirPath(Config.get("mapping.dir"));
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

    private void validateMappingFiles() {
        File file = new File(indexMappingFile);
        if (!file.exists()) {
            throw new InvalidParameterException("File " + indexMappingFile + " not found");
        }
        String jsonIndexMapping = StreamUtil.readFile2String(indexMappingFile);
        if(StringUtils.isEmpty(jsonIndexMapping)) {
            throw new InvalidParameterException("File " + indexMappingFile + " is empty");
        }
        index = Serialization.deserialize(jsonIndexMapping, Mapping.class);
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
    
    public Mapping getIndex() {
        return index;
    }

    public void setIndex(Mapping index) {
        this.index = index;
    }

}
