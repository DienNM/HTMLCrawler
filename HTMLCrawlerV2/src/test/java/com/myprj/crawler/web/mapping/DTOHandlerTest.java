package com.myprj.crawler.web.mapping;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.myprj.crawler.web.enumeration.TargetDTOType;
import com.myprj.crawler.web.exception.DtoConvertException;

/**
 * @author DienNM (DEE)
 */

public class DTOHandlerTest {
    
    @Before
    public void startUp() {
        DTOMapping class1Mapping = new DTOMapping();
        Map<TargetDTOType, String> class1TargetMapping = new HashMap<TargetDTOType, String>();
        class1TargetMapping.put(TargetDTOType.SIMPLE, "value1");
        class1TargetMapping.put(TargetDTOType.DEFAULT, "SIMPLE,value2,value3(DEFAULT)");
        class1TargetMapping.put(TargetDTOType.FULL, "SIMPLE,value2,value3(FULL)");
        class1Mapping.setTargetMappings(class1TargetMapping);
        class1Mapping.setTargetClassName("com.myprj.crawler.web.mapping.Class1");
        class1Mapping.init();
        
        DTOHandler.register(class1Mapping.getTargetClassName(), class1Mapping);
        
        DTOMapping class2Mapping = new DTOMapping();
        Map<TargetDTOType, String> class2TargetMapping = new HashMap<TargetDTOType, String>();
        class2TargetMapping.put(TargetDTOType.DEFAULT, "classValue1");
        class2TargetMapping.put(TargetDTOType.FULL, "classValue1,classValue2");
        class2Mapping.setTargetMappings(class2TargetMapping);
        class2Mapping.setTargetClassName("com.myprj.crawler.web.mapping.Class2");
        class2Mapping.init();
        
        DTOHandler.register(class2Mapping.getTargetClassName(), class2Mapping);
        
        DTOMapping class3Mapping = new DTOMapping();
        Map<TargetDTOType, String> class3TargetMapping = new HashMap<TargetDTOType, String>();
        class3TargetMapping.put(TargetDTOType.DEFAULT, "class3Value1,class3Value2");
        class3TargetMapping.put(TargetDTOType.FULL, "class3Value1,class3Value2,class3Value3");
        class3Mapping.setTargetMappings(class3TargetMapping);
        class3Mapping.setTargetClassName("com.myprj.crawler.web.mapping.Class3");
        class2Mapping.init();
        
        DTOHandler.register(class3Mapping.getTargetClassName(), class3Mapping);
    }
    
    @SuppressWarnings("unchecked")
    @Test
    public void testConvert() {
        Class1 class1 = createClass1();
        try {
            
            Map<String, Object> result = DTOHandler.convert(class1, Class1.class.getName(), TargetDTOType.SIMPLE);
            Assert.assertEquals(1, result.size());
            Assert.assertEquals("Class1 Value1", result.get("value1"));
            
            result = DTOHandler.convert(class1, Class1.class.getName(), TargetDTOType.DEFAULT);
            Assert.assertEquals(3, result.size());
            Assert.assertEquals("Class1 Value1", result.get("value1"));
            Assert.assertEquals("Class1 Value2", result.get("value2"));
            Assert.assertEquals("Class2 Value1", ((Map<String, Object>)result.get("value3")).get("classValue1") );
            
            result = DTOHandler.convert(class1, Class1.class.getName(), TargetDTOType.FULL);
            Assert.assertEquals(3, result.size());
            Assert.assertEquals("Class1 Value1", result.get("value1"));
            Assert.assertEquals("Class1 Value2", result.get("value2"));
            Assert.assertEquals("Class2 Value1", ((Map<String, Object>)result.get("value3")).get("classValue1") );
            Assert.assertEquals("12.6", ((Map<String, Object>)result.get("value3")).get("classValue2") );
        } catch (DtoConvertException e) {
            System.out.println(e.getMessage());
        }
    }
    
    private Class1 createClass1() {
        Class1 class1 = new Class1();
        class1.setValue1("Class1 Value1");
        class1.setValue2("Class1 Value2");
        
        Class2 class2 = new Class2();
        class2.setClassValue1("Class2 Value1");
        class2.setClassValue2(12.6);
        
        Class3 class31 = new Class3();
        class31.setClass3Value1(10);
        class31.setClass3Value2(123);
        class31.setClass3Value3(Calendar.getInstance().getTime());
        class2.getClassValue3s().add(class31);
        
        Class3 class32 = new Class3();
        class32.setClass3Value1(11);
        class32.setClass3Value2(1234);
        class32.setClass3Value3(Calendar.getInstance().getTime());
        class2.getClassValue3s().add(class32);
        
        class1.setValue3(class2);
        
        return class1;
    }
}
