package com.myprj.crawler.client;

import java.util.Map;

import com.myprj.crawler.domain.config.AttributeData;
import com.myprj.crawler.service.impl.DefaultItemStructureBuilder;
import com.myprj.crawler.util.Serialization;

/**
 * @author DienNM (DEE)
 */

public class Test {

    public static void main(String[] args) {
        String text = "{\"name\":\"\",\"price\":\"\",\"originalPrice\":\"\",\"specifications\":[\"\"],\"includedInBox\":[\"\"],\"descriptionDetails\":{\"brand\" : \"\",\"gender\" : \"\",\"size\" : {\"large\" : [\"\"],\"medium\" : [\"\"]}}}";
        Map<String, Object> map = Serialization.deserialize(text, Map.class);
        DefaultItemStructureBuilder a = new DefaultItemStructureBuilder();
        AttributeData root = a.build(1, text);
        a.print(root);
    }
    

}
