package com.myprj.crawler.web.dto;

import java.util.List;

import com.myprj.crawler.annotation.DataTransfer;
import com.myprj.crawler.domain.config.ItemAttributeData;
import com.myprj.crawler.enumeration.AttributeType;
import com.myprj.crawler.util.converter.DomainConverter;

/**
 * @author DienNM (DEE)
 */

public class ItemAttributeDTO extends AuditTDO {
    
    private static final long serialVersionUID = 1L;

    @DataTransfer("id")
    private String id;

    @DataTransfer("itemId")
    private String itemId;

    @DataTransfer("name")
    private String name;

    @DataTransfer("type")
    private AttributeType type;

    @DataTransfer("parentId")
    private String parentId;

    @DataTransfer("root")
    private boolean root;
    
    public static void toDTOs(List<ItemAttributeData> sources, List<ItemAttributeDTO> dests) {
        for(ItemAttributeData source :  sources) {
            ItemAttributeDTO dest = new ItemAttributeDTO();
            toDTO(source, dest);
            dests.add(dest);
        }
    }
    
    public static void toDTO(ItemAttributeData source, ItemAttributeDTO dest) {
        DomainConverter.convert(source, dest);
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
