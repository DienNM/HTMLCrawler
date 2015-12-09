package com.myprj.crawler.web.facades.impl;

import static org.apache.commons.lang3.StringUtils.join;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myprj.crawler.domain.config.CategoryData;
import com.myprj.crawler.domain.config.ItemData;
import com.myprj.crawler.service.CategoryService;
import com.myprj.crawler.service.ItemService;
import com.myprj.crawler.web.facades.ItemFacade;
import com.myprj.crawler.web.util.ImportFileParser;
import com.myprj.crawler.web.util.ImportFileStruture;

/**
 * @author DienNM (DEE)
 */
@Service
public class ItemFacadeImpl implements ItemFacade {

    private Logger logger = LoggerFactory.getLogger(ItemFacadeImpl.class);

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ItemService itemService;

    @Override
    public List<String> loadItemsFromSource(InputStream inputStream, boolean forceBuild) {
        List<String> errorStructures = new ArrayList<String>();
        List<ImportFileStruture> importFileStrutures = ImportFileParser.loadItemFromSource(inputStream);
        if (importFileStrutures.isEmpty()) {
            errorStructures.add("No Item loaded");
            return errorStructures;
        }
        Map<String, CategoryData> categoryRepo = categoryService.getAllMap();
        for (ImportFileStruture item : importFileStrutures) {
            List<ItemData> itemDatas = saveItems(item.getMainLines(), categoryRepo);
            if (itemDatas.isEmpty()) {
                errorStructures.add("Cannot import Items: " + join(item.getMainLines(), ", "));
                break;
            }
            List<String> errors = buildItemStructures(itemDatas, item.getJson(), forceBuild);
            errorStructures.addAll(errors);
        }
        return errorStructures;
    }

    @Override
    public List<String> buildItemStructure(InputStream inputStream, boolean forceBuild) {
        List<String> errorStructures = new ArrayList<String>();
        List<ImportFileStruture> importFileStrutures = ImportFileParser.loadItemFromSource(inputStream);
        if (importFileStrutures.isEmpty()) {
            errorStructures.add("No Item loaded");
            return errorStructures;
        }
        for (ImportFileStruture item : importFileStrutures) {
            for (List<String> itemKeys : item.getMainLines()) {
                String itemKey = itemKeys.get(0);
                try {
                    itemService.buildItem(itemKey, item.getJson(), forceBuild);
                } catch (Exception e) {
                    errorStructures.add(itemKey + " - Error: " + e.getMessage());
                    logger.error("Cannot build Item Struture of: " + itemKey + " - Error: " + e.getMessage());
                }
            }
        }
        return errorStructures;
    }

    protected List<Map<String, String>> loadItemStructures(InputStream inputStream) {
        List<Map<String, String>> itemStructures = new ArrayList<Map<String, String>>();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(inputStream));
            String line = null;

            List<String> lines = new ArrayList<String>();
            boolean loadingItemStructure = false;

            String currentKey = null;

            while ((line = br.readLine()) != null) {
                if (line == null || line.trim().isEmpty() || line.startsWith("#")) {
                    continue;
                }
                if (line.startsWith(">") || line.startsWith("<END")) {
                    if (!lines.isEmpty()) {
                        Map<String, String> map = itemStructures.get(itemStructures.size() - 1);
                        map.put(currentKey, StringUtils.join(lines, " "));
                        loadingItemStructure = false;
                        currentKey = null;
                    }
                }

                if (loadingItemStructure) {
                    lines.add(line);
                }

                if (line.startsWith(">")) {
                    line = line.substring(1);
                    currentKey = line;
                    Map<String, String> itemStructureMap = new HashMap<String, String>();
                    itemStructureMap.put(line, null);
                    itemStructures.add(itemStructureMap);

                    loadingItemStructure = true;
                }
            }

        } catch (Exception ex) {
            return new ArrayList<Map<String, String>>();
        } finally {
            IOUtils.closeQuietly(br);
        }
        return itemStructures;
    }

    private List<ItemData> saveItems(List<List<String>> itemLines, Map<String, CategoryData> categoryRepo) {
        List<ItemData> items = new ArrayList<ItemData>();
        for (List<String> lines : itemLines) {
            for (String line : lines) {
                ItemData item = parseItem(line, categoryRepo);
                if (item != null) {
                    items.add(item);
                }
            }
        }
        if (!items.isEmpty()) {
            itemService.saveOrUpdate(items);
        }
        return items;
    }

    private List<String> buildItemStructures(List<ItemData> items, String json, boolean forceBuild) {
        List<String> itemErrors = new ArrayList<String>();
        for (ItemData item : items) {
            try {
                itemService.buildItem(item.getId(), json, forceBuild);
            } catch (Exception e) {
                itemErrors.add(item.getId() + " - Error: " + e.getMessage());
                logger.error("Cannot build Item Struture of: " + item.getId() + " - Error: " + e.getMessage());
            }
        }
        return itemErrors;
    }

    private ItemData parseItem(String line, Map<String, CategoryData> categoryRepo) {
        try {
            String[] elements = line.split(Pattern.quote("|"));
            ItemData item = new ItemData();
            item.setId(elements[0]);
            item.setName(elements[1]);

            CategoryData category = categoryRepo.get(elements[2]);
            if(category == null) {
                logger.error("Cannot find category: " + elements[2]);
                return null;
            }
            item.setCategoryId(category.getId());

            item.setDescription(elements[3]);
            return item;
        } catch (Exception e) {
            logger.error("Error parsing Line: {} to Item. Error: {}", line, e);
            return null;
        }
    }
}
