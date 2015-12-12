package com.myprj.crawler.service.mapping;

import java.util.List;

import com.myprj.crawler.domain.MigrationParam;
import com.myprj.crawler.domain.crawl.CrawlResultData;

/**
 * @author DienNM (DEE)
 */

public interface MigrationService {
    
    void migrate(MigrationParam migrationParam, List<CrawlResultData> crawlResults);
    
}
