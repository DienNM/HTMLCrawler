package com.myprj.crawler.web.util;

import static org.apache.commons.lang3.StringUtils.join;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author DienNM (DEE)
 */

public final class ImportFileParser {

    private static Logger logger = LoggerFactory.getLogger(ImportFileParser.class);

    public static List<ImportFileStruture> loadItemFromSource(InputStream inputStream) {
        List<ImportFileStruture> importFileStrutures = new ArrayList<ImportFileStruture>();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(inputStream));
            String line = null;

            List<List<String>> lineOfItems = new ArrayList<List<String>>();
            List<String> lineOfStructures = new ArrayList<String>();

            boolean flagItem = false;
            boolean flagStructure = false;

            while ((line = br.readLine()) != null) {
                if (line == null || line.trim().isEmpty() || line.startsWith("#")) {
                    continue;
                }
                if (line.startsWith("<<START>>")) {
                    flagStructure = true;
                    flagItem = false;
                    continue;
                }
                if (line.startsWith("<<END>>")) {
                    flagStructure = false;
                    flagItem = true;

                    if (!lineOfItems.isEmpty()) {
                        ImportFileStruture importFileStruture = new ImportFileStruture();
                        importFileStruture.getMainLines().addAll(lineOfItems);
                        importFileStruture.setJson(join(lineOfStructures, " "));
                        importFileStrutures.add(importFileStruture);
                    }
                    lineOfItems = new ArrayList<List<String>>();
                    lineOfStructures = new ArrayList<String>();
                    continue;
                }
                
                if(line.startsWith(">")) {
                    flagItem = true;
                    List<String> subLines = new ArrayList<String>();
                    lineOfItems.add(subLines);
                    line = line.substring(1);
                }
                
                if (flagStructure) {
                    lineOfStructures.add(line);
                }
                
                if (flagItem) {
                    List<String> subLines = lineOfItems.get(lineOfItems.size() - 1);
                    subLines.add(line);
                }
            }
        } catch (Exception ex) {
            logger.error("Error loading Item. Error: {}", ex);
            return new ArrayList<ImportFileStruture>();
        } finally {
            IOUtils.closeQuietly(br);
            IOUtils.closeQuietly(inputStream);
        }
        return importFileStrutures;
    }
}
