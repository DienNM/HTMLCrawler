package com.myprj.crawler.client;

import java.util.Map;

import com.myprj.crawler.domain.config.AttributeData;
import com.myprj.crawler.domain.config.ItemData;
import com.myprj.crawler.util.ItemStructureUtil;

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
        
        ItemData item = new ItemData();
        item.setId(1);

        AttributeData root = ItemStructureUtil.build(item, text);
        System.out.println(item.getItemContent().getContent().toString());
        
        Map<String, Object> detail = item.getItemContent().getContent();
        
        // populate
        ItemStructureUtil.populateValue2Attributes(detail, "1|content|name", "Bộ 16 son mẫu thử Aron Intrend Lipstick 5g x 16");
        ItemStructureUtil.populateValue2Attributes(detail, "1|content|price", "149.000 VND");
        ItemStructureUtil.populateValue2Attributes(detail, "1|content|originalPrice", "339.000 VND");
        ItemStructureUtil.populateValue2Attributes(detail, "1|content|includedInBox", "16 x thỏi");
        ItemStructureUtil.populateValue2Attributes(detail, "1|content|specifications", "Thương hiệu Aron");
        ItemStructureUtil.populateValue2Attributes(detail, "1|content|specifications", "Sản xuất tại Thái Lan");
        ItemStructureUtil.populateValue2Attributes(detail, "1|content|specifications", "Với 16 màu sắc khác nhau; dễ dàng thay đổi và phối màu với nhau");
        ItemStructureUtil.populateValue2Attributes(detail, "1|content|descriptionDetails|brand", "Aron");
        ItemStructureUtil.populateValue2Attributes(detail, "1|content|descriptionDetails|size|large", "XXL");
        ItemStructureUtil.populateValue2Attributes(detail, "1|content|descriptionDetails|size|large", "XL");
        ItemStructureUtil.populateValue2Attributes(detail, "1|content|descriptionDetails|size|medium", "M");
        ItemStructureUtil.populateValue2Attributes(detail, "1|content|descriptionDetails|size|detail|width", "150");
        ItemStructureUtil.populateValue2Attributes(detail, "1|content|descriptionDetails|size|detail|height", "200");
        
        System.out.println("After populate:...");
        System.out.println(item.getItemContent().getContent().toString());
        //ItemStructureUtil.print(root);
    }

}
