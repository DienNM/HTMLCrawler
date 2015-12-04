package com.myprj.crawler.util;

/**
 * @author DienNM (DEE)
 */

public final class TypeCompareUtil {
    
    public static int compareInteger(Integer int1, Integer int2) {
        if (int1 == null && int2 == null) {
            return 0;
        }
        if (int1 == null) {
            return -1;
        }
        if (int2 == null) {
            return 1;
        }
        return int1.compareTo(int2);
    }
    
}
