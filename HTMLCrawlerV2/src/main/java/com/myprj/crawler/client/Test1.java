package com.myprj.crawler.client;

import java.util.Map;

import com.myprj.crawler.domain.config.AttributeData;
import com.myprj.crawler.domain.config.ItemData;
import com.myprj.crawler.util.ItemStructureUtil;

/**
 * @author DienNM (DEE)
 */

public class Test1 {

    public static void main(String[] args) {
        String text = "{\"name\":\"\",\"price\":\"\",\"pastPrice\":\"\",\"includedInBox\":[\"\"],\"specifications\":[\"\"],\"descriptionDetails\":[{\"key\" : \"\",\"value\" : \"\"}]}";
        
        ItemData item = new ItemData();
        item.setId(1);

        AttributeData root = ItemStructureUtil.buildAttributes(item, text);
        System.out.println(item.getSampleContent().getContent().toString());
        ItemStructureUtil.print(root);
        
        Map<String, Object> detail = item.getSampleContent().getContent();
        // populate
        ItemStructureUtil.populateValue2Attribute(detail, "1|content|name", "Bộ 16 son mẫu thử Aron Intrend Lipstick 5g x 16");
        ItemStructureUtil.populateValue2Attribute(detail, "1|content|price", "149.000 VND");
        ItemStructureUtil.populateValue2Attribute(detail, "1|content|pastPrice", "339.000 VND");
        ItemStructureUtil.populateValue2Attribute(detail, "1|content|includedInBox", "16 x thỏi");
        ItemStructureUtil.populateValue2Attribute(detail, "1|content|specifications", "Thương hiệu Aron");
        ItemStructureUtil.populateValue2Attribute(detail, "1|content|specifications", "Sản xuất tại Thái Lan");
        ItemStructureUtil.populateValue2Attribute(detail, "1|content|specifications", "Với 16 màu sắc khác nhau; dễ dàng thay đổi và phối màu với nhau");
        ItemStructureUtil.populateValue2Attribute(detail, "1|content|descriptionDetails|1|key", "Model");
        ItemStructureUtil.populateValue2Attribute(detail, "1|content|descriptionDetails|1|value", "Rouge Edition Velvet");

        ItemStructureUtil.populateValue2Attribute(detail, "1|content|descriptionDetails|2|key", "Made-In");
        ItemStructureUtil.populateValue2Attribute(detail, "1|content|descriptionDetails|2|value", "VietName");
        
        System.out.println("After populate:...");
        System.out.println(item.getSampleContent().getContent().toString());
    }
}
