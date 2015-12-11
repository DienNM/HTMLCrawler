package com.myprj.crawler.util;

import java.util.Calendar;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author DienNM (DEE)
 */

public final class IdGenerator {

    private static AtomicInteger count = new AtomicInteger(0);
    private final static int MAX = 10000;
    
    public synchronized static String generateIdByTime() {
        Calendar calendar = Calendar.getInstance();
        if(count.incrementAndGet() > MAX) {
            count.set(1);
        }
        return String.format("%04d%02d%02d-%02d%02d%02d-%03d-%04d", 
                calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH), 
                calendar.get(Calendar.HOUR), calendar.get(Calendar.MINUTE), calendar.get(Calendar.SECOND), 
                calendar.get(Calendar.MILLISECOND), count.get());
        
    }
    
    public synchronized static String generateStringByDate() {
        Calendar calendar = Calendar.getInstance();
        return String.format("%04d-%02d-%02d", 
                calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH));
        
    }
}
