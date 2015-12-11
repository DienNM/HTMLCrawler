package com.myprj.crawler.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author DienNM
 **/

public final class StreamUtil {

    private static Logger logger = LoggerFactory.getLogger(StreamUtil.class);

    public static void write(File file, String line, boolean append) {
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, append), "UTF8"));
            bw.write(line);
            bw.write(System.lineSeparator());
            bw.flush();
        } catch (Exception e) {
            logger.error(e.getMessage());
            if (logger.isDebugEnabled()) {
                logger.error("{}", e);
            }
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

    public static void writeImage(String imageUrl, String destinationFile) throws Exception {
        OutputStream out = null;
        InputStream is = null;
        try {
            URL url = new URL(imageUrl);
            is = url.openStream();
            int length;
            out = new FileOutputStream(destinationFile);
            byte[] b = new byte[2048];
            while ((length = is.read(b)) != -1) {
                out.write(b, 0, length);
            }
            out.flush();
        } finally {
            IOUtils.closeQuietly(is);
            IOUtils.closeQuietly(out);
        }
    }

    public static String getMIMEFormat(InputStream is) {
        ImageInputStream iis = null;
        try {
            iis = ImageIO.createImageInputStream(is);
            Iterator<ImageReader> iter = ImageIO.getImageReaders(iis);
            ImageReader reader = iter.next();
            String formatName = reader.getFormatName();
            System.out.println("FOUND IMAGE FORMAT :" + formatName);
            iis.close();
            return formatName;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        } finally {
            IOUtils.closeQuietly(is);
            IOUtils.closeQuietly(iis);
        }

    }

    public static void main(String[] args) {
        try {
            StreamUtil.writeImage("http://cdn.timviecnhanh.com/asset/home/img/employer/549274acd348a_1418884268.jpg",
                    "/media/diennm/Working/dee-projects/open_source/crawler/images/1");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static byte[] readImage(String imageUrl) throws Exception {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        InputStream is = null;
        try {
            URL url = new URL(imageUrl);
            is = url.openStream();
            int length;
            byte[] b = new byte[2048];
            while ((length = is.read(b)) != -1) {
                out.write(b, 0, length);
            }
            byte[] response = out.toByteArray();
            return response;
        } finally {
            IOUtils.closeQuietly(is);
            IOUtils.closeQuietly(out);
        }
    }

    /**
     * Encodes the byte array into base64 string
     * 
     * @param imageByteArray
     *            - byte array
     * @return String a {@link java.lang.String}
     */
    public static String encodeImage(byte[] imageByteArray) {
        return Base64.encodeBase64URLSafeString(imageByteArray);
    }

    /**
     * Decodes the base64 string into byte array
     * 
     * @param imageDataString
     *            - a {@link java.lang.String}
     * @return byte array
     */
    public static byte[] decodeImage(String imageDataString) {
        return Base64.decodeBase64(imageDataString);
    }

}
