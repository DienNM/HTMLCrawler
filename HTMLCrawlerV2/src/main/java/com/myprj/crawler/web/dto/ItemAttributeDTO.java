package com.myprj.crawler.web.dto;

import java.util.List;

import com.myprj.crawler.annotation.DataTransfer;
import com.myprj.crawler.domain.config.ItemAttributeData;
import com.myprj.crawler.enumeration.AttributeType;
import com.myprj.crawler.util.converter.DomainConverter;
import com.myprj.crawler.util.converter.ObjectConverter;

/**
 * @author DienNM (DEE)
 */

public class ItemAttributeDTO extends AuditTDO {

    private static final long serialVersionUID = 1L;

    @DataTransfer("id")
    private String id;

    @DataTransfer("itemId")
    private long itemId;

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
    
    public static void toDTOs(List<ItemAttributeData> sources, List<ItemAttributeDTO> dests) {
        for(ItemAttributeData source : sources) {
            ItemAttributeDTO dest = new ItemAttributeDTO();
            toDTO(source, dest);
            dests.add(dest);
        }
    }
    
    public static void toDTO(ItemAttributeData source, ItemAttributeDTO dest) {
        DomainConverter.convert(source, dest, new ObjectConverter<ItemAttributeData, ItemAttributeDTO>() {
            @Override
            public void convert(ItemAttributeData src, ItemAttributeDTO dest) {
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

    public long getItemId() {
        return itemId;
    }

    public void setItemId(long itemId) {
        this.itemId = itemId;
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
}
