package com.myprj.crawler.domain.config;

import static com.myprj.crawler.util.Serialization.deserialize;

import java.util.ArrayList;
import java.util.List;

import com.myprj.crawler.annotation.DataTransfer;
import com.myprj.crawler.annotation.EntityTransfer;
import com.myprj.crawler.domain.AuditData;
import com.myprj.crawler.enumeration.AttributeType;
import com.myprj.crawler.model.config.WorkerItemAttributeModel;
import com.myprj.crawler.util.Serialization;
import com.myprj.crawler.util.converter.EntityConverter;
import com.myprj.crawler.util.converter.ObjectConverter;

/**
 * @author DienNM (DEE)
 */

public class WorkerItemAttributeData extends AuditData {

    private static final long serialVersionUID = 1L;

    @DataTransfer("id")
    @EntityTransfer("id")
    private String id;

    @DataTransfer("itemKey")
    @EntityTransfer("item_key")
    private String itemKey;
    

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

    private WorkerItemAttributeData parent;

    private List<WorkerItemAttributeData> children = new ArrayList<WorkerItemAttributeData>();

    public WorkerItemAttributeData() {
    }

    public static void collectionAllItemAttributes(WorkerItemAttributeData itemAttribute,
            List<WorkerItemAttributeData> itemAttributes) {
        itemAttributes.add(itemAttribute);
        for (WorkerItemAttributeData itemAttributeData : itemAttribute.getChildren()) {
            collectionAllItemAttributes(itemAttributeData, itemAttributes);
        }
    }

    public static void toDatas(List<WorkerItemAttributeModel> sources, List<WorkerItemAttributeData> dests) {
        for (WorkerItemAttributeModel source : sources) {
            WorkerItemAttributeData dest = new WorkerItemAttributeData();
            toData(source, dest);
            dests.add(dest);
        }
    }

    public static void toModels(List<WorkerItemAttributeData> sources, List<WorkerItemAttributeModel> dests) {
        for (WorkerItemAttributeData source : sources) {
            WorkerItemAttributeModel dest = new WorkerItemAttributeModel();
            toModel(source, dest);
            dests.add(dest);
        }
    }

    public static void toData(WorkerItemAttributeModel source, WorkerItemAttributeData dest) {
        EntityConverter.convert2Data(source, dest, new ObjectConverter<WorkerItemAttributeModel, WorkerItemAttributeData>() {
            @Override
            public void convert(WorkerItemAttributeModel src, WorkerItemAttributeData dest) {
                dest.setSelector(deserialize(src.getSelectorJson(), AttributeSelector.class));
            }
        });
    }

    public static void toModel(WorkerItemAttributeData source, WorkerItemAttributeModel dest) {
        EntityConverter.convert2Entity(source, dest, new ObjectConverter<WorkerItemAttributeData, WorkerItemAttributeModel>() {
            @Override
            public void convert(WorkerItemAttributeData src, WorkerItemAttributeModel dest) {
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public WorkerItemAttributeData getParent() {
        return parent;
    }

    public void setParent(WorkerItemAttributeData parent) {
        this.parent = parent;
    }

    public List<WorkerItemAttributeData> getChildren() {
        return children;
    }

    public void setChildren(List<WorkerItemAttributeData> children) {
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

    public String getItemKey() {
        return itemKey;
    }

    public void setItemKey(String itemKey) {
        this.itemKey = itemKey;
    }
}
