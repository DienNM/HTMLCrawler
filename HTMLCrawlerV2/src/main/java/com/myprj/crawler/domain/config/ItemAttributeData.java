package com.myprj.crawler.domain.config;

import java.util.ArrayList;
import java.util.List;

import com.myprj.crawler.annotation.DataTransfer;
import com.myprj.crawler.annotation.EntityTransfer;
import com.myprj.crawler.domain.AuditData;
import com.myprj.crawler.enumeration.AttributeType;
import com.myprj.crawler.model.config.ItemAttributeModel;
import com.myprj.crawler.util.converter.EntityConverter;

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
    private String itemId;

    @DataTransfer("name")
    @EntityTransfer("name")
    private String name;

    @DataTransfer("type")
    @EntityTransfer("att_type")
    private AttributeType type;

    @DataTransfer("parentId")
    @EntityTransfer("parent_id")
    private String parentId;

    private ItemAttributeData parent;

    @DataTransfer("root")
    @EntityTransfer("is_root")
    private boolean root;

    private List<ItemAttributeData> children = new ArrayList<ItemAttributeData>();

    public ItemAttributeData() {
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
        EntityConverter.convert2Data(source, dest);
    }

    public static void toModel(ItemAttributeData source, ItemAttributeModel dest) {
        EntityConverter.convert2Entity(source, dest);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AttributeType getType() {
        return type;
    }

    public void setType(AttributeType type) {
        this.type = type;
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

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public boolean isRoot() {
        return root;
    }

    public void setRoot(boolean root) {
        this.root = root;
    }
}
