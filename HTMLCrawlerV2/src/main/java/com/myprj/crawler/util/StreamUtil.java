package com.myprj.crawler.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
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
        } catch (Exception e) {
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
                if (line == null || line.trim().isEmpty()) {
                    continue;
                }
                lines.add(line);
            }
            if (lines.isEmpty()) {
                return "";
            }
            return StringUtils.join(lines, " ");
        } catch (Exception ex) {
            return "";
        } finally {
            IOUtils.closeQuietly(br);
        }
    }

    public static List<String> readFile2Strings(InputStream inputStream) {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(inputStream));
            String line = null;
            List<String> lines = new ArrayList<String>();
            while ((line = br.readLine()) != null) {
                if (line == null || line.trim().isEmpty()) {
                    continue;
                }
                lines.add(line);
            }
            return lines;
        } catch (Exception ex) {
            return new ArrayList<String>();
        } finally {
            IOUtils.closeQuietly(br);
        }
    }

    public static byte[] readImage(String imageUrl) throws Exception {
        URL url = new URL(imageUrl);
        InputStream is = url.openStream();
        int length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        byte[] b = new byte[2048];
        while ((length = is.read(b)) != -1) {
            out.write(b, 0, length);
        }
        byte[] response = out.toByteArray();
        is.close();
        out.close();

        return response;
    }
    
    /**
     * Encodes the byte array into base64 string
     *
     * @param imageByteArray - byte array
     * @return String a {@link java.lang.String}
     */
    public static String encodeImage(byte[] imageByteArray) {
        return Base64.encodeBase64URLSafeString(imageByteArray);
    }
 
    /**
     * Decodes the base64 string into byte array
     *
     * @param imageDataString - a {@link java.lang.String}
     * @return byte array
     */
    public static byte[] decodeImage(String imageDataString) {
        return Base64.decodeBase64(imageDataString);
    }
    
}
