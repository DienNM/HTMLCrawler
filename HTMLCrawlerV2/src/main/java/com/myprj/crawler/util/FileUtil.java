package com.myprj.crawler.util;

import static java.io.File.separator;

import java.io.File;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author DienNM (DEE)
 */

public final class FileUtil {
    
    private static Logger logger = LoggerFactory.getLogger(FileUtil.class);

    public static String getDirPath(String path) {
        if (StringUtils.isEmpty(path)) {
            return path;
        }
        if (path.endsWith(separator)) {
            return path;
        }
        return path + separator;
    }
    
    public static String makeDir(String parentDir , String dir) {
        parentDir = getDirPath(parentDir);
        dir = getDirPath(dir);
        
        String fullPath = parentDir + dir;
        File file = new File(fullPath);
        if(!file.exists()) {
            if(!file.mkdirs()) {
                logger.warn("Cannot create directory: " + fullPath);
                return null;
            }
        }
        return  getDirPath(fullPath);
    }

}
