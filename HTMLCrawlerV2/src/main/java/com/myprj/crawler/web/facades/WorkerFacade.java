package com.myprj.crawler.web.facades;

import java.io.InputStream;
import java.util.List;

/**
 * @author DienNM (DEE)
 */

public interface WorkerFacade {

    List<String> loadImportWorkersFromSource(InputStream inputStream, boolean forceBuild);

}
