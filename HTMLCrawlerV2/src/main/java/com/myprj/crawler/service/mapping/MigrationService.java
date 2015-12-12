package com.myprj.crawler.service.mapping;

import java.util.List;

import com.myprj.crawler.domain.MigrationParam;
import com.myprj.crawler.domain.crawl.CrawlResultData;
import com.myprj.crawler.domain.target.ConsolidationData;

/**
 * @author DienNM (DEE)
 */

public interface MigrationService {
    
    void migrate(MigrationParam migrationParam);
    
    List<ConsolidationData> migrate(MigrationParam migrationParam, List<CrawlResultData> crawlResults);
    
}
