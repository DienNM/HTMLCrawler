package com.myprj.crawler.web.mapping;

import java.util.Date;

import com.myprj.crawler.annotation.DataTransfer;

/**
 * @author DienNM (DEE)
 */

public class Class3 {

    @DataTransfer("class3Value1")
    private int class3Value1;

    @DataTransfer("class3Value2")
    private long class3Value2;

    @DataTransfer("class3Value3")
    private Date class3Value3;
    
    public int getClass3Value1() {
        return class3Value1;
    }
    public void setClass3Value1(int class3Value1) {
        this.class3Value1 = class3Value1;
    }
    public long getClass3Value2() {
        return class3Value2;
    }
    public void setClass3Value2(long class3Value2) {
        this.class3Value2 = class3Value2;
    }
    public Date getClass3Value3() {
        return class3Value3;
    }
    public void setClass3Value3(Date class3Value3) {
        this.class3Value3 = class3Value3;
    }
}
