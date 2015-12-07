package com.myprj.crawler.web.facades;

import java.io.InputStream;
import java.util.List;

import com.myprj.crawler.domain.crawl.WorkerData;

/**
 * @author DienNM (DEE)
 */

public interface WorkerFacade {

    List<WorkerData> loadWorkersFromSource(InputStream inputStream);

}
