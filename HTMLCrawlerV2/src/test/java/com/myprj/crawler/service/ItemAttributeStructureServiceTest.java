package com.myprj.crawler.service;

import static com.myprj.crawler.util.StreamUtil.readFile2String;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.myprj.crawler.AbstractTest;
import com.myprj.crawler.domain.config.ItemAttributeData;
import com.myprj.crawler.domain.config.ItemData;
import com.myprj.crawler.enumeration.AttributeType;
import com.myprj.crawler.util.AttributeStructureUtil;

/**
 * @author DienNM (DEE)
 */

public class ItemAttributeStructureServiceTest extends AbstractTest {

    @Autowired
    private ItemAttributeStructureService itemAttributeStructureService;

    private ItemData itemData;

    @Before
    public void startUp() {
        itemData = new ItemData();
        itemData.setId("index1");
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testJson1() {
        String json = readFile2String(AbstractTest.class.getResourceAsStream("/input-files/item-1.json"));
        ItemAttributeData root = itemAttributeStructureService.build(itemData, json);

        // Verify Content
        Map<String, Object> content = (Map<String, Object>) itemData.getSampleContent().getContent().get("content");
        System.out.println(content);
        Assert.assertTrue(content.get("name") instanceof String);
        Assert.assertTrue(content.get("name").toString().equals("EMPTY"));

        Assert.assertTrue(content.get("include") instanceof String);
        Assert.assertTrue(content.get("include").toString().equals("EMPTY"));

        Assert.assertTrue(content.get("image") instanceof String);
        Assert.assertTrue(content.get("image").toString().equals("EMPTY"));

        Assert.assertTrue(content.get("promotions") instanceof List);
        Assert.assertTrue(((List<Object>) content.get("promotions")).get(0) instanceof String);
        Assert.assertEquals("EMPTY", ((List<Object>) content.get("promotions")).get(0).toString());

        Assert.assertTrue(content.get("descriptions") instanceof List);
        Assert.assertTrue(((List<Object>) content.get("descriptions")).get(0) instanceof String);
        Assert.assertEquals("EMPTY", ((List<Object>) content.get("descriptions")).get(0).toString());

        Assert.assertTrue(content.get("album") instanceof List);
        Assert.assertTrue(((List<Object>) content.get("album")).get(0) instanceof String);
        Assert.assertEquals("EMPTY", ((List<Object>) content.get("album")).get(0).toString());

        Assert.assertTrue(content.get("attributes") instanceof List);
        Assert.assertTrue(((List<Object>) content.get("attributes")).get(0) instanceof Map);
        Map<String, Object> attributes = (Map<String, Object>) ((List<Object>) content.get("attributes")).get(0);

        Assert.assertEquals("EMPTY", attributes.get("key").toString());
        Assert.assertEquals("EMPTY", attributes.get("value").toString());

        // Verify AttributeItem

        List<ItemAttributeData> itemAtts = AttributeStructureUtil.navigateAttribtesFromRoot(root);
        Map<String, ItemAttributeData> attMaps = new HashMap<String, ItemAttributeData>();
        for (ItemAttributeData att : itemAtts) {
            if (attMaps.containsKey(att.getId())) {
                Assert.fail();
            }
            attMaps.put(att.getId(), att);
        }

        Assert.assertEquals("content", attMaps.get("index1|content").getName());
        Assert.assertEquals(null, attMaps.get("index1|content").getParentId());
        Assert.assertEquals(AttributeType.OBJECT, attMaps.get("index1|content").getType());

        Assert.assertEquals("name", attMaps.get("index1|content|name").getName());
        Assert.assertEquals("index1|content", attMaps.get("index1|content|name").getParentId());
        Assert.assertEquals(AttributeType.TEXT, attMaps.get("index1|content|name").getType());

        Assert.assertEquals("promotions", attMaps.get("index1|content|promotions").getName());
        Assert.assertEquals("index1|content", attMaps.get("index1|content|promotions").getParentId());
        Assert.assertEquals(AttributeType.LIST, attMaps.get("index1|content|promotions").getType());

        Assert.assertEquals("promotions", attMaps.get("index1|content|promotions|promotions").getName());
        Assert.assertEquals("index1|content|promotions", attMaps.get("index1|content|promotions|promotions").getParentId());
        Assert.assertEquals(AttributeType.TEXT, attMaps.get("index1|content|promotions|promotions").getType());

        Assert.assertEquals("include", attMaps.get("index1|content|include").getName());
        Assert.assertEquals("index1|content", attMaps.get("index1|content|include").getParentId());
        Assert.assertEquals(AttributeType.HTML, attMaps.get("index1|content|include").getType());

        Assert.assertEquals("descriptions", attMaps.get("index1|content|descriptions").getName());
        Assert.assertEquals("index1|content", attMaps.get("index1|content|descriptions").getParentId());
        Assert.assertEquals(AttributeType.LIST, attMaps.get("index1|content|descriptions").getType());
        
        Assert.assertEquals("descriptions", attMaps.get("index1|content|descriptions|descriptions").getName());
        Assert.assertEquals("index1|content|descriptions", attMaps.get("index1|content|descriptions|descriptions").getParentId());
        Assert.assertEquals(AttributeType.HTML, attMaps.get("index1|content|descriptions|descriptions").getType());

        Assert.assertEquals("image", attMaps.get("index1|content|image").getName());
        Assert.assertEquals("index1|content", attMaps.get("index1|content|image").getParentId());
        Assert.assertEquals(AttributeType.IMAGE, attMaps.get("index1|content|image").getType());

        Assert.assertEquals("album", attMaps.get("index1|content|album").getName());
        Assert.assertEquals("index1|content", attMaps.get("index1|content|album").getParentId());
        Assert.assertEquals(AttributeType.LIST, attMaps.get("index1|content|album").getType());
        
        Assert.assertEquals("album", attMaps.get("index1|content|album|album").getName());
        Assert.assertEquals("index1|content|album", attMaps.get("index1|content|album|album").getParentId());
        Assert.assertEquals(AttributeType.IMAGE, attMaps.get("index1|content|album|album").getType());

        Assert.assertEquals("attributes", attMaps.get("index1|content|attributes").getName());
        Assert.assertEquals("index1|content", attMaps.get("index1|content|attributes").getParentId());
        Assert.assertEquals(AttributeType.LIST, attMaps.get("index1|content|attributes").getType());

        Assert.assertEquals("attributes", attMaps.get("index1|content|attributes|attributes").getName());
        Assert.assertEquals("index1|content|attributes", attMaps.get("index1|content|attributes|attributes").getParentId());
        Assert.assertEquals(AttributeType.OBJECT, attMaps.get("index1|content|attributes|attributes").getType());

        Assert.assertEquals("key", attMaps.get("index1|content|attributes|attributes|key").getName());
        Assert.assertEquals("index1|content|attributes|attributes", attMaps.get("index1|content|attributes|attributes|key").getParentId());
        Assert.assertEquals(AttributeType.TEXT, attMaps.get("index1|content|attributes|attributes|key").getType());

        Assert.assertEquals("value", attMaps.get("index1|content|attributes|attributes|value").getName());
        Assert.assertEquals("index1|content|attributes|attributes", attMaps.get("index1|content|attributes|attributes|value").getParentId());
        Assert.assertEquals(AttributeType.HTML, attMaps.get("index1|content|attributes|attributes|value").getType());
    }
    
    
    
    @SuppressWarnings("unchecked")
    @Test
    public void testJson2() {
        String json = readFile2String(AbstractTest.class.getResourceAsStream("/input-files/item-2.json"));
        ItemAttributeData root = itemAttributeStructureService.build(itemData, json);

        // Verify Content
        Map<String, Object> content = (Map<String, Object>) itemData.getSampleContent().getContent().get("content");
        System.out.println(content);
        Assert.assertTrue(content.get("attributes") instanceof List);
        Assert.assertTrue(((List<Object>) content.get("attributes")).get(0) instanceof Map);
        Map<String, Object> attributes = (Map<String, Object>) ((List<Object>) content.get("attributes")).get(0);

        Assert.assertTrue(attributes.get("key") instanceof String);
        Assert.assertEquals("EMPTY", attributes.get("key").toString());

        Assert.assertTrue(attributes.get("value") instanceof List);
        Assert.assertEquals("EMPTY", ((List<Object>)attributes.get("value")).get(0).toString());

        // Detail
        Assert.assertTrue(attributes.get("detail") instanceof Map);
        Map<String, Object> detail = (Map<String, Object>)attributes.get("detail");
        
        Assert.assertTrue(detail.get("phone") instanceof String);
        Assert.assertEquals("EMPTY", detail.get("phone").toString());

        // Customer
        Assert.assertTrue(detail.get("customer") instanceof Map);
        Map<String, Object> customer = (Map<String, Object>)detail.get("customer");
        
        Assert.assertTrue(customer.get("name") instanceof String);
        Assert.assertEquals("EMPTY", customer.get("name").toString());
        Assert.assertTrue(customer.get("habits") instanceof List);
        Assert.assertEquals("EMPTY", ((List<Object>) customer.get("habits")).get(0).toString());
        
        // album
        Assert.assertTrue(detail.get("album") instanceof List);
        List<Object> album = (List<Object>)detail.get("album");
        Assert.assertTrue(album.get(0) instanceof Map);
        Map<String, Object> album0 = (Map<String, Object>)album.get(0);
        
        Assert.assertTrue(album0.get("image") instanceof String);
        Assert.assertEquals("EMPTY", album0.get("image").toString());
        Assert.assertTrue(album0.get("title") instanceof String);
        Assert.assertEquals("EMPTY", album0.get("title").toString());

        // Verify AttributeItem

        List<ItemAttributeData> itemAtts = AttributeStructureUtil.navigateAttribtesFromRoot(root);
        Map<String, ItemAttributeData> attMaps = new HashMap<String, ItemAttributeData>();
        for (ItemAttributeData att : itemAtts) {
            if (attMaps.containsKey(att.getId())) {
                Assert.fail();
            }
            System.out.println(att.getId());
            attMaps.put(att.getId(), att);
        }

        Assert.assertEquals("content", attMaps.get("index1|content").getName());
        Assert.assertEquals(null, attMaps.get("index1|content").getParentId());
        Assert.assertEquals(AttributeType.OBJECT, attMaps.get("index1|content").getType());

        
        Assert.assertEquals("attributes", attMaps.get("index1|content|attributes").getName());
        Assert.assertEquals("index1|content", attMaps.get("index1|content|attributes").getParentId());
        Assert.assertEquals(AttributeType.LIST, attMaps.get("index1|content|attributes").getType());


        Assert.assertEquals("attributes", attMaps.get("index1|content|attributes|attributes").getName());
        Assert.assertEquals("index1|content|attributes", attMaps.get("index1|content|attributes|attributes").getParentId());
        Assert.assertEquals(AttributeType.OBJECT, attMaps.get("index1|content|attributes|attributes").getType());

        Assert.assertEquals("key", attMaps.get("index1|content|attributes|attributes|key").getName());
        Assert.assertEquals("index1|content|attributes|attributes", attMaps.get("index1|content|attributes|attributes|key").getParentId());
        Assert.assertEquals(AttributeType.TEXT, attMaps.get("index1|content|attributes|attributes|key").getType());

        Assert.assertEquals("value", attMaps.get("index1|content|attributes|attributes|value").getName());
        Assert.assertEquals("index1|content|attributes|attributes", attMaps.get("index1|content|attributes|attributes|value").getParentId());
        Assert.assertEquals(AttributeType.LIST, attMaps.get("index1|content|attributes|attributes|value").getType());

        Assert.assertEquals("value", attMaps.get("index1|content|attributes|attributes|value|value").getName());
        Assert.assertEquals("index1|content|attributes|attributes|value", attMaps.get("index1|content|attributes|attributes|value|value").getParentId());
        Assert.assertEquals(AttributeType.HTML, attMaps.get("index1|content|attributes|attributes|value|value").getType());

        Assert.assertEquals("detail", attMaps.get("index1|content|attributes|attributes|detail").getName());
        Assert.assertEquals("index1|content|attributes|attributes", attMaps.get("index1|content|attributes|attributes|detail").getParentId());
        Assert.assertEquals(AttributeType.OBJECT, attMaps.get("index1|content|attributes|attributes|detail").getType());

        Assert.assertEquals("phone", attMaps.get("index1|content|attributes|attributes|detail|phone").getName());
        Assert.assertEquals("index1|content|attributes|attributes|detail", attMaps.get("index1|content|attributes|attributes|detail|phone").getParentId());
        Assert.assertEquals(AttributeType.TEXT, attMaps.get("index1|content|attributes|attributes|detail|phone").getType());
        
        Assert.assertEquals("customer", attMaps.get("index1|content|attributes|attributes|detail|customer").getName());
        Assert.assertEquals("index1|content|attributes|attributes|detail", attMaps.get("index1|content|attributes|attributes|detail|customer").getParentId());
        Assert.assertEquals(AttributeType.OBJECT, attMaps.get("index1|content|attributes|attributes|detail|customer").getType());

        Assert.assertEquals("name", attMaps.get("index1|content|attributes|attributes|detail|customer|name").getName());
        Assert.assertEquals("index1|content|attributes|attributes|detail|customer", attMaps.get("index1|content|attributes|attributes|detail|customer|name").getParentId());
        Assert.assertEquals(AttributeType.TEXT, attMaps.get("index1|content|attributes|attributes|detail|customer|name").getType());

        Assert.assertEquals("habits", attMaps.get("index1|content|attributes|attributes|detail|customer|habits").getName());
        Assert.assertEquals("index1|content|attributes|attributes|detail|customer", attMaps.get("index1|content|attributes|attributes|detail|customer|habits").getParentId());
        Assert.assertEquals(AttributeType.LIST, attMaps.get("index1|content|attributes|attributes|detail|customer|habits").getType());

        Assert.assertEquals("habits", attMaps.get("index1|content|attributes|attributes|detail|customer|habits|habits").getName());
        Assert.assertEquals("index1|content|attributes|attributes|detail|customer|habits", attMaps.get("index1|content|attributes|attributes|detail|customer|habits|habits").getParentId());
        Assert.assertEquals(AttributeType.TEXT, attMaps.get("index1|content|attributes|attributes|detail|customer|habits|habits").getType());

        
        Assert.assertEquals("album", attMaps.get("index1|content|attributes|attributes|detail|album").getName());
        Assert.assertEquals("index1|content|attributes|attributes|detail", attMaps.get("index1|content|attributes|attributes|detail|album").getParentId());
        Assert.assertEquals(AttributeType.LIST, attMaps.get("index1|content|attributes|attributes|detail|album").getType());

        Assert.assertEquals("album", attMaps.get("index1|content|attributes|attributes|detail|album|album").getName());
        Assert.assertEquals("index1|content|attributes|attributes|detail|album", attMaps.get("index1|content|attributes|attributes|detail|album|album").getParentId());
        Assert.assertEquals(AttributeType.OBJECT, attMaps.get("index1|content|attributes|attributes|detail|album|album").getType());

        Assert.assertEquals("image", attMaps.get("index1|content|attributes|attributes|detail|album|album|image").getName());
        Assert.assertEquals("index1|content|attributes|attributes|detail|album|album", attMaps.get("index1|content|attributes|attributes|detail|album|album|image").getParentId());
        Assert.assertEquals(AttributeType.IMAGE, attMaps.get("index1|content|attributes|attributes|detail|album|album|image").getType());

        Assert.assertEquals("title", attMaps.get("index1|content|attributes|attributes|detail|album|album|title").getName());
        Assert.assertEquals("index1|content|attributes|attributes|detail|album|album", attMaps.get("index1|content|attributes|attributes|detail|album|album|title").getParentId());
        Assert.assertEquals(AttributeType.TEXT, attMaps.get("index1|content|attributes|attributes|detail|album|album|title").getType());

    }

}
