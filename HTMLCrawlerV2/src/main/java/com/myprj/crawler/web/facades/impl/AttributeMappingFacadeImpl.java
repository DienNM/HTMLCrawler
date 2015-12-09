package com.myprj.crawler.web.facades.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myprj.crawler.domain.target.AttributeMappingData;
import com.myprj.crawler.service.CategoryService;
import com.myprj.crawler.service.ItemService;
import com.myprj.crawler.service.SiteService;
import com.myprj.crawler.service.target.AttributeMappingService;
import com.myprj.crawler.web.facades.AttributeMappingFacade;
import com.myprj.crawler.web.util.ImportDataException;
import com.myprj.crawler.web.util.ParserDataException;

/**
 * @author DienNM (DEE)
 */
@Service
public class AttributeMappingFacadeImpl implements AttributeMappingFacade {

    private Logger logger = LoggerFactory.getLogger(AttributeMappingFacadeImpl.class);

    @Autowired
    private AttributeMappingService attributeMappingService;

    @Autowired
    private SiteService siteService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ItemService itemService;

    @Override
    public List<AttributeMappingData> importFromSource(InputStream inputStream) throws ImportDataException {
        try {
            List<AttributeMappingData> attributeMappingDatas = loadMappingsFromSource(inputStream);
            if (attributeMappingDatas.isEmpty()) {
                throw new ImportDataException("No Attribute Mappings are imported");
            }
            attributeMappingService.saveOrUpdate(attributeMappingDatas);
            return attributeMappingDatas;
        } catch (ParserDataException e) {
            throw new ImportDataException(e.getMessage());
        }
    }

    @Override
    public List<AttributeMappingData> loadMappingsFromSource(InputStream inputStream) throws ParserDataException {
        List<AttributeMappingData> attributesMapping = new ArrayList<AttributeMappingData>();
        BufferedReader br = null;
        try {

            Set<String> siteIds = siteService.getAllIds();
            Set<String> categoryIds = categoryService.getAllIds();

            br = new BufferedReader(new InputStreamReader(inputStream));
            String line = null;
            while ((line = br.readLine()) != null) {
                if (line == null || line.trim().isEmpty() || line.startsWith("#")) {
                    continue;
                }
                AttributeMappingData att = parseObject(line);
                if (!siteIds.contains(att.getSiteKey())) {
                    throw new ParserDataException("Site Key: " + att.getSiteKey() + " not found");
                }
                if (!categoryIds.contains(att.getCategoryKey())) {
                    throw new ParserDataException("Category Key: " + att.getCategoryKey() + " not found");
                }
                attributesMapping.add(att);
            }
        } catch (IOException ex) {
            logger.error("Error loading Sites. Error: {}", ex);
            throw new ParserDataException(ex.getMessage());
        } finally {
            IOUtils.closeQuietly(br);
            IOUtils.closeQuietly(inputStream);
        }
        return attributesMapping;
    }

    private AttributeMappingData parseObject(String line) throws ParserDataException {
        try {
            String[] elements = line.split(Pattern.quote("|"));
            AttributeMappingData attMapping = new AttributeMappingData();
            attMapping.setSiteKey(elements[0]);
            attMapping.setCategoryKey(elements[1]);
            attMapping.setItemKey(elements[2]);
            attMapping.setAttributeName(elements[3]);
            attMapping.setValueMapping(elements[4]);
            if (elements.length > 5) {
                attMapping.setMappingStrategy(elements[5]);
            }
            return attMapping;
        } catch (Exception e) {
            throw new ParserDataException("Error parsing line: " + line);
        }
    }

}
