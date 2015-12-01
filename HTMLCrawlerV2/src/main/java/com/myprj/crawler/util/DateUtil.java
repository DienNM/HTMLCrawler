package com.myprj.crawler.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author DienNM (DEE)
 */

public final class DateUtil {
    
    public static Date convertString2Date(String text, String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        try {
            return  simpleDateFormat.parse(text);
        } catch (ParseException e) {
            return null;
        }
    }
    
    public static String convertDate2String(Date date, String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        return  simpleDateFormat.format(date);
    }
    
    public static boolean lte(Date date1, Date date2) {
        if(date1 == date2) {
            return true;
        }
        return date1.before(date2);
    }
    
    public static int diff(Date date1, Date date2) {
        long diffMs = date2.getTime() - date1.getTime();
        return (int) TimeUnit.DAYS.convert(diffMs, TimeUnit.MILLISECONDS);
    }
    
    public static Date addDays(Date date, int days) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, days);
        return c.getTime();
    }
    
}
