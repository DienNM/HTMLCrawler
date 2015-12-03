package com.myprj.crawler.client;

import java.util.Map;

import com.myprj.crawler.domain.config.AttributeData;
import com.myprj.crawler.domain.config.ItemData;
import com.myprj.crawler.service.AttributeStructureService;
import com.myprj.crawler.service.impl.AttributeStructureServiceImpl;
import com.myprj.crawler.util.ItemStructureUtil;

/**
 * @author DienNM (DEE)
 */

public class TestAttributeBuilder {

    public static void main(String[] args) {
        
        AttributeStructureService<AttributeData> attributeStructureService = new AttributeStructureServiceImpl();
        
        String text = "{\"name\":\"\",\"price\":\"\", \"originalPrice\":\"\","
                + "\"includedInBox\":[\"\"],"
                + "\"specifications\":[\"\"],"
                + "\"descriptionDetails\":{"
                        + "\"brand\" : \"\","
                        + "\"size\" : {"
                            + "\"large\" : [],"
                            + "\"medium\" : \"\", "
                            + "\"detail\" : {"
                                + "\"width\" : \"\", "
                                + "\"height\":\"\""
                            + "}}}}";
        
        ItemData item = new ItemData();
        item.setId(1);

        AttributeData root = attributeStructureService.build(item, text);
        System.out.println(item.getSampleContent().getContent().toString());
        ItemStructureUtil.print(root);
        
        Map<String, Object> detail = item.getSampleContent().getContent();
        // populate
        ItemStructureUtil.populateValue2Attribute(detail, "1|content|name", "Bộ 16 son mẫu thử Aron Intrend Lipstick 5g x 16");
        ItemStructureUtil.populateValue2Attribute(detail, "1|content|price", "149.000 VND");
        ItemStructureUtil.populateValue2Attribute(detail, "1|content|originalPrice", "339.000 VND");
        ItemStructureUtil.populateValue2Attribute(detail, "1|content|includedInBox", "16 x thỏi");
        ItemStructureUtil.populateValue2Attribute(detail, "1|content|specifications", "Thương hiệu Aron");
        ItemStructureUtil.populateValue2Attribute(detail, "1|content|specifications", "Sản xuất tại Thái Lan");
        ItemStructureUtil.populateValue2Attribute(detail, "1|content|specifications", "Với 16 màu sắc khác nhau; dễ dàng thay đổi và phối màu với nhau");
        ItemStructureUtil.populateValue2Attribute(detail, "1|content|descriptionDetails|brand", "Aron");
        ItemStructureUtil.populateValue2Attribute(detail, "1|content|descriptionDetails|size|large", "XXL");
        ItemStructureUtil.populateValue2Attribute(detail, "1|content|descriptionDetails|size|large", "XL");
        ItemStructureUtil.populateValue2Attribute(detail, "1|content|descriptionDetails|size|medium", "M");
        ItemStructureUtil.populateValue2Attribute(detail, "1|content|descriptionDetails|size|detail|width", "150");
        ItemStructureUtil.populateValue2Attribute(detail, "1|content|descriptionDetails|size|detail|height", "200");
        
        System.out.println("After populate:...");
        System.out.println(item.getSampleContent().getContent().toString());
    }
}
