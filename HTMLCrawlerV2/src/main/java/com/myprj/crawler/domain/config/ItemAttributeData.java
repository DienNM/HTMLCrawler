package com.myprj.crawler.domain.config;

import static com.myprj.crawler.util.Serialization.deserialize;

import java.util.ArrayList;
import java.util.List;

import com.myprj.crawler.annotation.DataTransfer;
import com.myprj.crawler.annotation.EntityTransfer;
import com.myprj.crawler.domain.AuditData;
import com.myprj.crawler.enumeration.AttributeType;
import com.myprj.crawler.model.config.ItemAttributeModel;
import com.myprj.crawler.util.Serialization;
import com.myprj.crawler.util.converter.EntityConverter;
import com.myprj.crawler.util.converter.ObjectConverter;

/**
 * @author DienNM (DEE)
 */

public class ItemAttributeData extends AuditData {

    private static final long serialVersionUID = 1L;

    @DataTransfer("id")
    @EntityTransfer("id")
    private String id;

    @DataTransfer("itemId")
    @EntityTransfer("item_id")
    private long itemId;
    

    @DataTransfer("workerItemId")
    @EntityTransfer("worker_item_id")
    private long workerItemId;

    @DataTransfer("parentId")
    @EntityTransfer("parent_id")
    private String parentId;

    @DataTransfer("name")
    @EntityTransfer("name")
    private String name;

    @DataTransfer("attributeId")
    @EntityTransfer("attribute_id")
    private String attributeId;

    @DataTransfer("type")
    @EntityTransfer("att_type")
    private AttributeType type;

    @DataTransfer("root")
    @EntityTransfer("is_root")
    private boolean root = false;

    private AttributeSelector selector;

    private AttributeData attribute;

    private ItemAttributeData parent;

    private List<ItemAttributeData> children = new ArrayList<ItemAttributeData>();

    public ItemAttributeData() {
    }

    public static void collectionAllItemAttributes(ItemAttributeData itemAttribute,
            List<ItemAttributeData> itemAttributes) {
        itemAttributes.add(itemAttribute);
        for (ItemAttributeData itemAttributeData : itemAttribute.getChildren()) {
            collectionAllItemAttributes(itemAttributeData, itemAttributes);
        }
    }

    public static void toDatas(List<ItemAttributeModel> sources, List<ItemAttributeData> dests) {
        for (ItemAttributeModel source : sources) {
            ItemAttributeData dest = new ItemAttributeData();
            toData(source, dest);
            dests.add(dest);
        }
    }

    public static void toModels(List<ItemAttributeData> sources, List<ItemAttributeModel> dests) {
        for (ItemAttributeData source : sources) {
            ItemAttributeModel dest = new ItemAttributeModel();
            toModel(source, dest);
            dests.add(dest);
        }
    }

    public static void toData(ItemAttributeModel source, ItemAttributeData dest) {
        EntityConverter.convert2Data(source, dest, new ObjectConverter<ItemAttributeModel, ItemAttributeData>() {
            @Override
            public void convert(ItemAttributeModel src, ItemAttributeData dest) {
                dest.setSelector(deserialize(src.getSelectorJson(), AttributeSelector.class));
            }
        });
    }

    public static void toModel(ItemAttributeData source, ItemAttributeModel dest) {
        EntityConverter.convert2Entity(source, dest, new ObjectConverter<ItemAttributeData, ItemAttributeModel>() {
            @Override
            public void convert(ItemAttributeData src, ItemAttributeModel dest) {
                dest.setSelectorJson(Serialization.serialize(src.getSelector()));
            }
        });
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getItemId() {
        return itemId;
    }

    public void setItemId(long itemId) {
        this.itemId = itemId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ItemAttributeData getParent() {
        return parent;
    }

    public void setParent(ItemAttributeData parent) {
        this.parent = parent;
    }

    public List<ItemAttributeData> getChildren() {
        return children;
    }

    public void setChildren(List<ItemAttributeData> children) {
        this.children = children;
    }

    public AttributeSelector getSelector() {
        return selector;
    }

    public void setSelector(AttributeSelector selector) {
        this.selector = selector;
    }

    public String getAttributeId() {
        return attributeId;
    }

    public void setAttributeId(String attributeId) {
        this.attributeId = attributeId;
    }

    public AttributeData getAttribute() {
        return attribute;
    }

    public void setAttribute(AttributeData attribute) {
        this.attribute = attribute;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public AttributeType getType() {
        return type;
    }

    public void setType(AttributeType type) {
        this.type = type;
    }

    public boolean isRoot() {
        return root;
    }

    public void setRoot(boolean root) {
        this.root = root;
    }

    public long getWorkerItemId() {
        return workerItemId;
    }

    public void setWorkerItemId(long workerItemId) {
        this.workerItemId = workerItemId;
    }
}
