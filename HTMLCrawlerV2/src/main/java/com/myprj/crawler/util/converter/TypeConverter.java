package com.myprj.crawler.util.converter;

import java.math.BigInteger;
import java.util.Date;

/**
 * @author DienNM (DEE)
 */

public final class TypeConverter {
    
    public static String convertInteger2String(Integer value) {
        if (value != null) {
            return value.toString();
        }
        return null;
    }

    public static long convertInteger2Long(Integer value) {
        if (value != null) {
            return Long.valueOf(value.toString());
        }
        return 0;
    }

    public static int convertBigInt2Int(BigInteger value) {
        if (value != null) {
            return Integer.valueOf(value.toString());
        }
        return 0;
    }

    public static long convertBigInt2Long(BigInteger value) {
        if (value != null) {
            return Long.valueOf(value.toString());
        }
        return 0;
    }

    public static int unwrapInteger(Integer value) {
        if (value != null) {
            return value.intValue();
        }
        return 0;
    }

    public static String convertObject2String(Object obj) {
        if (obj != null) {
            return obj.toString();
        }
        return null;
    }

    public static long convertString2Long(String value) {
        if (value == null) {
            return 0;
        }
        try {
            return Long.valueOf(value);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public static double convertString2Double(String value) {
        if (value == null) {
            return 0;
        }
        try {
            return Double.valueOf(value);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public static int convertString2Int(String value) {
        if (value == null) {
            return 0;
        }
        try {
            return Integer.valueOf(value);
        } catch (Exception e) {
            return 0;
        }
    }

    public static long convertObject2Long(Object value) {
        if (value == null) {
           return 0;
        }
        try {
            return Long.valueOf(value.toString());
        } catch (Exception e) {
            return 0;
        }
    }

    public static int convertObject2Int(Object value) {
        if (value == null) {
            return 0;
        }
        try {
            return Integer.valueOf(value.toString());
        } catch (Exception e) {
            return 0;
        }
    }

    public static Integer convertObject2Integer(Object value) {
        if (value == null) {
            return 0;
        }
        try {
            return Integer.valueOf(value.toString());
        } catch (Exception e) {
            return null;
        }
    }

    public static boolean convertString2Boolean(String value) {
        if (value == null) {
            return false;
        }
        if (value.equalsIgnoreCase("N") || value.equals("0")) {
            return false;
        } else if (value.equalsIgnoreCase("Y") || value.equals("1")) {
            return true;
        } 
        return Boolean.valueOf(value);
    }

    public static boolean convertObject2Boolean(Object object) {
        if(object == null) {
            return false;
        }
        return convertString2Boolean(object.toString());
    }
    
    @SuppressWarnings("deprecation")
    public static Date convertString2Date(String value) {
        if (value == null) {
            return null;
        }
        return new Date(value);
    }
}
