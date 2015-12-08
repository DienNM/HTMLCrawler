package com.myprj.crawler.web.dto;

import java.util.List;

import com.myprj.crawler.annotation.DataTransfer;
import com.myprj.crawler.domain.config.WorkerItemAttributeData;
import com.myprj.crawler.enumeration.AttributeType;
import com.myprj.crawler.util.converter.DomainConverter;
import com.myprj.crawler.util.converter.ObjectConverter;

/**
 * @author DienNM (DEE)
 */

public class WorkerItemAttributeDTO extends AuditTDO {

    private static final long serialVersionUID = 1L;

    @DataTransfer("id")
    private String id;

    @DataTransfer("itemKey")
    private String itemKey;

    @DataTransfer("workerItemId")
    private long workerItemId;

    @DataTransfer("parentId")
    private String parentId;

    @DataTransfer("name")
    private String name;

    @DataTransfer("attributeId")
    private String attributeId;

    @DataTransfer("type")
    private AttributeType type;

    @DataTransfer("root")
    private boolean root;

    @DataTransfer("selector")
    private String selector;
    
    public static void toDTOs(List<WorkerItemAttributeData> sources, List<WorkerItemAttributeDTO> dests) {
        for(WorkerItemAttributeData source : sources) {
            WorkerItemAttributeDTO dest = new WorkerItemAttributeDTO();
            toDTO(source, dest);
            dests.add(dest);
        }
    }
    
    public static void toDTO(WorkerItemAttributeData source, WorkerItemAttributeDTO dest) {
        DomainConverter.convert(source, dest, new ObjectConverter<WorkerItemAttributeData, WorkerItemAttributeDTO>() {
            @Override
            public void convert(WorkerItemAttributeData src, WorkerItemAttributeDTO dest) {
                if(src.getSelector() != null) {
                    dest.setSelector(src.getSelector().getText());
                }
            }
        });
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAttributeId() {
        return attributeId;
    }

    public void setAttributeId(String attributeId) {
        this.attributeId = attributeId;
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

    public String getSelector() {
        return selector;
    }

    public void setSelector(String selector) {
        this.selector = selector;
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
