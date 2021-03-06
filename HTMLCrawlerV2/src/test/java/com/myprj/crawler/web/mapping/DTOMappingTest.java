package com.myprj.crawler.web.mapping;

import static com.myprj.crawler.web.enumeration.DTOLevel.DEFAULT;
import static com.myprj.crawler.web.enumeration.DTOLevel.SIMPLE;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.myprj.crawler.web.enumeration.DTOLevel;

/**
 * @author DienNM (DEE)
 */

public class DTOMappingTest {
    
    @Test
    public void testDTOMapping1() {
        DTOMapping dtoMapping = new DTOMapping();
        
        Map<DTOLevel, String> targetMappings = new HashMap<DTOLevel, String>();
        targetMappings.put(DTOLevel.SIMPLE, "a1,a2,a3");
        
        dtoMapping.setTargetMappings(targetMappings);
        dtoMapping.setTargetClassName("a.b.c.ABC");
        dtoMapping.init();
        
        Assert.assertEquals("a1", dtoMapping.getSimpleMap().get("a1").getFieldName());
        Assert.assertEquals("a2", dtoMapping.getSimpleMap().get("a2").getFieldName());
        Assert.assertEquals("a3", dtoMapping.getSimpleMap().get("a3").getFieldName());
    }
    
    @Test
    public void testDTOMapping2() {
        DTOMapping dtoMapping = new DTOMapping();
        
        Map<DTOLevel, String> targetMappings = new HashMap<DTOLevel, String>();
        targetMappings.put(DTOLevel.SIMPLE, "a1,a2,a3");
        targetMappings.put(DTOLevel.DEFAULT, "SIMPLE,a4,ref(SIMPLE)");
        targetMappings.put(DTOLevel.FULL, "DEFAULT,a5,a6(DEFAULT)");
        
        dtoMapping.setTargetMappings(targetMappings);
        dtoMapping.setTargetClassName("a.b.c.ABC");
        dtoMapping.init();
        
        Assert.assertEquals("a1", dtoMapping.getSimpleMap().get("a1").getFieldName());
        Assert.assertEquals("a2", dtoMapping.getSimpleMap().get("a2").getFieldName());
        Assert.assertEquals("a3", dtoMapping.getSimpleMap().get("a3").getFieldName());
        
        Assert.assertEquals("a1", dtoMapping.getDefaultMap().get("a1").getFieldName());
        Assert.assertEquals("a2", dtoMapping.getDefaultMap().get("a2").getFieldName());
        Assert.assertEquals("a3", dtoMapping.getDefaultMap().get("a3").getFieldName());
        Assert.assertEquals("a4", dtoMapping.getDefaultMap().get("a4").getFieldName());
        
        Assert.assertEquals("a1", dtoMapping.getFullMap().get("a1").getFieldName());
        Assert.assertEquals("a2", dtoMapping.getFullMap().get("a2").getFieldName());
        Assert.assertEquals("a3", dtoMapping.getFullMap().get("a3").getFieldName());
        
        Assert.assertEquals("a4", dtoMapping.getFullMap().get("a4").getFieldName());
        Assert.assertEquals(SIMPLE, dtoMapping.getFullMap().get("ref").getTargetRefType());
        
        Assert.assertEquals("a5", dtoMapping.getFullMap().get("a5").getFieldName());
        Assert.assertEquals("a6", dtoMapping.getFullMap().get("a6").getFieldName());
        Assert.assertEquals(DEFAULT, dtoMapping.getFullMap().get("a6").getTargetRefType());
    }
    
}
