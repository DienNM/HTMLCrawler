package com.myprj.crawler.web.facades.impl;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myprj.crawler.domain.MigrationParam;
import com.myprj.crawler.service.mapping.MigrationService;
import com.myprj.crawler.util.Config;
import com.myprj.crawler.util.StreamUtil;
import com.myprj.crawler.web.facades.MigrationFacade;

/**
 * @author DienNM (DEE)
 */
@Service
public class MigrationFacadeImpl implements MigrationFacade {

    private Logger logger = LoggerFactory.getLogger(MigrationFacadeImpl.class);

    @Autowired
    private MigrationService migrationService;

    private ExecutorService executorService = null;

    @PostConstruct
    public void init() {
        executorService = Executors.newFixedThreadPool(5);
    }

    @PreDestroy
    public void destroy() {
        if (executorService != null && !executorService.isShutdown()) {
            executorService.shutdown();
        }
    }

    @Override
    public void doFullMapping() {
        String mappingFullFileName = Config.get("mapping.full.file");
        List<String> csvLines = StreamUtil.readCSVFile(mappingFullFileName);
        if (csvLines.isEmpty()) {
            logger.warn("Mapping file is empty");
            return;
        }
        for (String line : csvLines) {
            String[] items = line.split(Pattern.quote("|"));
            if (items.length != 3) {
                logger.warn("Line is invalid: " + line);
                continue;
            }
            String categoryKey = items[0];
            String itemKey = items[1];
            String siteKey = items[2];
            MigrationParam migrationParam = new MigrationParam(categoryKey, itemKey, siteKey);
            migrationService.migrate(migrationParam);
        }
    }

}
