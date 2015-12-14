package com.myprj.crawler.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * @author DienNM (DEE)
 */

public class Md5 {
    
    public static String hex(File file) {
        try {
            FileInputStream fis = new FileInputStream(file);
            String md5 = DigestUtils.md5Hex(fis);
            fis.close();
            return md5;
        } catch (Exception e) {
            return null;
        }
    }

    public static String hex(InputStream inputStream) {
        try {
            return DigestUtils.md5Hex(inputStream);
        } catch (Exception e) {
            return null;
        }
    }

    public static String hex(String inputString) {
        try {
            return DigestUtils.md5Hex(inputString);
        } catch (Exception e) {
            return null;
        }
    }
}
