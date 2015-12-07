package com.myprj.crawler.web.facades.impl;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myprj.crawler.domain.config.CategoryData;
import com.myprj.crawler.domain.config.ItemData;
import com.myprj.crawler.service.CategoryService;
import com.myprj.crawler.web.facades.ItemFacade;

/**
 * @author DienNM (DEE)
 */
@Service
public class ItemFacadeImpl implements ItemFacade {

    private Logger logger = LoggerFactory.getLogger(ItemFacadeImpl.class);

    @Autowired
    private CategoryService categoryService;

    @Override
    public List<ItemData> loadItemsFromSource(InputStream inputStream) {
        List<ItemData> items = new ArrayList<ItemData>();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(inputStream));
            String line = null;
            Map<String, CategoryData> categoryRepo = categoryService.getAllMap();
            while ((line = br.readLine()) != null) {
                if (line == null || line.trim().isEmpty() || line.startsWith("#")) {
                    continue;
                }
                ItemData item = parseItem(line, categoryRepo);
                if (item == null) {
                    return new ArrayList<ItemData>();
                }
                items.add(item);
            }
        } catch (Exception ex) {
            logger.error("Error loading Item. Error: {}", ex);
            return new ArrayList<ItemData>();
        } finally {
            IOUtils.closeQuietly(br);
            IOUtils.closeQuietly(inputStream);
        }
        return items;
    }

    private ItemData parseItem(String line, Map<String, CategoryData> categoryRepo) {
        try {
            String[] elements = line.split(Pattern.quote("|"));
            ItemData item = new ItemData();
            item.setKey(elements[0]);
            item.setName(elements[1]);

            CategoryData category = categoryRepo.get(elements[2]);
            item.setCategoryId(category.getId());

            item.setDescription(elements[3]);
            return item;
        } catch (Exception e) {
            logger.error("Error parsing Line: {} to Item. Error: {}", line, e);
            return null;
        }
    }
}
