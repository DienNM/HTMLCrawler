package com.myprj.crawler.web.dto;

import java.util.List;

import com.myprj.crawler.annotation.DataTransfer;
import com.myprj.crawler.domain.config.AttributeData;
import com.myprj.crawler.enumeration.AttributeType;
import com.myprj.crawler.util.converter.DomainConverter;

/**
 * @author DienNM (DEE)
 */

public class AttributeDTO extends AuditTDO {
    
    private static final long serialVersionUID = 1L;

    @DataTransfer("id")
    private String id;

    @DataTransfer("itemId")
    private long itemId;

    @DataTransfer("name")
    private String name;

    @DataTransfer("type")
    private AttributeType type;

    @DataTransfer("parentId")
    private String parentId;

    @DataTransfer("root")
    private boolean root;
    
    public static void toDTOs(List<AttributeData> sources, List<AttributeDTO> dests) {
        for(AttributeData source :  sources) {
            AttributeDTO dest = new AttributeDTO();
            toDTO(source, dest);
            dests.add(dest);
        }
    }
    
    public static void toDTO(AttributeData source, AttributeDTO dest) {
        DomainConverter.convert(source, dest);
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
