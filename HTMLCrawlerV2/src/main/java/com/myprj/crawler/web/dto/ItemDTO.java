package com.myprj.crawler.web.dto;

import java.util.ArrayList;
import java.util.List;

import com.myprj.crawler.annotation.DataTransfer;
import com.myprj.crawler.domain.config.AttributeData;
import com.myprj.crawler.domain.config.ItemData;
import com.myprj.crawler.util.converter.DomainConverter;
import com.myprj.crawler.util.converter.ObjectConverter;

/**
 * @author DienNM (DEE)
 */

public class ItemDTO extends AuditTDO {

    private static final long serialVersionUID = 1L;

    @DataTransfer("id")
    private long id;

    @DataTransfer("name")
    private String name;

    @DataTransfer("categoryId")
    private long categoryId;

    @DataTransfer("description")
    private String description;

    @DataTransfer("built")
    private boolean built;
    
    @DataTransfer("attributes")
    private List<AttributeDTO> attributes = new ArrayList<AttributeDTO>();
    
    public static void toItemDTOs(List<ItemData> sources, List<ItemDTO> dests) {
        for(ItemData source : sources) {
            ItemDTO dest = new ItemDTO();
            toItemDTO(source, dest);
            dests.add(dest);
        }
    }
    
    public static void toItemDTO(ItemData source, ItemDTO dest) {
        DomainConverter.convert(source, dest, new ObjectConverter<ItemData, ItemDTO>() {
            @Override
            public void convert(ItemData src, ItemDTO dest) {
                List<AttributeData> attributeDatas = src.getAttributes();
                List<AttributeDTO>  attributeDTOs = new ArrayList<AttributeDTO>();
                AttributeDTO.toDTOs(attributeDatas, attributeDTOs);
                dest.setAttributes(attributeDTOs);
            }
            
        });
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isBuilt() {
        return built;
    }

    public void setBuilt(boolean built) {
        this.built = built;
    }

    public List<AttributeDTO> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<AttributeDTO> attributes) {
        this.attributes = attributes;
    }
}
