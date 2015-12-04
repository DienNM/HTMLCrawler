package com.myprj.crawler.web.mapping;

import com.myprj.crawler.annotation.DataTransfer;

/**
 * @author DienNM (DEE)
 */

public class Class1 {
    
    @DataTransfer("value1")
    private String value1;
    
    @DataTransfer("value2")
    private String value2;

    @DataTransfer("value3")
    private Class2 value3;
    
    public String getValue1() {
        return value1;
    }
    public void setValue1(String value1) {
        this.value1 = value1;
    }
    public String getValue2() {
        return value2;
    }
    public void setValue2(String value2) {
        this.value2 = value2;
    }
    public Class2 getValue3() {
        return value3;
    }
    public void setValue3(Class2 value3) {
        this.value3 = value3;
    } 
    
}
