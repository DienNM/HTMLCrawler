package com.myprj.crawler.service;

import static com.myprj.crawler.util.StreamUtil.readFile2String;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.myprj.crawler.AbstractTest;
import com.myprj.crawler.domain.config.ItemAttributeData;
import com.myprj.crawler.domain.config.ItemData;
import com.myprj.crawler.domain.config.WorkerItemAttributeData;
import com.myprj.crawler.domain.crawl.WorkerItemData;
import com.myprj.crawler.enumeration.AttributeType;
import com.myprj.crawler.enumeration.CrawlType;
import com.myprj.crawler.enumeration.Level;
import com.myprj.crawler.util.AttributeStructureUtil;

/**
 * @author DienNM (DEE)
 */

public class WorkerItemAttributeStructureServiceTest extends AbstractTest {
    
    @Autowired
    private ItemService itemService;

    @Autowired
    private ItemAttributeService itemAttributeService;

    @Autowired
    private ItemAttributeStructureService itemAttributeStructureService;

    @Autowired
    private WorkerItemAttributeStructureService workerItemAttributeStructureService;

    private ItemData itemData;

    @Before
    @Transactional
    public void startUp() {
        itemData = new ItemData();
        itemData.setId("index1");
        itemData.setCategoryId("cate-1");
        itemData.setName("item1");
        itemService.save(itemData);
        
        String json = readFile2String(AbstractTest.class.getResourceAsStream("/input-files/item-1.json"));
        ItemAttributeData root = itemAttributeStructureService.build(itemData, json);
        List<ItemAttributeData> attributes = AttributeStructureUtil.navigateAttribtesFromRoot(root);
        itemAttributeService.save(attributes);
    }

    @Test
    public void testJson1() {
        WorkerItemData workerItem = new WorkerItemData();
        workerItem.setCrawlType(CrawlType.DETAIL);
        workerItem.setId(1);
        workerItem.setItemKey(itemData.getId());
        workerItem.setLevel(Level.Level1);
        workerItem.setSiteKey("test-site");


        String json = readFile2String(AbstractTest.class.getResourceAsStream("/input-files/worker-tem-1.json"));
        WorkerItemAttributeData root = workerItemAttributeStructureService.build(workerItem, json);
        List<WorkerItemAttributeData> itemAtts = AttributeStructureUtil.navigateAttribtesFromRoot(root);
        Map<String, WorkerItemAttributeData> attMaps = new HashMap<String, WorkerItemAttributeData>();
        for (WorkerItemAttributeData att : itemAtts) {
            if (attMaps.containsKey(att.getId())) {
                Assert.fail();
            }
            System.out.println(att.getAttributeId());
            attMaps.put(att.getId(), att);
        }

        Assert.assertEquals("content", attMaps.get("1|index1|content").getName());
        Assert.assertEquals(null, attMaps.get("1|index1|content").getParentId());
        Assert.assertEquals(AttributeType.OBJECT, attMaps.get("1|index1|content").getType());

        Assert.assertEquals("name", attMaps.get("1|index1|content|name").getName());
        Assert.assertEquals("1|index1|content", attMaps.get("1|index1|content|name").getParentId());
        Assert.assertEquals(AttributeType.TEXT, attMaps.get("1|index1|content|name").getType());

        Assert.assertEquals("promotions", attMaps.get("1|index1|content|promotions").getName());
        Assert.assertEquals("1|index1|content", attMaps.get("1|index1|content|promotions").getParentId());
        Assert.assertEquals(AttributeType.LIST, attMaps.get("1|index1|content|promotions").getType());

        Assert.assertEquals("promotions", attMaps.get("1|index1|content|promotions|promotions").getName());
        Assert.assertEquals("1|index1|content|promotions", attMaps.get("1|index1|content|promotions|promotions")
                .getParentId());
        Assert.assertEquals(AttributeType.TEXT, attMaps.get("1|index1|content|promotions|promotions").getType());

        Assert.assertEquals("include", attMaps.get("1|index1|content|include").getName());
        Assert.assertEquals("1|index1|content", attMaps.get("1|index1|content|include").getParentId());
        Assert.assertEquals(AttributeType.HTML, attMaps.get("1|index1|content|include").getType());

        Assert.assertEquals("descriptions", attMaps.get("1|index1|content|descriptions").getName());
        Assert.assertEquals("1|index1|content", attMaps.get("1|index1|content|descriptions").getParentId());
        Assert.assertEquals(AttributeType.LIST, attMaps.get("1|index1|content|descriptions").getType());

        Assert.assertEquals("descriptions", attMaps.get("1|index1|content|descriptions|descriptions").getName());
        Assert.assertEquals("1|index1|content|descriptions", attMaps.get("1|index1|content|descriptions|descriptions")
                .getParentId());
        Assert.assertEquals(AttributeType.HTML, attMaps.get("1|index1|content|descriptions|descriptions").getType());

        Assert.assertEquals("image", attMaps.get("1|index1|content|image").getName());
        Assert.assertEquals("1|index1|content", attMaps.get("1|index1|content|image").getParentId());
        Assert.assertEquals(AttributeType.IMAGE, attMaps.get("1|index1|content|image").getType());

        Assert.assertEquals("album", attMaps.get("1|index1|content|album").getName());
        Assert.assertEquals("1|index1|content", attMaps.get("1|index1|content|album").getParentId());
        Assert.assertEquals(AttributeType.LIST, attMaps.get("1|index1|content|album").getType());

        Assert.assertEquals("album", attMaps.get("1|index1|content|album|album").getName());
        Assert.assertEquals("1|index1|content|album", attMaps.get("1|index1|content|album|album").getParentId());
        Assert.assertEquals(AttributeType.IMAGE, attMaps.get("1|index1|content|album|album").getType());

        Assert.assertEquals("attributes", attMaps.get("1|index1|content|attributes").getName());
        Assert.assertEquals("1|index1|content", attMaps.get("1|index1|content|attributes").getParentId());
        Assert.assertEquals(AttributeType.LIST, attMaps.get("1|index1|content|attributes").getType());

        Assert.assertEquals("attributes", attMaps.get("1|index1|content|attributes|attributes").getName());
        Assert.assertEquals("1|index1|content|attributes", attMaps.get("1|index1|content|attributes|attributes")
                .getParentId());
        Assert.assertEquals(AttributeType.OBJECT, attMaps.get("1|index1|content|attributes|attributes").getType());

        Assert.assertEquals("key", attMaps.get("1|index1|content|attributes|attributes|key").getName());
        Assert.assertEquals("1|index1|content|attributes|attributes",
                attMaps.get("1|index1|content|attributes|attributes|key").getParentId());
        Assert.assertEquals(AttributeType.TEXT, attMaps.get("1|index1|content|attributes|attributes|key").getType());

        Assert.assertEquals("value", attMaps.get("1|index1|content|attributes|attributes|value").getName());
        Assert.assertEquals("1|index1|content|attributes|attributes",
                attMaps.get("1|index1|content|attributes|attributes|value").getParentId());
        Assert.assertEquals(AttributeType.HTML, attMaps.get("1|index1|content|attributes|attributes|value").getType());
    }

}
