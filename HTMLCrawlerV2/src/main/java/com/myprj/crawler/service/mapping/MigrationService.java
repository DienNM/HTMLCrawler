package com.myprj.crawler.service.mapping;

import java.util.List;

import com.myprj.crawler.domain.MigrationContext;
import com.myprj.crawler.domain.crawl.CrawlResultData;
import com.myprj.crawler.domain.target.ConsolidationData;

/**
 * @author DienNM (DEE)
 */

public interface MigrationService {
    
    void migrate(MigrationContext migrationCtx);
    
    List<ConsolidationData> migrate(MigrationContext migrationCtx, List<CrawlResultData> crawlResults);
    
}
