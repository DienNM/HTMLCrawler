package com.myprj.crawler.util;

import java.util.Calendar;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author DienNM (DEE)
 */
public class DateUtilTest {
    
    @Test
    public void testConvertString2Date() {
        Assert.assertNull(DateUtil.convertString2Date("10/12/2015", "dd-MM-yyyy"));
        Assert.assertNotNull(DateUtil.convertString2Date("10-12-2015", "dd-MM-yyyy"));
        Assert.assertNotNull(DateUtil.convertString2Date("10/12/2015", "dd/MM/yyyy"));
    }
    
    @Test
    public void testConvertDate2String() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, 2015);
        calendar.set(Calendar.MONTH, 10);
        calendar.set(Calendar.DAY_OF_MONTH, 15);
        
        Assert.assertEquals("15-11-2015", DateUtil.convertDate2String(calendar.getTime(), "dd-MM-yyyy"));
        Assert.assertEquals("15/11/2015", DateUtil.convertDate2String(calendar.getTime(), "dd/MM/yyyy"));
    }
}
