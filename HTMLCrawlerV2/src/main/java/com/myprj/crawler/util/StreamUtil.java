package com.myprj.crawler.util;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * @author DienNM
 **/

public final class StreamUtil {
    
    public static void write(File file, String line, boolean append) {
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, append), "UTF8"));
            bw.write(line);
            bw.write(System.lineSeparator());
            bw.flush();
        } catch(Exception e) {
            System.out.println(e.getMessage());
        } finally {
            IOUtils.closeQuietly(bw);
        }
    }

    public static void write(File file, List<String> lines, boolean append) {
        write(file, StringUtils.join(lines, System.lineSeparator()), append);
    }
    
    public static String readFile2String(InputStream inputStream) {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(inputStream));
            String line = null;
            List<String> lines = new ArrayList<String>();
            while ((line = br.readLine()) != null) {
                if(line == null || line.trim().isEmpty()) {
                    continue;
                }
                lines.add(line);
            }
            if(lines.isEmpty()) {
                return "";
            }
            return StringUtils.join(lines, " ");
        } catch (Exception ex) {
            return "";
        } finally {
            IOUtils.closeQuietly(br);
        }
    }
    
}
