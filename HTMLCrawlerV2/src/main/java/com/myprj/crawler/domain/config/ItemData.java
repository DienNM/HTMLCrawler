package com.myprj.crawler.domain.config;

import static com.myprj.crawler.util.Serialization.deserialize;
import static com.myprj.crawler.util.Serialization.serialize;

import java.util.ArrayList;
import java.util.List;

import com.myprj.crawler.annotation.DataTransfer;
import com.myprj.crawler.annotation.EntityTransfer;
import com.myprj.crawler.domain.AuditData;
import com.myprj.crawler.model.config.ItemModel;
import com.myprj.crawler.util.converter.EntityConverter;
import com.myprj.crawler.util.converter.ObjectConverter;

/**
 * @author DienNM (DEE)
 */

public class ItemData extends AuditData {

    private static final long serialVersionUID = 1L;

    @DataTransfer("id")
    @EntityTransfer("id")
    private long id;

    @DataTransfer("name")
    @EntityTransfer("name")
    private String name;

    @DataTransfer("key")
    @EntityTransfer("item_key")
    private String key;

    @DataTransfer("categoryId")
    @EntityTransfer("category_id")
    private long categoryId;

    @DataTransfer("description")
    @EntityTransfer("description")
    private String description;

    @DataTransfer("built")
    @EntityTransfer("is_built")
    private boolean built;
    
    private CategoryData categoryData;
    
    private ItemContent sampleContent;

    private List<ItemAttributeData> attributes = new ArrayList<ItemAttributeData>();

    public ItemData() {
    }

    public static void toDatas(List<ItemModel> sources, List<ItemData> dests) {
        for (ItemModel source : sources) {
            ItemData dest = new ItemData();
            toData(source, dest);
            dests.add(dest);
        }
    }

    public static void toModels(List<ItemData> sources, List<ItemModel> dests) {
        for (ItemData source : sources) {
            ItemModel dest = new ItemModel();
            toModel(source, dest);
            dests.add(dest);
        }
    }

    public static void toData(ItemModel source, ItemData dest) {
        EntityConverter.convert2Data(source, dest, new ObjectConverter<ItemModel, ItemData>() {
            @Override
            public void convert(ItemModel src, ItemData dest) {
                dest.setSampleContent(deserialize(src.getSampleContentJson(), ItemContent.class));
            }
        });
    }

    public static void toModel(ItemData source, ItemModel dest) {
        EntityConverter.convert2Entity(source, dest, new ObjectConverter<ItemData, ItemModel>() {
            @Override
            public void convert(ItemData src, ItemModel dest) {
                dest.setSampleContentJson(serialize(src.getSampleContent()));
            }
        });
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<ItemAttributeData> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<ItemAttributeData> attributes) {
        this.attributes = attributes;
    }

    public ItemContent getSampleContent() {
        return sampleContent;
    }

    public void setSampleContent(ItemContent sampleontent) {
        this.sampleContent = sampleontent;
    }

    public boolean isBuilt() {
        return built;
    }

    public void setBuilt(boolean built) {
        this.built = built;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public CategoryData getCategoryData() {
        return categoryData;
    }

    public void setCategoryData(CategoryData categoryData) {
        this.categoryData = categoryData;
    }
}
