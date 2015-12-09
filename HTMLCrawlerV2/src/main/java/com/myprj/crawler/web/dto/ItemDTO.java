package com.myprj.crawler.web.dto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.myprj.crawler.annotation.DataTransfer;
import com.myprj.crawler.domain.config.ItemAttributeData;
import com.myprj.crawler.domain.config.ItemData;
import com.myprj.crawler.util.converter.DomainConverter;
import com.myprj.crawler.util.converter.ObjectConverter;

/**
 * @author DienNM (DEE)
 */

public class ItemDTO extends AuditTDO {

    private static final long serialVersionUID = 1L;

    @DataTransfer("id")
    private String id;

    @DataTransfer("name")
    private String name;

    @DataTransfer("categoryId")
    private String categoryId;

    @DataTransfer("description")
    private String description;

    @DataTransfer("built")
    private boolean built;

    @DataTransfer("attributes")
    private List<ItemAttributeDTO> attributes = new ArrayList<ItemAttributeDTO>();

    @DataTransfer("sampleContent")
    private Map<String, Object> sampleContent = new HashMap<String, Object>();
    
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
                List<ItemAttributeData> attributeDatas = src.getAttributes();
                List<ItemAttributeDTO>  attributeDTOs = new ArrayList<ItemAttributeDTO>();
                ItemAttributeDTO.toDTOs(attributeDatas, attributeDTOs);
                dest.setAttributes(attributeDTOs);
                
                if(src.getSampleContent() != null) {
                    dest.setSampleContent(src.getSampleContent().getContent());
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
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

    public List<ItemAttributeDTO> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<ItemAttributeDTO> attributes) {
        this.attributes = attributes;
    }
    
    public Map<String, Object> getSampleContent() {
        return sampleContent;
    }
    
    public void setSampleContent(Map<String, Object> sampleContent) {
        this.sampleContent = sampleContent;
    }
}
