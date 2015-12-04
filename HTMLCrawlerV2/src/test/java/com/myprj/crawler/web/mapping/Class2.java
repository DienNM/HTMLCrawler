package com.myprj.crawler.web.mapping;

import java.util.ArrayList;
import java.util.List;

import com.myprj.crawler.annotation.DataTransfer;

/**
 * @author DienNM (DEE)
 */

public class Class2 {

    @DataTransfer("classValue1")
    private String classValue1;

    @DataTransfer("classValue2")
    private double classValue2;
    
    private List<Class3> classValue3s = new ArrayList<Class3>(); 
    
    public String getClassValue1() {
        return classValue1;
    }
    public void setClassValue1(String classValue1) {
        this.classValue1 = classValue1;
    }
    public double getClassValue2() {
        return classValue2;
    }
    public void setClassValue2(double classValue2) {
        this.classValue2 = classValue2;
    }
    public List<Class3> getClassValue3s() {
        return classValue3s;
    }
    public void setClassValue3s(List<Class3> classValue3) {
        this.classValue3s = classValue3;
    }
    
}
