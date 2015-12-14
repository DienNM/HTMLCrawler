package com.myprj.crawler.util;

import static java.io.File.separator;

import java.util.ArrayList;
import java.util.List;

import com.myprj.crawler.domain.DataMapping;

/**
 * @author DienNM (DEE)
 */

public final class MappingUtil {
    
    public static List<DataMapping> loadRuleMappings(String site, String category, String item) {
        
        List<DataMapping> dataMappings = new ArrayList<DataMapping>();

        String mappingDir = FileUtil.getDirPath(Config.get("mapping.dir"));
        String mappingFile = mappingDir + category + separator + item + separator + site + separator + "data.mapping";

        List<String> csvLines = StreamUtil.readCSVFile(mappingFile);
        if (csvLines.isEmpty()) {
            return dataMappings;
        }
        for (String line : csvLines) {
            DataMapping fieldMapping = DataMapping.parse(line);
            if (fieldMapping != null) {
                dataMappings.add(fieldMapping);
            }
        }
        return dataMappings;
    }
    
}
