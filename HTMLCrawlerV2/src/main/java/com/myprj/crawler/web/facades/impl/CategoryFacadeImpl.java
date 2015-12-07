package com.myprj.crawler.web.facades.impl;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.myprj.crawler.domain.config.CategoryData;
import com.myprj.crawler.web.facades.CategoryFacade;

/**
 * @author DienNM (DEE)
 */
@Service
public class CategoryFacadeImpl implements CategoryFacade {

    private Logger logger = LoggerFactory.getLogger(CategoryFacadeImpl.class);

    @Override
    public List<CategoryData> loadCategoriesFromSource(InputStream inputStream) {
        List<CategoryData> categories = new ArrayList<CategoryData>();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(inputStream));
            String line = null;
            while ((line = br.readLine()) != null) {
                if (line == null || line.trim().isEmpty() || line.startsWith("#")) {
                    continue;
                }
                line = line.substring(1);
                CategoryData category = parseCategory(line);
                if (category == null) {
                    return new ArrayList<CategoryData>();
                }
                categories.add(category);
            }
        } catch (Exception ex) {
            logger.error("Error loading Categories. Error: {}", ex);
            return new ArrayList<CategoryData>();
        } finally {
            IOUtils.closeQuietly(br);
            IOUtils.closeQuietly(inputStream);
        }
        return categories;
    }

    private CategoryData parseCategory(String line) {
        try {
            String[] elements = line.split(Pattern.quote("|"));
            CategoryData category = new CategoryData();
            category.setKey(elements[0]);
            category.setName(elements[1]);
            category.setParentKey(elements[2]);
            category.setDescription(elements[3]);
            return category;
        } catch (Exception e) {
            logger.error("Error parsing Line: {} to Category. Error: {}", line, e);
            return null;
        }
    }

}
