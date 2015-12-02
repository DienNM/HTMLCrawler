package com.myprj.crawler.client;

import com.myprj.crawler.domain.config.AttributeData;
import com.myprj.crawler.domain.config.ItemData;
import com.myprj.crawler.service.impl.DefaultItemStructureBuilder;

/**
 * @author DienNM (DEE)
 */

public class Test {

    public static void main(String[] args) {
        String text = "{\"name\":\"\",\"price\":\"\", \"originalPrice\":\"\","
                + "\"includedInBox\":[\"\"],"
                + "\"specifications\":[{ \"att1\": \"\"}],"
                + "\"descriptionDetails\":{"
                        + "\"brand\" : \"\","
                        + "\"size\" : {"
                            + "\"large\" : [],"
                            + "\"medium\" : \"\", "
                            + "\"detail\" : {"
                                + "\"width\" : \"\", "
                                + "\"height\":\"\""
                            + "}}}}";
        
        DefaultItemStructureBuilder builder = new DefaultItemStructureBuilder();
        ItemData item = new ItemData();
        item.setId(1);

        AttributeData root = builder.build(item, text);
        System.out.println(item.getItemContent().getContent().toString());
        builder.print(root);
    }

}
