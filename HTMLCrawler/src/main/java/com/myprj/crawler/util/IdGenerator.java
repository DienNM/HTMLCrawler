package com.myprj.crawler.util;

import java.util.Calendar;

/**
 *
 * @author DienNM
 */
public final class IdGenerator {
    
    public static String genByDateTime(String prefix, String postfix) {
        
        String format = "%s%04d%02d%02d%02d%02d%02d%03d%s";
        
        Calendar calendar = Calendar.getInstance();
        
        return String.format(format, 
                                    prefix,
                                    calendar.get(Calendar.YEAR), 
                                    calendar.get(Calendar.MONTH) + 1,
                                    calendar.get(Calendar.DAY_OF_MONTH),
                                    calendar.get(Calendar.HOUR_OF_DAY),
                                    calendar.get(Calendar.MINUTE),
                                    calendar.get(Calendar.SECOND),
                                    calendar.get(Calendar.MILLISECOND),
                                    postfix
        );
    }
    
}
