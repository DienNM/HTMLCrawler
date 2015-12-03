package com.myprj.crawler.client;

import java.util.Map;

import com.myprj.crawler.domain.config.AttributeData;
import com.myprj.crawler.domain.config.ItemData;
import com.myprj.crawler.service.ItemStructureService;
import com.myprj.crawler.service.impl.AttributeStructureServiceImpl;
import com.myprj.crawler.util.ItemStructureUtil;
/**
 * @author DienNM (DEE)
 */
public class TestAttributeBuilder2 {
    private ItemData itemData;
    AttributeData root = null;
    
    public static void main(String[] args) {
        TestAttributeBuilder2 testBuildAttribute2 = new TestAttributeBuilder2();
        testBuildAttribute2.buildAttributeStructure();
        System.out.println("After building Structure:");
        System.out.println(testBuildAttribute2.getItemData().getSampleContent().getContent().toString());
        System.out.println("===================================================");
        
        System.out.println("Structure:");
        System.out.println("*************************************************");
        testBuildAttribute2.print();
        System.out.println("*************************************************");
        
        testBuildAttribute2.populateData();
        System.out.println("After building Populate Data:");
        System.out.println(testBuildAttribute2.getItemData().getSampleContent().getContent().toString());
        System.out.println("===================================================");
        
        
    }

    public TestAttributeBuilder2() {
        itemData = new ItemData();
        itemData.setId(1);
    }

    public void buildAttributeStructure() {
        ItemStructureService<AttributeData> attributeStructureService = new AttributeStructureServiceImpl();
        String text = "{\"name\":\"\",\"price\":\"\",\"pastPrice\":\"\",\"includedInBox\":[\"\"],\"specifications\":[\"\"],\"descriptionDetails\":[{\"key\" : \"\",\"value\" : \"\"}]}";
        root = attributeStructureService.buildAttributes(itemData, text);
    }

    public void print() {
        ItemStructureUtil.print(root);
    }

    public void populateData() {
        Map<String, Object> detail = itemData.getSampleContent().getContent();
        // populate
        ItemStructureUtil.populateValue2Attribute(detail, "1|content|name",
                "Bộ 16 son mẫu thử Aron Intrend Lipstick 5g x 16");
        ItemStructureUtil.populateValue2Attribute(detail, "1|content|price", "149.000 VND");
        ItemStructureUtil.populateValue2Attribute(detail, "1|content|pastPrice", "339.000 VND");
        ItemStructureUtil.populateValue2Attribute(detail, "1|content|includedInBox", "16 x thỏi");
        ItemStructureUtil.populateValue2Attribute(detail, "1|content|specifications", "Thương hiệu Aron");
        ItemStructureUtil.populateValue2Attribute(detail, "1|content|specifications", "Sản xuất tại Thái Lan");
        ItemStructureUtil.populateValue2Attribute(detail, "1|content|specifications",
                "Với 16 màu sắc khác nhau; dễ dàng thay đổi và phối màu với nhau");
        ItemStructureUtil.populateValue2Attribute(detail, "1|content|descriptionDetails|1|key", "Model");
        ItemStructureUtil.populateValue2Attribute(detail, "1|content|descriptionDetails|1|value",
                "Rouge Edition Velvet");

        ItemStructureUtil.populateValue2Attribute(detail, "1|content|descriptionDetails|2|key", "Made-In");
        ItemStructureUtil.populateValue2Attribute(detail, "1|content|descriptionDetails|2|value", "VietName");
    }

    public ItemData getItemData() {
        return itemData;
    }

    public void setItemData(ItemData itemData) {
        this.itemData = itemData;
    }
    
    public AttributeData getRoot() {
        return root;
    }
}
