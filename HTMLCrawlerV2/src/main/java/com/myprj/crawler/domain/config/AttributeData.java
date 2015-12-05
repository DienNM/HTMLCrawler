package com.myprj.crawler.domain.config;

import java.util.ArrayList;
import java.util.List;

import com.myprj.crawler.annotation.DataTransfer;
import com.myprj.crawler.annotation.EntityTransfer;
import com.myprj.crawler.domain.AuditData;
import com.myprj.crawler.enumeration.AttributeType;
import com.myprj.crawler.model.config.AttributeModel;
import com.myprj.crawler.util.converter.EntityConverter;

/**
 * @author DienNM (DEE)
 */

public class AttributeData extends AuditData {

    private static final long serialVersionUID = 1L;

    @DataTransfer("id")
    @EntityTransfer("id")
    private String id;

    @DataTransfer("itemId")
    @EntityTransfer("item_id")
    private long itemId;

    @DataTransfer("name")
    @EntityTransfer("name")
    private String name;

    @DataTransfer("type")
    @EntityTransfer("att_type")
    private AttributeType type;

    @DataTransfer("parentId")
    @EntityTransfer("parent_id")
    private String parentId;

    private AttributeData parent;

    @DataTransfer("root")
    @EntityTransfer("is_root")
    private boolean root;

    private List<AttributeData> children = new ArrayList<AttributeData>();

    public AttributeData() {
    }

    public static void toDatas(List<AttributeModel> sources, List<AttributeData> dests) {
        for (AttributeModel source : sources) {
            AttributeData dest = new AttributeData();
            toData(source, dest);
            dests.add(dest);
        }
    }

    public static void toModels(List<AttributeData> sources, List<AttributeModel> dests) {
        for (AttributeData source : sources) {
            AttributeModel dest = new AttributeModel();
            toModel(source, dest);
            dests.add(dest);
        }
    }

    public static void toData(AttributeModel source, AttributeData dest) {
        EntityConverter.convert2Data(source, dest);
    }

    public static void toModel(AttributeData source, AttributeModel dest) {
        EntityConverter.convert2Entity(source, dest);
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

    public AttributeType getType() {
        return type;
    }

    public void setType(AttributeType type) {
        this.type = type;
    }

    public AttributeData getParent() {
        return parent;
    }

    public void setParent(AttributeData parent) {
        this.parent = parent;
    }

    public List<AttributeData> getChildren() {
        return children;
    }

    public void setChildren(List<AttributeData> children) {
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
